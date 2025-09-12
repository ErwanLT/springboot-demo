package fr.eletutour.exception;

import java.util.List;

/**
 * Classe représentant une réponse d'erreur standardisée.
 * Cette classe encapsule une liste de messages d'erreur pour
 * fournir une réponse d'erreur cohérente à travers l'API.
 */
public class ErrorResponse {
    private final List<String> errors;

    /**
     * Constructeur de la réponse d'erreur.
     *
     * @param errors La liste des messages d'erreur
     */
    public ErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    /**
     * Récupère la liste des messages d'erreur.
     *
     * @return La liste des messages d'erreur
     */
    public List<String> getErrors() {
        return errors;
    }
}