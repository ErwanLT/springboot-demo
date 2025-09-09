/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jwt-tutorial.
 *
 * jwt-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jwt-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jwt-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
