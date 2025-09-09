/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of AuthorApplication.
 *
 * AuthorApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuthorApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuthorApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Application principale de démonstration pour la gestion des auteurs.
 * Cette application utilise Spring Boot et OpenFeign pour la communication
 * avec d'autres services.
 */
@SpringBootApplication
@EnableFeignClients
public class AuthorDemoApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(AuthorDemoApplication.class, args);
    }
}
