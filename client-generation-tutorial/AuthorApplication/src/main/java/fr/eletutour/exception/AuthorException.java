package fr.eletutour.exception;

public class AuthorException extends Exception {

    public enum AuthorError {
        AUTHOR_NOT_FOUND("Author not found", 404),
        AUTHOR_ALREADY_EXISTS("Author already exists", 409),
        AUTHOR_INVALID("Author invalid", 400),
        AUTHOR_INTERNAL_ERROR("Internal error", 500);

        private final String message;
        private final int code;

        AuthorError(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }
    }

    private AuthorError error;
    private String msg;

    public AuthorException(AuthorError error, String msg) {
        super(msg);
        this.error = error;
    }

    public AuthorError getError() {
        return error;
    }

    public void setError(AuthorError error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
