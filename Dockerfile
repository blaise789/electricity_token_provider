# Builder stage
FROM openjdk:21 AS builder

WORKDIR /app
COPY target/vehicle_tracking-0.0.1-SNAPSHOT.jar application.jar

# Runtime stage
FROM openjdk:21-jre

WORKDIR /app
COPY --from=builder /app/application.jar .

EXPOSE 7000

# Environment variables (will be overridden by docker-compose)
ENV SPRING_PROFILES_ACTIVE=docker \
    JAVA_OPTS="-Xms256m -Xmx512m -Dspring.profiles.active=docker" \
    SPRING_DATASOURCE_URL="jdbc:postgresql://postgres-db-container:5432/vehicle_tracking_db" \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=postgres \
    SPRING_JPA_HIBERNATE_DDL_AUTO=update \
    SPRING_JPA_SHOW_SQL=true \
    SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect \
    SPRING_DATA_REDIS_HOST=redis-cache-container \
    SPRING_DATA_REDIS_PORT=6379

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/application.jar"]