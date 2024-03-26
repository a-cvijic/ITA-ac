package com.example.tours;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.stream.Collectors;
import java.util.List;

@Singleton
@GrpcService
public class TourServiceImplementation extends TourServiceGrpc.TourServiceImplBase {

    @Inject
    Logger logger;

    @Override
    @Transactional
    public void createTour(CreateTourRequest request, StreamObserver<CreateTourResponse> responseObserver) {
        logger.info("Received request to create tour");
        try {
            Tour newTour = new Tour();
            newTour.name = request.getTourName();
            newTour.description = request.getDescription();
            newTour.highlights = String.join(",", request.getHighlightsList());
            newTour.location = request.getLocation();
            newTour.persist();

            logger.infof("Tour created with ID: %s", newTour.id);

            CreateTourResponse response = CreateTourResponse.newBuilder()
                    .setTourId(newTour.id.toString())
                    .build();

            responseObserver.onNext(response);
        } catch (Exception e) {
            logger.error("Tour creation failed", e);
            responseObserver.onError(Status.INTERNAL.withDescription("Tour creation failed").asRuntimeException());
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    @Transactional
    public void getTour(GetTourRequest request, StreamObserver<TourResponse> responseObserver) {
        logger.infof("Received request to get tour with ID: %s", request.getTourId());
        Tour tour = Tour.findById(Long.parseLong(request.getTourId()));
        if (tour == null) {
            logger.errorf("Tour with ID %s not found", request.getTourId());
            responseObserver.onError(Status.NOT_FOUND.withDescription("Tour not found").asRuntimeException());
        } else {
            TourResponse response = TourResponse.newBuilder()
                    .setTourName(tour.name)
                    .setDescription(tour.description)
                    .addAllHighlights(List.of(tour.highlights.split(",")))
                    .setLocation(tour.location)
                    .build();

            responseObserver.onNext(response);
            logger.infof("Tour with ID %s retrieved successfully", request.getTourId());
        }
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateTour(UpdateTourRequest request, StreamObserver<UpdateTourResponse> responseObserver) {
        logger.infof("Received request to update tour with ID: %s", request.getTourId());
        Tour tour = Tour.findById(Long.parseLong(request.getTourId()));
        if (tour == null) {
            logger.errorf("Tour with ID %s not found for update", request.getTourId());
            responseObserver.onError(Status.NOT_FOUND.withDescription("Tour not found").asRuntimeException());
        } else {
            tour.name = request.getTourName();
            tour.description = request.getDescription();
            tour.highlights = String.join(",", request.getHighlightsList());
            tour.location = request.getLocation();
            tour.persist();

            logger.infof("Tour with ID %s updated successfully", tour.id);

            UpdateTourResponse response = UpdateTourResponse.newBuilder().build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void deleteTour(DeleteTourRequest request, StreamObserver<DeleteTourResponse> responseObserver) {
        logger.infof("Received request to delete tour with ID: %s", request.getTourId());
        boolean deleted = Tour.deleteById(Long.parseLong(request.getTourId()));
        if (deleted) {
            logger.infof("Tour with ID %s deleted successfully", request.getTourId());
            DeleteTourResponse response = DeleteTourResponse.newBuilder().build();
            responseObserver.onNext(response);
        } else {
            logger.errorf("Failed to delete tour with ID %s", request.getTourId());
            responseObserver.onError(Status.NOT_FOUND.withDescription("Tour not found or could not be deleted").asRuntimeException());
        }
        responseObserver.onCompleted();
    }
}
