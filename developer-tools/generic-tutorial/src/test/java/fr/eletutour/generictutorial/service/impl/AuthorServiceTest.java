package fr.eletutour.generictutorial.service.impl;

import fr.eletutour.generictutorial.controller.dto.AuthorResponse;
import fr.eletutour.generictutorial.controller.dto.CreateAuthorRequest;
import fr.eletutour.generictutorial.domain.Author;
import fr.eletutour.generictutorial.exception.EntityNotFoundException;
import fr.eletutour.generictutorial.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository repository;

    @InjectMocks
    private AuthorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_ShouldReturnResponse_WhenAuthorExists() {
        Author author = new Author(1L, "Test Author");
        when(repository.findById(1L)).thenReturn(Optional.of(author));

        AuthorResponse response = service.getById(1L);

        assertNotNull(response);
        assertEquals("Test Author", response.name());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void getById_ShouldThrowException_WhenAuthorDoesNotExist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.getById(1L));
    }

    @Test
    void create_ShouldReturnResponse_WhenValidRequest() {
        CreateAuthorRequest request = new CreateAuthorRequest("New Author");
        Author savedAuthor = new Author(1L, "New Author");
        when(repository.save(any(Author.class))).thenReturn(savedAuthor);

        AuthorResponse response = service.create(request);

        assertNotNull(response);
        assertEquals("New Author", response.name());
        verify(repository, times(1)).save(any(Author.class));
    }
}
