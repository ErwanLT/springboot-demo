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
