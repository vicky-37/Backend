package net.engineeringdigest.journalApp.Config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI myCustomConfig() {
                return new OpenAPI()
                                .info(new Info().title("Journal App API").description(
                                                "This is a custom description for helping you understand the working and backend of the app"))
                                .servers(Arrays.asList(
                                                new Server().url("http://localhost:8080").description("local"),
                                                new Server().url("http://localhost:8082").description("another")))
                                .tags(Arrays.asList(
                                                new Tag().name("User Login / Sign-Up"),
                                                new Tag().name("User API")))
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                                .components(new Components().addSecuritySchemes(
                                                "bearerAuth", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .in(SecurityScheme.In.HEADER)
                                                                .name("Authorization")));
        }
}
