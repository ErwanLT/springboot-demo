/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jwt-rbac-tutorial.
 *
 * jwt-rbac-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jwt-rbac-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jwt-rbac-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
