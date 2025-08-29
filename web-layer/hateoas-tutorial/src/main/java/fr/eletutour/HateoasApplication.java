package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application HATEOAS.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation de HATEOAS pour l'hypermedia</li>
 *     <li>La création d'API REST avec des liens hypermedia</li>
 *     <li>La navigation entre les ressources</li>
 *     <li>L'auto-découverte des actions disponibles</li>
 * </ul>
 */
@SpringBootApplication
public class HateoasApplication {
    /**
     * Point d'entrée de l'application HATEOAS.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(HateoasApplication.class, args);
    }
}
