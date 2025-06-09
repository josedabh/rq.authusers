# === Etapa 1: build con Maven y Java 21 ===
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom.xml y descarga dependencias primero (cache)
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw ./
RUN ./mvnw dependency:go-offline

# Copia el resto del proyecto y compila
COPY src ./src
RUN ./mvnw clean package -DskipTests

# === Etapa 2: imagen final solo con el JAR ===
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copia el JAR desde la etapa anterior
COPY --from=build /app/target/authusers-0.0.1-SNAPSHOT.jar /app/authusers.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "authusers.jar"]
