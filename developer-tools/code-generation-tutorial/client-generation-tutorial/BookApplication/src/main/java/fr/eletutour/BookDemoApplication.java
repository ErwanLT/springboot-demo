/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of BookApplication.
 *
 * BookApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BookApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BookApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour la gestion des livres.
 * Cette application utilise Spring Boot pour fournir une API REST
 * permettant de gérer une collection de livres.
 */
@SpringBootApplication
public class BookDemoApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(BookDemoApplication.class, args);
    }
}
