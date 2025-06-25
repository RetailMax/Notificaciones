package com.retailmax.notifications.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("RetailMax Notifications API")
                        .version("1.0.0")
                        .description("API para gestión de notificaciones y pedidos del sistema RetailMax. " +
                                   "Proporciona endpoints para crear, consultar, actualizar y eliminar pedidos, " +
                                   "así como funcionalidades de notificación.")
                        .contact(new Contact()
                                .name("RetailMax Development Team")
                                .email("dev@retailmax.com")
                                .url("https://retailmax.com"))
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