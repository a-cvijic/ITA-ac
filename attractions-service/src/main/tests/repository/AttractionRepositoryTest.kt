import com.example.attractionsservice.model.Attraction;
import com.example.attractionsservice.repository.AttractionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
class AttractionRepositoryTest {

    @Mock
    private AttractionRepository attractionRepository;

    @InjectMocks
    private AttractionService attractionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindById() {
        Long attractionId = 1L;
        Attraction attraction = new Attraction(attractionId, "Attraction 1", "Description 1", "Location 1");

        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));

        Optional<Attraction> foundAttraction = attractionRepository.findById(attractionId);

        assertEquals(attraction, foundAttraction.orElse(null));
    }

}
