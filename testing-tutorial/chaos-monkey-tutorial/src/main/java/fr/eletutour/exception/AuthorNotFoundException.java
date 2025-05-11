package fr.eletutour.exception;

public class AuthorNotFoundException extends Exception {

    private Long authorId;

    public AuthorNotFoundException(String msg, Long authorId) {
        super(msg);
        this.authorId = authorId;
    }

    public AuthorNotFoundException(String msg) {
        super(msg);
    }

    public String getAuthorId() {
        return authorId.toString();
    }
}
