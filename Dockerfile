# Dockerfile (place this in the root of your Spring Boot project)

# Stage 1: Use a specific OpenJDK version for a consistent build environment.
# Eclipse Temurin is a good, well-supported OpenJDK distribution.
# Alpine is small, but consider a slim Debian (e.g., temurin:17-jdk-jammy) if Alpine causes issues.
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy the specific JAR file from your host machine into the builder stage
# This assumes the Dockerfile is in the project root, and the JAR is in target/
COPY target/vehicle_tracking-0.0.1-SNAPSHOT.jar application.jar

# Stage 2: Create a minimal runtime image using a JRE for smaller size
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the JAR from the builder stage
COPY --from=builder /app/application.jar ./application.jar

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 7000

# Environment variables for Spring Boot application configuration.
# These will be overridden by docker-compose.yml for service discovery if used.
# It's good to have defaults here for running the image standalone if ever needed.
ENV SPRING_PROFILES_ACTIVE="docker" \
    JAVA_OPTS="-Xms256m -Xmx512m" \
    # Default DB connection (will be overridden by Docker Compose)
    SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/vehicle_tracking_db" \
    SPRING_DATASOURCE_USERNAME="postgres" \
    SPRING_DATASOURCE_PASSWORD="postgres" \
    # Default Redis connection (will be overridden by Docker Compose)
    SPRING_DATA_REDIS_HOST="localhost" \
    SPRING_DATA_REDIS_PORT="6379"

# Define the entry point for the container
# Uses exec form to make your application the main process (PID 1),
# which helps with signal handling (e.g., for graceful shutdowns).
# The shell form "sh -c" is used here to allow JAVA_OPTS environment variable expansion.
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/application.jar"]