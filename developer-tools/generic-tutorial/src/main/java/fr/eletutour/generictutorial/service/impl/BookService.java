package fr.eletutour.generictutorial.service.impl;

import fr.eletutour.generictutorial.controller.dto.BookResponse;
import fr.eletutour.generictutorial.controller.dto.CreateBookRequest;
import fr.eletutour.generictutorial.controller.dto.UpdateBookRequest;
import fr.eletutour.generictutorial.domain.Book;
import fr.eletutour.generictutorial.repository.BookRepository;
import fr.eletutour.generictutorial.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements CrudService<
        BookResponse,
        CreateBookRequest,
        UpdateBookRequest,
        Long> {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public BookResponse getById(Long id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        return toResponse(book);
    }

    @Override
    public List<BookResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public BookResponse create(CreateBookRequest request) {
        Book book = new Book(null, request.title());

        Book saved = repository.save(book);
        return toResponse(saved);
    }

    @Override
    public BookResponse update(Long id, UpdateBookRequest request) {
        Book book = repository.findById(id)
                .orElseThrow();

        book.rename(request.title());

        return toResponse(repository.save(book));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private BookResponse toResponse(Book book) {
        return new BookResponse(book.getId(), book.getTitle());
    }
}