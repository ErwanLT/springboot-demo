/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of rulebook-tutorial.
 *
 * rulebook-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * rulebook-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with rulebook-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application principale de démonstration pour l'utilisation de RuleBook.
 * Cette application utilise Spring Boot et RuleBook pour implémenter
 * un moteur de règles métier basé sur le pattern RuleBook.
 */
@SpringBootApplication
public class RuleBookApplication {

    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration RuleBook.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(RuleBookApplication.class, args);
    }
}
