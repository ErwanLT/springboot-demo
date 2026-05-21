package fr.eletutour.generictutorial.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleEntityNotFoundException_ShouldReturn404() {
        EntityNotFoundException ex = new EntityNotFoundException("Not found");
        ProblemDetail detail = handler.handleEntityNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND.value(), detail.getStatus());
        assertEquals("Entity Not Found", detail.getTitle());
        assertEquals("Not found", detail.getDetail());
        assertEquals(URI.create("https://api.generictutorial.com/errors/not-found"), detail.getType());
        assertNotNull(detail.getProperties().get("timestamp"));
    }

    @Test
    void handleIllegalArgumentException_ShouldReturn400() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid input");
        ProblemDetail detail = handler.handleIllegalArgumentException(ex);

        assertEquals(HttpStatus.BAD_REQUEST.value(), detail.getStatus());
        assertEquals("Invalid Request", detail.getTitle());
        assertEquals("Invalid input", detail.getDetail());
        assertEquals(URI.create("https://api.generictutorial.com/errors/bad-request"), detail.getType());
    }
}
