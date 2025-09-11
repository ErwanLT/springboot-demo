package com.example.r2dbctutorial.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(PersonHandler personHandler) {
        return route(GET("/api/persons"), personHandler::getAllPersons)
                .andRoute(GET("/api/persons/{id}"), personHandler::getPersonById)
                .andRoute(POST("/api/persons"), personHandler::createPerson);
    }
}
