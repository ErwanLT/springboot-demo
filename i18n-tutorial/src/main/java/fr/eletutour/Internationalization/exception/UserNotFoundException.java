package fr.eletutour.Internationalization.exception;

public class UserNotFoundException extends ResourceNotFoundException{
    public UserNotFoundException(String messageKey, Object... args) {
        super(messageKey, args);
    }
}
