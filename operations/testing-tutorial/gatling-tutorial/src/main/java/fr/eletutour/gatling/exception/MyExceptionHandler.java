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