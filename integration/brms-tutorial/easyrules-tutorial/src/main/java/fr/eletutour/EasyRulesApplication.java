package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour l'utilisation d'Easy Rules.
 * Cette application utilise Spring Boot et Easy Rules pour implémenter
 * un moteur de règles métier basé sur le pattern Easy Rules.
 */
@SpringBootApplication
public class EasyRulesApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration Easy Rules.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(EasyRulesApplication.class, args);
    }
}
