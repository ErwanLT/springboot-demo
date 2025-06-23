package fr.eletutour.pokedex;

import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Theme("my-theme")
public class PokedexApplication implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(PokedexApplication.class, args);
    }
}

