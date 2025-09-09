/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of async-tutorial.
 *
 * async-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * async-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with async-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.async.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.concurrent.CompletionException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompletionException.class)
    public ResponseEntity<ProblemDetail> handleCompletionException(CompletionException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        // Le message de la cause racine est souvent plus informatif.
        String detail = (ex.getCause() != null) ? ex.getCause().getMessage() : "Une erreur est survenue lors d'une opération asynchrone.";

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Erreur Asynchrone");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleAllExceptions(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Erreur Interne du Serveur");
        problemDetail.setInstance(URI.create(request.getDescription(false).replace("uri=", "")));

        return ResponseEntity.status(status).body(problemDetail);
    }
}