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

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Gestionnaire global des exceptions pour l'API.
 * Cette classe intercepte les exceptions non gérées et les transforme
 * en réponses HTTP appropriées avec des messages d'erreur formatés.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions de type IllegalArgumentException.
     * Retourne une réponse 400 avec le message d'erreur.
     *
     * @param ex L'exception IllegalArgumentException
     * @return Une ResponseEntity avec le statut 400 et le message d'erreur
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions de type TransactionException.
     * Retourne une réponse 400 avec une liste de messages d'erreur.
     *
     * @param ex L'exception TransactionException
     * @return Une ResponseEntity avec le statut 400 et la liste des messages d'erreur
     */
    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorResponse> handleTransactionException(TransactionException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrors());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}