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