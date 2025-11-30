# ---------- Stage 1: Build ----------
FROM gradle:8.14.3-jdk21 AS builder

WORKDIR /home/gradle

COPY gradlew .
COPY gradle/ gradle/

COPY build.gradle.kts settings.gradle.kts gradle.properties ./

COPY server/ server/
COPY catalogueModule/ catalogueModule/

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon || true

COPY . .

RUN ./gradlew :server:shadowJar --no-daemon

# ---------- Stage 2: Run ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /home/gradle/server/build/libs/*-all.jar app.jar

COPY keystore.jks /app/keystore.jks

EXPOSE 8080
EXPOSE 8443

CMD ["java", "-jar", "app.jar"]
