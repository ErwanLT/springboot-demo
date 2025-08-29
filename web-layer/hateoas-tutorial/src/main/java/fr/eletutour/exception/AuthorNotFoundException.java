package fr.eletutour.exception;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(String msg) {
        super(msg);
    }
}
