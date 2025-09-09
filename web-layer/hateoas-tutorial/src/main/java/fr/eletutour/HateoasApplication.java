/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of hateoas-tutorial.
 *
 * hateoas-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * hateoas-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with hateoas-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application HATEOAS.
 * Cette application illustre :
 * <ul>
 *     <li>L'utilisation de HATEOAS pour l'hypermedia</li>
 *     <li>La création d'API REST avec des liens hypermedia</li>
 *     <li>La navigation entre les ressources</li>
 *     <li>L'auto-découverte des actions disponibles</li>
 * </ul>
 */
@SpringBootApplication
public class HateoasApplication {
    /**
     * Point d'entrée de l'application HATEOAS.
     * Démarre le serveur Spring Boot avec la configuration par défaut.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(HateoasApplication.class, args);
    }
}
