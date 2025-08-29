package fr.eletutour.exception;

/**
 * Exception spécifique pour la gestion des erreurs liées aux livres.
 * Cette exception contient des informations détaillées sur l'erreur, incluant
 * un code d'erreur et un message personnalisé.
 */
public class BookException extends Exception {

    /**
     * Énumération des types d'erreurs possibles pour les livres.
     * Chaque erreur est associée à un message et un code HTTP.
     */
    public enum BookError {
        BOOK_NOT_FOUND("Book not found", 404),
        BOOK_ALREADY_EXISTS("Book already exists", 409),
        BOOK_INVALID("Book invalid", 400),
        BOOK_INTERNAL_ERROR("Internal error", 500);

        private final String message;
        private final int code;

        /**
         * Constructeur pour une erreur de livre.
         *
         * @param message Le message d'erreur
         * @param code Le code HTTP associé à l'erreur
         */
        BookError(String message, int code) {
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

    private BookError error;
    private String msg;

    /**
     * Constructeur de l'exception avec un type d'erreur et un message.
     *
     * @param error Le type d'erreur
     * @param msg Le message d'erreur
     */
    public BookException(BookError error, String msg) {
        super(msg);
        this.error = error;
    }

    /**
     * Récupère le type d'erreur.
     *
     * @return Le type d'erreur
     */
    public BookError getError() {
        return error;
    }

    /**
     * Définit le type d'erreur.
     *
     * @param error Le nouveau type d'erreur
     */
    public void setError(BookError error) {
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
