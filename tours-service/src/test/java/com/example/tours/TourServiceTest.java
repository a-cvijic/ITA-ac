package com.example.tours;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@QuarkusTest
public class TourServiceTest {

    @Inject
    EntityManager entityManager;

    @GrpcClient
    TourServiceGrpc.TourServiceBlockingStub tourService;

    @BeforeEach
    @Transactional
    public void setup() {
        // Clear the tours table and reset the sequence before each test
        entityManager.createNativeQuery("TRUNCATE TABLE public.tours RESTART IDENTITY").executeUpdate();
    }

    @Test
    public void testCreateTour() {
        CreateTourRequest request = CreateTourRequest.newBuilder()
                .setTourName("Run 4 Falcons")
                .setDescription("The best tour ever")
                .addHighlights("Highlight 1")
                .setLocation("Maribor")
                .build();

        CreateTourResponse response = tourService.createTour(request);

        // Verify that a tour ID was generated and is not zero, indicating a tour was created
        assertNotEquals("0", response.getTourId(), "The tour ID should not be 0.");
    }

    @Test
    public void testGetTour() {
        // Create a tour
        CreateTourRequest createRequest = CreateTourRequest.newBuilder()
                .setTourName("Run 4 Falcons")
                .setDescription("The best tour ever")
                .addHighlights("Highlight 1")
                .setLocation("Maribor")
                .build();
        CreateTourResponse createResponse = tourService.createTour(createRequest);

        // Get the tour by its ID
        GetTourRequest getRequest = GetTourRequest.newBuilder()
                .setTourId(createResponse.getTourId())
                .build();
        TourResponse tourResponse = tourService.getTour(getRequest);

        // Verify that the retrieved tour matches the created tour
        assertEquals(createRequest.getTourName(), tourResponse.getTourName());
        assertEquals(createRequest.getDescription(), tourResponse.getDescription());
        assertEquals(createRequest.getHighlightsList(), tourResponse.getHighlightsList());
        assertEquals(createRequest.getLocation(), tourResponse.getLocation());
    }
}
