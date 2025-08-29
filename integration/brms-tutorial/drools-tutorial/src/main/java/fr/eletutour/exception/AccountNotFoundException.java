package fr.eletutour.exception;

/**
 * Exception levée lorsqu'un compte bancaire n'est pas trouvé dans le système.
 * Cette exception est utilisée pour signaler qu'une opération a été tentée
 * sur un compte qui n'existe pas.
 */
public class AccountNotFoundException extends RuntimeException {
    
    /**
     * Constructeur de l'exception avec un message d'erreur.
     *
     * @param message Le message décrivant l'erreur
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}