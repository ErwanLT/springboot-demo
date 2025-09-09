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
package fr.eletutour.gatling.exception;

/**
 * Exception levée lorsqu'un auteur n'est pas trouvé dans le système.
 * Cette exception est utilisée pour :
 * <ul>
 *     <li>Signaler l'absence d'un auteur lors d'une recherche</li>
 *     <li>Gérer les cas d'erreur liés aux auteurs inexistants</li>
 * </ul>
 */
public class AuthorNotFoundException extends Exception {

    /**
     * Constructeur de l'exception.
     *
     * @param msg Le message d'erreur détaillant la raison de l'exception
     */
    public AuthorNotFoundException(String msg) {
        super(msg);
    }
}
