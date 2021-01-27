package com.faceit.example.internetlibrary.configuration;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDesciption,
                                 @Value("${application-version}") String appVersion) {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes("basic",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("basic")
                                                .in(SecurityScheme.In.HEADER)
                                                .name("Authorization")))
                .info(
                        new Info()
                                .title("Internet Library Api")
                                .version(appVersion)
                                .description(appDesciption)
                                .contact(new Contact()
                                        .email("alex.shalamov@faceit-team.com")
                                        .url("https://alexander-shalamov.ua")
                                        .name("Alexander Shalamov")
                                ));
    }
}
