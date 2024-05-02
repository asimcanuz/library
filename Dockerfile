# Base image for PostgreSQL
FROM postgres:latest

# Set environment variables
ENV POSTGRES_DB=mydb
ENV POSTGRES_USER=myuser
ENV POSTGRES_PASSWORD=mypassword

# Expose the PostgreSQL port
EXPOSE 5432

# Base image for Java application
FROM adoptium/openjdk:17-slim

# Set working directory inside the container
WORKDIR /app

## Copy application code
COPY . .

# Install Maven dependencies (adjust based on your build tool)
RUN mvn clean install

# Expose the port your application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "library.jar"]
