package fr.eletutour.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    private MyExceptionHandler() {
        LOGGER.info("MyExceptionHandler actif");
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ProblemDetail handleAuthorNotFound(AuthorNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail
                .forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Author Not Found");
        problemDetail.setInstance(URI.create("/authors/" + exception.getAuthorId()));
        problemDetail.setType(URI.create("http://localhost:8089/docs/errors/author-not-found.html"));
        problemDetail.setDetail(exception.getMessage());

        return problemDetail;
    }

}