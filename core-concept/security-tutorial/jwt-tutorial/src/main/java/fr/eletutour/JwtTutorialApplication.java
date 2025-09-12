package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application de démonstration JWT.
 * Cette application illustre :
 * <ul>
 *     <li>L'authentification basée sur JWT</li>
 *     <li>La génération et validation des tokens</li>
 *     <li>La sécurisation des endpoints REST</li>
 * </ul>
 */
@SpringBootApplication
public class JwtTutorialApplication {

    /**
     * Point d'entrée de l'application.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(JwtTutorialApplication.class);
    }
}
