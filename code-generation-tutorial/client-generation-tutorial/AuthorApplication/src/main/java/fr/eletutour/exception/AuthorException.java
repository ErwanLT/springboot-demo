package fr.eletutour.exception;

/**
 * Exception spécifique pour la gestion des erreurs liées aux auteurs.
 * Cette exception contient des informations détaillées sur l'erreur, incluant
 * un code d'erreur et un message personnalisé.
 */
public class AuthorException extends Exception {

    /**
     * Énumération des types d'erreurs possibles pour les auteurs.
     * Chaque erreur est associée à un message et un code HTTP.
     */
    public enum AuthorError {
        AUTHOR_NOT_FOUND("Author not found", 404),
        AUTHOR_ALREADY_EXISTS("Author already exists", 409),
        AUTHOR_INVALID("Author invalid", 400),
        AUTHOR_INTERNAL_ERROR("Internal error", 500);

        private final String message;
        private final int code;

        /**
         * Constructeur pour une erreur d'auteur.
         *
         * @param message Le message d'erreur
         * @param code Le code HTTP associé à l'erreur
         */
        AuthorError(String message, int code) {
            this.message = message;
            this.code = code;
        }

        /**
         * Récupère le message d'erreur.
         *
         * @return Le message d'erreur
         */
        public String getMessage() {
            return message;
        }

        /**
         * Récupère le code HTTP de l'erreur.
         *
         * @return Le code HTTP
         */
        public int getCode() {
            return code;
        }
    }

    private AuthorError error;
    private String msg;

    /**
     * Constructeur de l'exception avec un type d'erreur et un message.
     *
     * @param error Le type d'erreur
     * @param msg Le message d'erreur
     */
    public AuthorException(AuthorError error, String msg) {
        super(msg);
        this.error = error;
    }

    /**
     * Récupère le type d'erreur.
     *
     * @return Le type d'erreur
     */
    public AuthorError getError() {
        return error;
    }

    /**
     * Définit le type d'erreur.
     *
     * @param error Le nouveau type d'erreur
     */
    public void setError(AuthorError error) {
        this.error = error;
    }

    /**
     * Récupère le message d'erreur.
     *
     * @return Le message d'erreur
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Définit le message d'erreur.
     *
     * @param msg Le nouveau message d'erreur
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
