package fr.eletutour.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Classe représentant une réponse d'erreur standardisée.
 * Cette classe est utilisée pour formater les réponses d'erreur de l'API
 * avec un statut HTTP, un message et une liste de causes.
 */
public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private List<String> cause;

    /**
     * Constructeur de la réponse d'erreur.
     *
     * @param status Le statut HTTP de l'erreur
     * @param message Le message d'erreur principal
     * @param cause La liste des causes de l'erreur
     */
    public ErrorResponse(HttpStatus status, String message, List<String> cause) {
        this.status = status;
        this.message = message;
        this.cause = cause;
    }

    /**
     * Récupère le statut HTTP de l'erreur.
     *
     * @return Le statut HTTP
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * Définit le statut HTTP de l'erreur.
     *
     * @param status Le nouveau statut HTTP
     */
    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    /**
     * Récupère le message d'erreur principal.
     *
     * @return Le message d'erreur
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message d'erreur principal.
     *
     * @param message Le nouveau message d'erreur
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Récupère la liste des causes de l'erreur.
     *
     * @return La liste des causes
     */
    public List<String> getCause() {
        return cause;
    }

    /**
     * Définit la liste des causes de l'erreur.
     *
     * @param cause La nouvelle liste des causes
     */
    public void setCause(List<String> cause) {
        this.cause = cause;
    }
}
