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
package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour l'authentification JWT avec RBAC.
 * Cette application illustre :
 * <ul>
 *     <li>L'authentification basée sur JWT (JSON Web Tokens)</li>
 *     <li>La gestion des rôles et des autorisations (RBAC)</li>
 *     <li>La sécurisation des endpoints REST avec Spring Security</li>
 *     <li>La gestion des utilisateurs et des tokens</li>
 * </ul>
 */
@SpringBootApplication
public class JwtRbacTutorialApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration JWT et RBAC.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(JwtRbacTutorialApplication.class);
    }
}
