package com.rq.manager.authusers.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

/**
 * The Class SwaggerConfig.
 */
@Configuration
public class SwaggerConfig {

    /** The port. */
    @Value("${server.port}")
    private String port;

    /**
     * Custom open API.
     *
     * @return the open API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .externalDocs(apiDocumentation())
                .servers(apiServers())
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // Aplica seguridad
                .components(securityComponents());
    }

    /**
     * Api info.
     *
     * @return the info
     */
    private Info apiInfo() {
        return new Info()
                .title("Authentication API")
                .description("API para gestionar los roles de los usuarios")
                .version("1.0.0")
                .contact(new Contact().name("José Daniel Bravo Heredia"));
    }

    /**
     * Api extern documentation.
     *
     * @return the external documentation
     */
    private ExternalDocumentation apiDocumentation() {
        return new ExternalDocumentation()
                .description("Documentación completa")
                .url("http://vps-5060784-x.dattaweb.com:8080/" + port + "/index.html");
    }

    /**
     * Configuration servers.
     *
     * @return the list servers
     */
    private List<Server> apiServers() {
        return List.of(new Server()
                .url("http://localhost:" + port)
                .description("Servidor Local"),
                new Server()
                .url("http://vps-5060784-x.dattaweb.com:8080")
                .description("Servidor en produccion"));
    }

    /**
     * Security components.
     *
     * @return the components
     */
    private Components securityComponents() {
        return new Components()
                .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }
}
