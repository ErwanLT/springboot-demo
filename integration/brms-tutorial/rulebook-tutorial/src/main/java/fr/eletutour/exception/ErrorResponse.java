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
package fr.eletutour.exception;

import java.util.List;

/**
 * Classe représentant une réponse d'erreur standardisée.
 * Cette classe encapsule une liste de messages d'erreur pour
 * fournir une réponse d'erreur cohérente à travers l'API.
 */
public class ErrorResponse {
    private final List<String> errors;

    /**
     * Constructeur de la réponse d'erreur.
     *
     * @param errors La liste des messages d'erreur
     */
    public ErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    /**
     * Récupère la liste des messages d'erreur.
     *
     * @return La liste des messages d'erreur
     */
    public List<String> getErrors() {
        return errors;
    }
}