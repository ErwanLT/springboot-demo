/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of drools-tutorial.
 *
 * drools-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * drools-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with drools-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.exception;

import fr.eletutour.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Gestion centralisée des exceptions pour l'API.
 * Cette classe intercepte toutes les exceptions non gérées et les transforme
 * en réponses HTTP appropriées avec des messages d'erreur formatés.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Gère les exceptions de type AccountNotFoundException.
     * Retourne une réponse 404 avec le message d'erreur.
     *
     * @param ex L'exception AccountNotFoundException
     * @return Une ResponseEntity avec le statut 404 et le message d'erreur
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex) {
        logger.warn("Erreur: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Gère les exceptions de type TransactionFailedException.
     * Retourne une réponse 400 avec une transaction échouée.
     *
     * @param ex L'exception TransactionFailedException
     * @return Une ResponseEntity avec le statut 400 et les détails de la transaction échouée
     */
    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<Transaction> handleTransactionFailed(TransactionFailedException ex) {
        logger.warn("Transaction échouée: {}", ex.getMessage());
        Transaction failed = new Transaction();
        failed.setApproved(false);
        failed.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failed);
    }

    /**
     * Gère les exceptions de validation des arguments de méthode.
     * Retourne une réponse 400 avec les détails des erreurs de validation.
     *
     * @param ex L'exception MethodArgumentNotValidException
     * @return Une ResponseEntity avec le statut 400 et les messages d'erreur de validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Erreur de validation: {}", ex.getMessage());
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Données invalides");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    /**
     * Gère toutes les autres exceptions non gérées.
     * Retourne une réponse 500 avec un message générique.
     *
     * @param ex L'exception non gérée
     * @return Une ResponseEntity avec le statut 500 et un message d'erreur générique
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        logger.error("Erreur inattendue", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue");
    }
}