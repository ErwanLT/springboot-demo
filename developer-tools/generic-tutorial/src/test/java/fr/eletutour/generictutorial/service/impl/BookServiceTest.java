package fr.eletutour.generictutorial.service.impl;

import fr.eletutour.generictutorial.controller.dto.AuthorResponse;
import fr.eletutour.generictutorial.controller.dto.BookResponse;
import fr.eletutour.generictutorial.controller.dto.CreateBookRequest;
import fr.eletutour.generictutorial.domain.Author;
import fr.eletutour.generictutorial.domain.Book;
import fr.eletutour.generictutorial.exception.EntityNotFoundException;
import fr.eletutour.generictutorial.repository.AuthorRepository;
import fr.eletutour.generictutorial.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldCreateBook_WhenAuthorExists() {
        Author author = new Author(1L, "Test Author");
        CreateBookRequest request = new CreateBookRequest("Test Book", 1L);
        Book savedBook = new Book(1L, "Test Book", author);
        AuthorResponse authorResponse = new AuthorResponse(1L, "Test Author");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);
        when(authorService.toResponse(author)).thenReturn(authorResponse);

        BookResponse response = bookService.create(request);

        assertNotNull(response);
        assertEquals("Test Book", response.title());
        assertEquals("Test Author", response.author().name());
        verify(authorRepository).findById(1L);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void create_ShouldThrowException_WhenAuthorNotFound() {
        CreateBookRequest request = new CreateBookRequest("Test Book", 99L);
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.create(request));
    }
}
