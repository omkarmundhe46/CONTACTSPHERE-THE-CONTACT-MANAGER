# Use official Maven image to build Spring Boot app
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and install dependancy
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use lightweight JRE image for running the application
FROM eclipse-temurin:21-jre

# Set working directory
WORKDIR /app

# Copy the built JAR file from target directory
COPY --from=build /app/target/contactsphere-0.0.1-SNAPSHOT.jar .

# Expose the application port (must match server.port in application.properties)
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/contactsphere-0.0.1-SNAPSHOT.jar"]
