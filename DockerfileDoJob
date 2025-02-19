# Base image with Docker and Maven
FROM docker:24.0.7 AS builder

# Install Maven and JDK
RUN apk add --no-cache openjdk17 maven

# Set working directory
WORKDIR /app

# Copy project files
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

# Build the Java application
RUN mvn package -DskipTests

# Log in to Docker Hub and build/push the image
CMD docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD" && \
    docker build -t "$DOCKER_IMAGE" . && \
    docker push "$DOCKER_IMAGE"

