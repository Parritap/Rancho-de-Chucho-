# Use official Gradle image as base
FROM gradle:latest AS build

# Set working directory
WORKDIR /home/gradle/src

# Copy Gradle project
COPY --chown=gradle:gradle . .

# Install Java manually
RUN apt-get update && apt-get install -y openjdk-17-jdk

# Build Spring Boot application using Gradle wrapper
RUN ./gradlew clean bootJar

# Use another stage for the final image
FROM eclipse-temurin:17-jdk-jammy

# Set timezone (e.g., to "America/New_York")
ENV TZ=America/Bogota

# Copy JAR file from the build stage
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]