package fr.eletutour.gatling.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Gestionnaire global des exceptions de l'application.
 * Ce gestionnaire :
 * <ul>
 *     <li>Intercepte les exceptions levées dans les contrôleurs</li>
 *     <li>Transforme les exceptions en réponses HTTP appropriées</li>
 *     <li>Gère la journalisation des erreurs</li>
 * </ul>
 */
@ControllerAdvice
public class MyExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * Constructeur du gestionnaire d'exceptions.
     * Initialise le logger et enregistre l'activation du gestionnaire.
     */
    private MyExceptionHandler() {
        LOGGER.info("MyExceptionHandler actif");
    }

    /**
     * Gère les exceptions de type AuthorNotFoundException.
     * Transforme l'exception en réponse HTTP 404 (Not Found).
     *
     * @param exception L'exception à gérer
     * @return Une réponse HTTP avec le message d'erreur
     */
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<Object> handleAuthorNotFound(AuthorNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}