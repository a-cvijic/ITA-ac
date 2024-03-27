# Use the official Quarkus runtime image as a base
FROM adoptopenjdk:latest

# Set the working directory in the container
WORKDIR /work/

# Copy the package jar file into the container at /work
COPY target/quarkus-app/*.jar /work/application.jar

# Expose the port on which your service will run
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "/work/application.jar"]
