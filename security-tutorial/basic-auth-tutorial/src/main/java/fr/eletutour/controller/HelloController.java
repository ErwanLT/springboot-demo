package fr.eletutour.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST de démonstration.
 * Ce contrôleur expose deux endpoints :
 * <ul>
 *     <li>/hello : Requiert une authentification</li>
 *     <li>/goodbye : Accessible publiquement</li>
 * </ul>
 */
@RestController
public class HelloController {

    /**
     * Endpoint protégé qui renvoie un message de salutation.
     * Nécessite une authentification pour y accéder.
     *
     * @return Le message de salutation
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world!";
    }

    /**
     * Endpoint public qui renvoie un message d'au revoir.
     * Accessible sans authentification.
     *
     * @return Le message d'au revoir
     */
    @GetMapping("/goodbye")
    public String sayGoodbye() {
        return "GoodBye world!";
    }
}
