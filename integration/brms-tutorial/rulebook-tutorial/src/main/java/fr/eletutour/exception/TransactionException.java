package fr.eletutour.exception;

import java.util.List;

/**
 * Exception levée lorsqu'une transaction bancaire échoue.
 * Cette exception encapsule une liste de messages d'erreur détaillant
 * les raisons de l'échec de la transaction.
 */
public class TransactionException extends RuntimeException {
    private final List<String> errors;

    /**
     * Constructeur de l'exception de transaction.
     *
     * @param errors La liste des messages d'erreur détaillant l'échec
     */
    public TransactionException(List<String> errors) {
        super("Transaction validation failed");
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