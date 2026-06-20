package fr.eletutour.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Découpe la documentation Swagger en plusieurs groupes navigables.
 * Chaque groupe expose son propre contrat sur /v3/api-docs/{group}.
 *
 * Le contrôleur InternalController (@Hidden) n'apparaît dans aucun
 * groupe : il reste fonctionnel mais invisible dans la documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Groupe par défaut : toutes les API non masquées (@Hidden exclu
     * automatiquement par springdoc, quel que soit le pathsToMatch).
     * Pratique comme point d'entrée unique avant de filtrer par version.
     */
    @Bean
    public GroupedOpenApi defaultApi() {
        return GroupedOpenApi.builder()
                .group("default")
                .displayName("Toutes les API")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi v1Api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .displayName("v1 (dépréciée)")
                .pathsToMatch("/api/v1/**")
                .build();
    }

    @Bean
    public GroupedOpenApi v2Api() {
        return GroupedOpenApi.builder()
                .group("v2")
                .displayName("v2 (actuelle)")
                .pathsToMatch("/api/v2/**")
                .build();
    }
}
