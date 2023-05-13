package com.undina.vacation.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Приложение \"Калькулятор отпускных\".")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("foxundina@gmail.com")
                                                .url("https://github.com/undina13")
                                                .name("Suponeva Irina")
                                )
                );
    }
}
