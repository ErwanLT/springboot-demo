package fr.eletutour.exception;

public class TransactionException extends RuntimeException {
    private final String error;

    public TransactionException(String error) {
        super("Transaction validation failed");
        this.error = error;
    }

    public String getErrors() {
        return error;
    }
}