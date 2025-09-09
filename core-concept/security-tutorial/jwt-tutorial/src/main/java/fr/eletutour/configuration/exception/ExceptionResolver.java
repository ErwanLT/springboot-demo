/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jwt-tutorial.
 *
 * jwt-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jwt-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jwt-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.configuration.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Gestionnaire global des exceptions de l'application.
 * Ce gestionnaire :
 * <ul>
 *     <li>Intercepte les exceptions de sécurité</li>
 *     <li>Gère les erreurs d'authentification</li>
 *     <li>Gère les erreurs d'autorisation</li>
 *     <li>Gère les erreurs liées aux JWT</li>
 *     <li>Transforme les exceptions en réponses HTTP appropriées</li>
 * </ul>
 */
@ControllerAdvice
public class ExceptionResolver extends ResponseEntityExceptionHandler {

    /**
     * Gère les exceptions de sécurité et les transforme en réponses HTTP.
     * Cette méthode gère :
     * <ul>
     *     <li>BadCredentialsException (401) : Identifiants incorrects</li>
     *     <li>AccountStatusException (403) : Compte verrouillé</li>
     *     <li>AccessDeniedException (403) : Accès non autorisé</li>
     *     <li>SignatureException (403) : Signature JWT invalide</li>
     *     <li>ExpiredJwtException (403) : Token JWT expiré</li>
     *     <li>Autres exceptions (500) : Erreur serveur inconnue</li>
     * </ul>
     *
     * @param exception L'exception à gérer
     * @return Un ProblemDetail contenant les détails de l'erreur
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        return switch (exception) {
            case BadCredentialsException e -> createProblemDetail(401, e.getMessage(), "The username or password is incorrect");
            case AccountStatusException e -> createProblemDetail(403, e.getMessage(), "The account is locked");
            case AccessDeniedException e -> createProblemDetail(403, e.getMessage(), "You are not authorized to access this resource");
            case SignatureException e -> createProblemDetail(403, e.getMessage(), "The JWT signature is invalid");
            case ExpiredJwtException e -> createProblemDetail(403, e.getMessage(), "The JWT token has expired");
            default -> createProblemDetail(500, exception.getMessage(), "Unknown internal server error.");
        };
    }

    /**
     * Crée un objet ProblemDetail pour une réponse d'erreur HTTP.
     *
     * @param status Le code de statut HTTP
     * @param message Le message d'erreur
     * @param description La description détaillée de l'erreur
     * @return Un ProblemDetail configuré
     */
    private ProblemDetail createProblemDetail(int status, String message, String description) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status), message);
        detail.setProperty("description", description);
        return detail;
    }
}
