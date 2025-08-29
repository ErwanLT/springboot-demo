package fr.eletutour.exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(String msg) {
        super(msg);
    }
}
