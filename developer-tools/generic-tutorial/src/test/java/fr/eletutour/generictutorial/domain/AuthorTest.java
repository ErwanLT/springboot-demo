package fr.eletutour.generictutorial.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void shouldCreateValidAuthor() {
        Author author = new Author(1L, "Antoine de Saint-Exupéry");
        assertEquals("Antoine de Saint-Exupéry", author.getName());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Author(1L, ""));
        assertThrows(IllegalArgumentException.class, () -> new Author(1L, "   "));
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooShort() {
        assertThrows(IllegalArgumentException.class, () -> new Author(1L, "A"));
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooLong() {
        String longName = "A".repeat(101);
        assertThrows(IllegalArgumentException.class, () -> new Author(1L, longName));
    }
}
