/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of chaos-monkey-tutorial.
 *
 * chaos-monkey-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * chaos-monkey-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with chaos-monkey-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    private MyExceptionHandler() {
        LOGGER.info("MyExceptionHandler actif");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleExceptions(Exception e) {
        return switch (e) {
            case AuthorNotFoundException ex -> buildProblemDetail(
                    HttpStatus.NOT_FOUND,
                    "Author Not Found",
                    "/authors/" + ex.getAuthorId(),
                    "http://localhost:8089/docs/errors/author-not-found.html",
                    ex.getMessage(),
                    Map.of()
            );
            case ArticleNotFoundException ex -> buildProblemDetail(
                    HttpStatus.NOT_FOUND,
                    "Article Not Found",
                    "/articles/" + ex.getArticleId(),
                    "http://localhost:8089/docs/errors/article-not-found.html",
                    ex.getMessage(),
                    Map.of()
            );
            case TimeoutException ex -> buildProblemDetail(
                    HttpStatus.REQUEST_TIMEOUT,
                    "Time Out",
                    "",
                    "",
                    ex.getMessage(),
                    Map.of()
            );
            case InterruptedException ex -> {
                Thread.currentThread().interrupt();
                yield buildProblemDetail(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Erreur interne",
                        "",
                        "",
                        "Le traitement a été interrompu",
                        Map.of()
                );
            }
            case MethodArgumentNotValidException ex -> {
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getFieldErrors().forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );
                yield buildProblemDetail(
                        HttpStatus.BAD_REQUEST,
                        "Validation error",
                        "",
                        "https://datatracker.ietf.org/doc/html/rfc7807",
                        "Un ou plusieurs champs sont invalides.",
                        errors
                );
            }
            case ConstraintViolationException ex -> {
                Map<String, String> violations = new HashMap<>();
                for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                    violations.put(violation.getPropertyPath().toString(), violation.getMessage());
                }
                yield buildProblemDetail(
                        HttpStatus.BAD_REQUEST,
                        "Violation de contrainte",
                        "",
                        "https://datatracker.ietf.org/doc/html/rfc7807",
                        "Les données soumises ne respectent pas les contraintes attendues.",
                        violations
                );
            }
            default -> buildProblemDetail(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erreur interne",
                    "",
                    "",
                    "Une erreur est survenue",
                    Map.of()
            );
        };
    }

    private ProblemDetail buildProblemDetail(HttpStatus httpStatus, String title, String instance, String type, String detail, Map<String, String> properties) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(instance));
        problemDetail.setType(URI.create(type));
        problemDetail.setDetail(detail);
        properties.entrySet().stream()
                .forEach(entry -> problemDetail.setProperty(entry.getKey(), entry.getValue()));
        return problemDetail;
    }

}