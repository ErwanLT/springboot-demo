package fr.eletutour.gatling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application de démonstration Gatling.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation de Gatling pour les tests de performance</li>
 *     <li>La simulation de charges sur les endpoints REST</li>
 *     <li>La génération de rapports de performance</li>
 * </ul>
 */
@SpringBootApplication
public class GatlingApplication {
    /**
     * Point d'entrée de l'application.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(GatlingApplication.class, args);
    }
}
