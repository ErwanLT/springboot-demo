package fr.eletutour.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur de démonstration pour les endpoints protégés.
 * Ce contrôleur expose :
 * <ul>
 *     <li>Un endpoint /hello qui nécessite une authentification JWT valide</li>
 * </ul>
 */
@RestController
public class HelloController {

    /**
     * Endpoint protégé qui retourne un message de salutation.
     * Cette méthode :
     * <ul>
     *     <li>Nécessite une authentification JWT valide</li>
     *     <li>Vérifie que l'utilisateur est authentifié</li>
     *     <li>Retourne un message de salutation</li>
     * </ul>
     *
     * @return Un message de salutation
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world!";
    }
}
