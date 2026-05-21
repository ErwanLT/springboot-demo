package fr.eletutour.generictutorial.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void shouldCreateValidBook() {
        Author author = new Author(1L, "Author Name");
        Book book = new Book(1L, "Valid Title", author);
        assertEquals("Valid Title", book.getTitle().value());
        assertEquals(author, book.getAuthor());
    }

    @Test
    void shouldThrowExceptionWhenTitleIsEmpty() {
        Author author = new Author(1L, "Author Name");
        assertThrows(IllegalArgumentException.class, () -> new Book(1L, "", author));
    }

    @Test
    void shouldThrowExceptionWhenTitleIsTooShort() {
        Author author = new Author(1L, "Author Name");
        assertThrows(IllegalArgumentException.class, () -> new Book(1L, "Bo", author));
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Book(1L, "Valid Title", null));
    }

    @Test
    void shouldRenameValidly() {
        Author author = new Author(1L, "Author Name");
        Book book = new Book(1L, "Original Title", author);
        book.rename("New Title");
        assertEquals("New Title", book.getTitle().value());
    }

    @Test
    void shouldThrowExceptionWhenRenamingToInvalidTitle() {
        Author author = new Author(1L, "Author Name");
        Book book = new Book(1L, "Original Title", author);
        assertThrows(IllegalArgumentException.class, () -> book.rename(""));
    }
}
