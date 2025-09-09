/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of basic-auth-tutorial.
 *
 * basic-auth-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * basic-auth-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with basic-auth-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
