package fr.eletutour.generictutorial.repository.impl;

import fr.eletutour.generictutorial.domain.Author;
import fr.eletutour.generictutorial.domain.Book;
import fr.eletutour.generictutorial.repository.BookJpaRepository;
import fr.eletutour.generictutorial.repository.BookRepository;
import fr.eletutour.generictutorial.repository.dao.BookEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryAdapter implements BookRepository {

    private final BookJpaRepository jpa;

    public BookRepositoryAdapter(BookJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = toEntity(book);
        return toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Book> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Book> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    private BookEntity toEntity(Book book) {
        return new BookEntity(book.getId(), book.getTitle().value(), book.getAuthor().getId(), book.getAuthor().getName());
    }

    private Book toDomain(BookEntity entity) {
        return new Book(entity.getId(), entity.getTitle(), new Author(entity.getAuthorId(), entity.getAuthorName()));
    }
}