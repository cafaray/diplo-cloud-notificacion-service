# Use an official OpenJDK runtime as a parent image
# FROM arm64v8/openjdk:17-ea-16-jdk
# FROM khipu/openjdk17-alpine:latest
FROM docker.io/khipu/openjdk17-alpine:latest

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the target directory into the container
COPY target/notificacion-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application will listen on
EXPOSE 8081

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
