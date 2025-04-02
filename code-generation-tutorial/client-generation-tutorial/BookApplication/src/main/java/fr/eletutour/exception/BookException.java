package fr.eletutour.exception;

public class BookException extends Exception {

    public enum BookError {
        BOOK_NOT_FOUND("Book not found", 404),
        BOOK_ALREADY_EXISTS("Book already exists", 409),
        BOOK_INVALID("Book invalid", 400),
        BOOK_INTERNAL_ERROR("Internal error", 500);

        private final String message;
        private final int code;

        BookError(String message, int code) {
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

    private BookError error;
    private String msg;

    public BookException(BookError error, String msg) {
        super(msg);
        this.error = error;
    }

    public BookError getError() {
        return error;
    }

    public void setError(BookError error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
