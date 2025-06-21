package com.retailmax.notifications.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Notificaciones - RetailMax")
                        .description("Microservicio para gestión de pedidos y notificaciones")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("RetailMax Team")
                                .email("support@retailmax.com")
                                .url("https://www.retailmax.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8082")
                                .description("Servidor de desarrollo"),
                        new Server()
                                .url("https://api.retailmax.com")
                                .description("Servidor de producción")
                ));
    }
} 