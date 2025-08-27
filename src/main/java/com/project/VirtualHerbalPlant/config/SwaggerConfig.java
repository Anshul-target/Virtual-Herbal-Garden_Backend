package com.project.VirtualHerbalPlant.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Virtual Herbal Plant",
                description = "This is the api documentation for the virtual herbal plant"
        ),
        servers = {
//                @Server(url = "http://localhost:8082", description = "Local Server")
                @Server(url = "https://virtual-herbal-gardenbackend-production.up.railway.app", description = "Production Server")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
