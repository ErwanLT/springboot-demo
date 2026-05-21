package fr.eletutour.generictutorial.repository.impl;

import fr.eletutour.generictutorial.domain.Book;
import fr.eletutour.generictutorial.repository.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final Map<Long, Book> db = new HashMap<>();
    private long seq = 1;

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book = new Book(seq++, book.getTitle(), book.getAuthor());
        }
        db.put(book.getId(), book);
        return book;
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }
}