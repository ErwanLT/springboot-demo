package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour la gestion des livres.
 * Cette application utilise Spring Boot pour fournir une API REST
 * permettant de gérer une collection de livres.
 */
@SpringBootApplication
public class BookDemoApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(BookDemoApplication.class, args);
    }
}
