# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (better cache)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot JAR
RUN mvn clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose port (Render uses PORT env var but good to document)
EXPOSE 8080

# Optional JVM flags can be passed via JAVA_OPTS
ENV JAVA_OPTS=""

# Run with prod profile so it uses Postgres config
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar --spring.profiles.active=prod"]
