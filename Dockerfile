# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Add the JAR file to the container
COPY target/PMS-Portal-0.0.1-SNAPSHOT.jar pmsportal.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "pmsportal.jar"]
