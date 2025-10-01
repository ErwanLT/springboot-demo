package fr.eletutour.exception;

public class BookException extends Exception {

    private final String msg;

    public BookException(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
