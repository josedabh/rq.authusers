package com.rq.manager.authusers.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * The Class SwaggerConfig.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Custom open API.
     * Si se cambia el puerto en server url hay 
     * que cambiarlo tambien para que funcione las respuestas
     *
     * @return the open API
     */
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Authentication API")
                        .description("API para gestionar los roles de los usuarios")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("José Daniel Bravo Heredia"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Documentación completa")
                        .url("https://localhost:8080/index.html"))
                .servers(List.of(
                        new Server().url("http://localhost:8081")
                        .description("Servidor Local")));
    }
}