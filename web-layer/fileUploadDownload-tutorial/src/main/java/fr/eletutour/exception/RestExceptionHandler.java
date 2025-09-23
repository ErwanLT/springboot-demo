package fr.eletutour.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ProblemDetail body = createProblemDetail(ex, status, "Malformed JSON request", null, null, request);
        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
        final ProblemDetail problemDetail;
        if (ex instanceof FileStorageException) {
            problemDetail = createProblemDetail(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), null, null, request);
        } else {
            problemDetail = createProblemDetail(ex, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", null, null, request);
        }
        return handleExceptionInternal(ex, problemDetail, new HttpHeaders(), HttpStatus.valueOf(problemDetail.getStatus()), request);
    }
}
