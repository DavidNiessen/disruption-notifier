FROM gradle:8.13.0-jdk21 AS builder
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./

COPY src ./src
RUN gradle build -x test

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
