FROM gradle:8.5.0-jdk21 AS builder

WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN dos2unix gradlew || true
RUN chmod +x gradlew
RUN ./gradlew --no-daemon dependencies --stacktrace --info

COPY src /app/src

RUN ./gradlew bootJar --no-daemon --stacktrace

FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]