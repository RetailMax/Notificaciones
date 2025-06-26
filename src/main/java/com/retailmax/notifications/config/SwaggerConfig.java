package com.retailmax.notifications.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RetailMax Notifications API")
                        .version("1.0.0")
                        .description("API REST para el sistema de notificaciones y gestión de usuarios de RetailMax. " +
                                   "Esta API proporciona endpoints para la gestión completa de usuarios, " +
                                   "incluyendo operaciones CRUD, validaciones, y manejo de estados. " +
                                   "Diseñada para integrarse con sistemas de retail y e-commerce.")
                        .contact(new Contact()
                                .name("RetailMax Development Team")
                                .email("desarrollo@retailmax.com")
                                .url("https://www.retailmax.com/developers"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082")
                                .description("Servidor de desarrollo local"),
                        new Server()
                                .url("https://dev-api.retailmax.com")
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.retailmax.com")
                                .description("Servidor de producción")
                ))
                .tags(List.of(
                        new Tag()
                                .name("Usuarios")
                                .description("Operaciones relacionadas con la gestión de usuarios del sistema"),
                        new Tag()
                                .name("Pedidos")
                                .description("Operaciones relacionadas con la gestión de pedidos (futuro)"),
                        new Tag()
                                .name("Notificaciones")
                                .description("Operaciones relacionadas con el sistema de notificaciones (futuro)")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", 
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT para autenticación (implementación futura)")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
