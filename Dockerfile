# Usar una imagen base de Java
FROM openjdk:21-jdk-slim

# Crear directorio de la aplicación
RUN mkdir -p /app

# Copiar el JAR de la aplicación
COPY target/authusers-0.0.1-SNAPSHOT.jar /app/authusers.jar

# Establecer el directorio de trabajo
WORKDIR /app

# Exponer el puerto de la aplicación
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "authusers.jar"]
