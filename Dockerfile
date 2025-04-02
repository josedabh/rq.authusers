# Usar una imagen base de Java
FROM openjdk:21

# Copiar el archivo jar de la aplicación al contenedor
COPY target/authusers-0.0.1-SNAPSHOT.jar /app/authusers.jar

# Exponer el puerto en el que se ejecuta la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/authusers.jar"]
