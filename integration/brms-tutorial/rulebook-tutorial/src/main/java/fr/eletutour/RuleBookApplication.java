package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour l'utilisation de RuleBook.
 * Cette application utilise Spring Boot et RuleBook pour implémenter
 * un moteur de règles métier basé sur le pattern RuleBook.
 */
@SpringBootApplication
public class RuleBookApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration RuleBook.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(RuleBookApplication.class, args);
    }
}
