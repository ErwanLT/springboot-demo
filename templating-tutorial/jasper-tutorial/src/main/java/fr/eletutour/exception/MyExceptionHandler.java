package fr.eletutour.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class MyExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(MyExceptionHandler.class);

    private MyExceptionHandler() {
        LOGGER.info("MyExceptionHandler actif");
    }

    @ExceptionHandler(JasperException.class)
    public ResponseEntity<Object> handleAuthorException(JasperException je) {
        return ResponseEntity.status(je.getError().getCode())
                .body(new ErrorResponse(HttpStatus.resolve(je.getError().getCode()), je.getError().getMessage(), List.of(je.getMessage())));
    }

}