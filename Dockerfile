# Stage 1: Build the Spring Boot JAR
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Use Gradle wrapper and cache dependencies
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ./gradlew bootJar --no-daemon


# Use a base image with JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the JAR file (built by Gradle)
COPY build/libs/recipeManagement-0.0.1-SNAPSHOT.jar recipeManagement-0.0.1-SNAPSHOT.jar

# Expose the default Spring Boot port
EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "recipeManagement-0.0.1-SNAPSHOT.jar"]