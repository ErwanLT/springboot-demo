package fr.eletutour.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.component.flow.ComponentFlow;

@Configuration
public class ShellConfig {

    @Bean
    public ComponentFlow componentFlow(ComponentFlow.Builder builder) {
        return builder.build();
    }
}