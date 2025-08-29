package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application principale de démonstration pour la gestion des auteurs.
 * Cette application utilise Spring Boot et OpenFeign pour la communication
 * avec d'autres services.
 */
@SpringBootApplication
@EnableFeignClients
public class AuthorDemoApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthorDemoApplication.class, args);
    }
}
