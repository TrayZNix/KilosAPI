package com.grupocinco.kilosapi.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OpenApiDocsConfig {
    @Bean
    public OpenAPI getOpenAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .version("1.0")
                        .contact(new Contact()
                                .name("Grupo 5")
                                .url("https://github.com/TrayZNix/KilosAPI"))
                        .license(new License().name("MIT License").url("https://github.com/TrayZNix/KilosAPI/blob/main/LICENSE"))
                        .title("KiloAPI V1")
                        .description("KiloAPI es un API que servirá como ayuda para coordinar la operación solidaria del kilo"));

    }

}

