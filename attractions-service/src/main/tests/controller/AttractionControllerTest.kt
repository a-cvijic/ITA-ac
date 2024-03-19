import com.example.attractionsservice.model.Attraction;
import com.example.attractionsservice.repository.AttractionRepository;
import com.example.attractionsservice.service.AttractionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AttractionControllerTest {

    @Mock
    private AttractionRepository attractionRepository;

    @InjectMocks
    private AttractionService attractionService;

    private AttractionController attractionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        attractionController = new AttractionController(attractionService);
    }

    @Test
    void testGetAllAttractions() {
        Attraction attraction1 = new Attraction(1L, "Attraction 1", "Description 1", "Location 1");
        Attraction attraction2 = new Attraction(2L, "Attraction 2", "Description 2", "Location 2");
        List<Attraction> attractions = Arrays.asList(attraction1, attraction2);

        when(attractionRepository.findAll()).thenReturn(attractions);

        ResponseEntity<List<Attraction>> response = attractionController.getAllAttractions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAttractionById() {
        Long attractionId = 1L;
        Attraction attraction = new Attraction(attractionId, "Attraction 1", "Description 1", "Location 1");

        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));

        ResponseEntity<Attraction> response = attractionController.getAttractionById(attractionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(attraction, response.getBody());
    }

    @Test
    void testCreateAttraction() {
        Attraction attraction = new Attraction(1L, "Attraction 1", "Description 1", "Location 1");

        when(attractionRepository.save(attraction)).thenReturn(attraction);

        ResponseEntity<Attraction> response = attractionController.createAttraction(attraction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(attraction, response.getBody());
    }

    @Test
    void testUpdateAttraction() {
        Long attractionId = 1L;
        Attraction existingAttraction = new Attraction(attractionId, "Attraction 1", "Description 1", "Location 1");
        Attraction updatedAttraction = new Attraction(attractionId, "Updated Attraction", "Updated Description", "Updated Location");

        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(existingAttraction));
        when(attractionRepository.save(updatedAttraction)).thenReturn(updatedAttraction);

        ResponseEntity<Attraction> response = attractionController.updateAttraction(attractionId, updatedAttraction);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedAttraction, response.getBody());
    }

    @Test
    void testDeleteAttraction() {
        Long attractionId = 1L;
        Attraction attraction = new Attraction(attractionId, "Attraction 1", "Description 1", "Location 1");

        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));

        ResponseEntity<Void> response = attractionController.deleteAttraction(attractionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(attractionRepository, times(1)).deleteById(attractionId);
    }
}
