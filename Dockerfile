# Use the official Maven image to create a build environment
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project object model file to the container
COPY pom.xml .

# Copy the rest of the project files to the container
COPY src ./src/

# Build the project
RUN mvn clean package -DskipTests

# Use AdoptOpenJDK as base image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build environment to the container
COPY --from=build /app/target/*.jar app.jar

# Expose the port that the Spring Boot application will run on
EXPOSE 8070

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]