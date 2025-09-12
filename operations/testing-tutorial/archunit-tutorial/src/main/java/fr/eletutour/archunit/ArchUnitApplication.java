package fr.eletutour.archunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application de démonstration ArchUnit.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation d'ArchUnit pour tester l'architecture</li>
 *     <li>La vérification des dépendances entre couches</li>
 *     <li>La validation des règles d'architecture</li>
 * </ul>
 */
@SpringBootApplication
public class ArchUnitApplication {

    /**
     * Point d'entrée de l'application.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(ArchUnitApplication.class, args);
    }
}
