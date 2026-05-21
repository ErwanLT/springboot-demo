package fr.eletutour.generictutorial.service.impl;

import fr.eletutour.generictutorial.controller.dto.AuthorResponse;
import fr.eletutour.generictutorial.controller.dto.CreateAuthorRequest;
import fr.eletutour.generictutorial.domain.Author;
import fr.eletutour.generictutorial.exception.EntityNotFoundException;
import fr.eletutour.generictutorial.repository.AuthorRepository;
import fr.eletutour.generictutorial.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService implements CrudService<AuthorResponse, CreateAuthorRequest, CreateAuthorRequest, Long> {

    private final AuthorRepository repository;

    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public AuthorResponse getById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        return toResponse(author);
    }

    @Override
    public List<AuthorResponse> getAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public AuthorResponse create(CreateAuthorRequest request) {
        Author author = new Author(null, request.name());
        return toResponse(repository.save(author));
    }

    @Override
    public AuthorResponse update(Long id, CreateAuthorRequest request) {
        Author author = repository.findById(id).orElseThrow();
        // Update logic here
        return toResponse(repository.save(author));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public AuthorResponse toResponse(Author author) {
        return new AuthorResponse(author.getId(), author.getName());
    }
}
