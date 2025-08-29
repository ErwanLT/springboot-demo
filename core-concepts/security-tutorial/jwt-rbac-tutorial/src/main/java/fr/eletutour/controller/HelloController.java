package fr.eletutour.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de démonstration pour les endpoints protégés.
 * Ce contrôleur expose un endpoint :
 * <ul>
 *     <li>/hello : Endpoint protégé nécessitant une authentification</li>
 * </ul>
 */
@RestController
public class HelloController {

    /**
     * Endpoint protégé qui renvoie un message de salutation.
     * Cette méthode :
     * <ul>
     *     <li>Nécessite une authentification JWT valide</li>
     *     <li>Vérifie que l'utilisateur est authentifié</li>
     *     <li>Retourne un message de salutation</li>
     * </ul>
     *
     * @return Le message de salutation
     */
    @GetMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public String sayHello() {
        return "Hello world!";
    }
}
