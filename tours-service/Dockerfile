FROM eclipse-temurin:21-jdk

# Set the location of the deployments
ENV DEPLOYMENTS_DIR=/deployments

# Copy your JAR file to the container
COPY target/quarkus-app /deployments/

# Set the working directory to the deployments directory
WORKDIR $DEPLOYMENTS_DIR

# Run Quarkus runner JAR
CMD ["java", "-jar", "quarkus-run.jar"]

# Optionally, you can expose the port if your application needs external access
EXPOSE 8080
