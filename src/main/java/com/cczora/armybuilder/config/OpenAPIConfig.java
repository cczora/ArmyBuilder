package com.cczora.armybuilder.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("ArmyBuilder API")
                        .description("An API to be used for the Grimdark Army Builder app.\nFor testing purposes, please use the username 'Test User'.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Cody Czora")
                                .email("cczora@gmail.com")
                                .url("https://github.com/cczora")));
    }

}
