package fr.eletutour.generictutorial.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Generic Tutorial API")
                        .version("1.0")
                        .description("Documentation for the Generic Tutorial API showcasing generic CRUD operations and ProblemDetail error handling.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
