package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour l'utilisation de Drools.
 * Cette application utilise Spring Boot et Drools pour implémenter
 * un moteur de règles métier.
 */
@SpringBootApplication
public class DroolsApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration Drools.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(DroolsApplication.class, args);
    }
}
