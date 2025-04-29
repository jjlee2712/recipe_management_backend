# Build the Spring Boot JAR
FROM gradle:8.4-jdk17-alpine AS builder
WORKDIR /app
COPY . .
RUN ./gradlew bootJar --no-daemon


# Use a base image with JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file (built by Gradle)
COPY build/libs/*.jar recipeManagement-0.0.1-SNAPSHOT.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "recipeManagement-0.0.1-SNAPSHOT.jar"]