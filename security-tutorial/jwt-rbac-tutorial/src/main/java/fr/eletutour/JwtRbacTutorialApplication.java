package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour l'authentification JWT avec RBAC.
 * Cette application illustre :
 * <ul>
 *     <li>L'authentification basée sur JWT (JSON Web Tokens)</li>
 *     <li>La gestion des rôles et des autorisations (RBAC)</li>
 *     <li>La sécurisation des endpoints REST avec Spring Security</li>
 *     <li>La gestion des utilisateurs et des tokens</li>
 * </ul>
 */
@SpringBootApplication
public class JwtRbacTutorialApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration JWT et RBAC.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(JwtRbacTutorialApplication.class);
    }
}
