# Base image for Java application
FROM openjdk:17-jdk-slim

# Update package list and install Maven
RUN apt-get update && \
    apt-get install -y maven

# Copy application code
COPY . .

# Install Maven dependencies
RUN mvn clean install

# Command to run the application
CMD ["java", "-jar", "library.jar"]