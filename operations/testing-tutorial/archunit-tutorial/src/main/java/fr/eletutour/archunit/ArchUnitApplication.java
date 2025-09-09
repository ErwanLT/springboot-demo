/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of archunit-tutorial.
 *
 * archunit-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * archunit-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with archunit-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.archunit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application de démonstration ArchUnit.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation d'ArchUnit pour tester l'architecture</li>
 *     <li>La vérification des dépendances entre couches</li>
 *     <li>La validation des règles d'architecture</li>
 * </ul>
 */
@SpringBootApplication
public class ArchUnitApplication {

    /**
     * Point d'entrée de l'application.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(ArchUnitApplication.class, args);
    }
}
