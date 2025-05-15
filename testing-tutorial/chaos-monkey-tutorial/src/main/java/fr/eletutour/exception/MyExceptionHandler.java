package fr.eletutour.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    private MyExceptionHandler() {
        LOGGER.info("MyExceptionHandler actif");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleExceptions(Exception e) {
        return switch (e) {
            case AuthorNotFoundException  authorNotFoundException -> buildProblemDetail(HttpStatus.NOT_FOUND, "Author Not Found", "/authors/" + authorNotFoundException.getAuthorId(), "http://localhost:8089/docs/errors/author-not-found.html", authorNotFoundException.getMessage(), Map.of());
            case ArticleNotFoundException articleNotFoundException -> buildProblemDetail(HttpStatus.NOT_FOUND, "Article Not Found", "/authors/" + articleNotFoundException.getArticleId(), "http://localhost:8089/docs/errors/article-not-found.html", articleNotFoundException.getMessage(), Map.of());
            case TimeoutException timeoutException -> buildProblemDetail(HttpStatus.REQUEST_TIMEOUT, "Time Out", "", "", timeoutException.getMessage(), Map.of());
            case InterruptedException interruptedException -> {
                Thread.currentThread().interrupt();
                yield buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne", "", "", "Le traitement a été interrompu", Map.of());
            }
            default -> buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur interne", "", "", "Une erreur est survenue", Map.of());
        };
    }

    private ProblemDetail buildProblemDetail(HttpStatus httpStatus, String title, String instance, String type, String detail, Map<String, String> properties) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(title);
        problemDetail.setInstance(URI.create(instance));
        problemDetail.setType(URI.create(type));
        problemDetail.setDetail(detail);
        properties.entrySet().stream()
                .forEach(entry -> problemDetail.setProperty(entry.getKey(), entry.getValue()));
        return problemDetail;
    }

}