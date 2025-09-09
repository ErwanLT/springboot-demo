/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of gatling-tutorial.
 *
 * gatling-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * gatling-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with gatling-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.gatling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application de démonstration Gatling.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation de Gatling pour les tests de performance</li>
 *     <li>La simulation de charges sur les endpoints REST</li>
 *     <li>La génération de rapports de performance</li>
 * </ul>
 */
@SpringBootApplication
public class GatlingApplication {
    /**
     * Point d'entrée de l'application.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(GatlingApplication.class, args);
    }
}
