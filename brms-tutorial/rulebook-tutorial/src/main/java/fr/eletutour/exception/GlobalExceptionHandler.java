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