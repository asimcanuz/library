# Base image for PostgreSQL
FROM postgres:latest

# Set environment variables
ENV POSTGRES_DB=mydb
ENV POSTGRES_USER=myuser
ENV POSTGRES_PASSWORD=mypassword

# Expose the PostgreSQL port
EXPOSE 5432

# Base image for Java application
FROM adoptopenjdk:17-jre-hotspot

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/library.jar /app/library.jar

# Expose the port your application runs on
EXPOSE 9090

# Command to run the application
CMD ["java", "-jar", "library.jar"]
