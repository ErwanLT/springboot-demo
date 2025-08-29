package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application principale de démonstration pour l'authentification basique Spring Security.
 * Cette application illustre :
 * <ul>
 *     <li>La configuration de l'authentification basique</li>
 *     <li>La gestion des utilisateurs en mémoire</li>
 *     <li>La sécurisation des endpoints REST</li>
 * </ul>
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class AuditTutorialApplication {
    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration de sécurité.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(AuditTutorialApplication.class, args);
    }
}