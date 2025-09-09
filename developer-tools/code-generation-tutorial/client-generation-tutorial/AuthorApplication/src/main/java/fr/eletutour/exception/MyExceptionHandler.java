/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of AuthorApplication.
 *
 * AuthorApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuthorApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuthorApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * Gestionnaire global des exceptions de l'application.
 * Cette classe intercepte les exceptions levées dans les contrôleurs
 * et les transforme en réponses HTTP appropriées.
 */
@ControllerAdvice
public class MyExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    /**
     * Constructeur privé du gestionnaire d'exceptions.
     * Initialise le logger et enregistre l'activation du gestionnaire.
     */
    private MyExceptionHandler() {
        LOGGER.info("MyExceptionHandler actif");
    }

    /**
     * Gère les exceptions de type AuthorException.
     * Transforme l'exception en une réponse HTTP avec le code d'erreur
     * et le message appropriés.
     *
     * @param ae L'exception AuthorException à gérer
     * @return Une ResponseEntity contenant la réponse d'erreur formatée
     */
    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<Object> handleAuthorException(AuthorException ae) {
        return ResponseEntity.status(ae.getError().getCode())
                .body(new ErrorResponse(HttpStatus.resolve(ae.getError().getCode()), ae.getError().getMessage(), List.of(ae.getMessage())));
    }

}