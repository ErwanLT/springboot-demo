package fr.eletutour.service;

import fr.eletutour.configuration.GreetingProperties;

public class GreetingService {

    private final GreetingProperties properties;

    public GreetingService(GreetingProperties properties) {
        this.properties = properties;
    }

    public String greet() {
        return properties.getMessage();
    }
}
