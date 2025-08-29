package fr.eletutour.exception;

/**
 * Exception levée lorsqu'une transaction bancaire échoue.
 * Cette exception est utilisée pour signaler qu'une opération de dépôt ou de retrait
 * n'a pas pu être effectuée pour diverses raisons (solde insuffisant, limite dépassée, etc.).
 */
public class TransactionFailedException extends RuntimeException {
    
    /**
     * Constructeur de l'exception avec un message d'erreur.
     *
     * @param message Le message décrivant la raison de l'échec de la transaction
     */
    public TransactionFailedException(String message) {
        super(message);
    }
}