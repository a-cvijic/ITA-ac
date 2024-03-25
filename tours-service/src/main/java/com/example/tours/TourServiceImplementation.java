package com.example.tours;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Singleton;

import com.example.tours.TourServiceGrpc;
import com.example.tours.CreateTourRequest;
import com.example.tours.CreateTourResponse;
import com.example.tours.GetTourRequest;
import com.example.tours.TourResponse;
import com.example.tours.UpdateTourRequest;
import com.example.tours.UpdateTourResponse;
import com.example.tours.DeleteTourRequest;
import com.example.tours.DeleteTourResponse;

@Singleton
@GrpcService
public class TourServiceImplementation extends TourServiceGrpc.TourServiceImplBase {

    @Override
    public void createTour(CreateTourRequest request, StreamObserver<CreateTourResponse> responseObserver) {
        // Implement your business logic here for creating a tour

        // This is a placeholder implementation
        String tourId = "1"; // Replace with the actual ID after saving to your database
        CreateTourResponse response = CreateTourResponse.newBuilder()
                .setTourId(tourId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTour(GetTourRequest request, StreamObserver<TourResponse> responseObserver) {
        // Implement your business logic here for retrieving a tour by its ID

        // This is a placeholder implementation
        TourResponse response = TourResponse.newBuilder()
                .setTourName("Example Tour")
                .setDescription("This is an example tour description.")
                // Add highlights as needed
                .setLocation("Example Location")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateTour(UpdateTourRequest request, StreamObserver<UpdateTourResponse> responseObserver) {
        // Implement your business logic here for updating a tour

        // This is a placeholder implementation
        UpdateTourResponse response = UpdateTourResponse.newBuilder().build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTour(DeleteTourRequest request, StreamObserver<DeleteTourResponse> responseObserver) {
        // Implement your business logic here for deleting a tour

        // This is a placeholder implementation
        DeleteTourResponse response = DeleteTourResponse.newBuilder().build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Add additional service methods if you have more RPC calls

}
