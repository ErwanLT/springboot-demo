package fr.eletutour.controller.internal;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints techniques, jamais destinés aux consommateurs de l'API.
 * @Hidden masque l'ensemble du contrôleur dans Swagger UI et dans
 * le contrat OpenAPI généré, tout en gardant les endpoints fonctionnels.
 */
@Hidden
@RestController
@RequestMapping("/api/internal")
public class InternalController {

    @PostMapping("/cache/refresh")
    public void refreshCache() {
        // purge du cache applicatif
    }

    @PostMapping("/debug/reset")
    public void debugReset() {
        // à utiliser uniquement en cas d'urgence, jamais documenté
    }
}
