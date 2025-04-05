FROM gradle:8.13.0-jdk21 AS builder
WORKDIR /app

RUN apt-get update && apt-get install -y wget gnupg2 curl unzip \
  && wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - \
  && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list \
  && apt-get update && apt-get install -y google-chrome-stable

COPY build.gradle.kts settings.gradle.kts ./

COPY src ./src
RUN gradle build -x test

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
