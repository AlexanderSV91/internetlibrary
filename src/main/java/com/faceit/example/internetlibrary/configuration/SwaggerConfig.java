package com.faceit.example.internetlibrary.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;


public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Internet Library Api")
                        .version("1.0.0")
                        .contact(new Contact()
                                .email("alex.shalamov@faceit-team.com")
                                .url("https://alexander-shalamov.ua")
                                .name("Alexander Shalamov")
                        ));
    }
}
