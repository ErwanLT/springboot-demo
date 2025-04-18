package fr.eletutour.exception;

import java.util.List;

public class TransactionException extends RuntimeException {
    private final List<String> errors;

    public TransactionException(List<String> errors) {
        super("Transaction validation failed");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}