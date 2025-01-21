# Use a base image with Java 21
FROM eclipse-temurin:21-jre AS builder

# Set the working directory for the build stage to an absolute path
WORKDIR /app/extracted

# Add the application JAR to the container
ADD target/*.jar /app/app.jar

# Extract layers using Spring Boot's layertools
RUN java -Djarmode=layertools -jar /app/app.jar extract

# Final image with Java 21 runtime
FROM eclipse-temurin:21-jre

# Set the working directory for the application runtime to an absolute path
WORKDIR /app/application

# Copy the extracted layers from the build stage
COPY --from=builder /app/extracted/dependencies/ ./
COPY --from=builder /app/extracted/spring-boot-loader/ ./
COPY --from=builder /app/extracted/snapshot-dependencies/ ./
COPY --from=builder /app/extracted/application/ ./

# Set the entry point for the application
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
