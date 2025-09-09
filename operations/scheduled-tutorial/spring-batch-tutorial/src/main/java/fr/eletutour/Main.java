/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of spring-batch-tutorial.
 *
 * spring-batch-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * spring-batch-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with spring-batch-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Classe principale de l'application Spring Batch.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation de Spring Batch pour le traitement par lots</li>
 *     <li>La configuration des jobs et des steps</li>
 *     <li>Le traitement des données en masse</li>
 *     <li>La planification des tâches avec @EnableScheduling</li>
 * </ul>
 */
@SpringBootApplication
@EnableScheduling
public class Main {

    /**
     * Point d'entrée de l'application Spring Batch.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}