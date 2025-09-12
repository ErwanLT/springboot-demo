package fr.eletutour.repository;

import fr.eletutour.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    public InMemoryBookRepository() {
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", new Date()));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", new Date()));
        books.add(new Book("The Silmarillion", "J.R.R. Tolkien", new Date()));
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public long count() {
        return books.size();
    }

    @Override
    public long countByBorrowed(boolean borrowed) {
        return books.stream().filter(b -> b.isBorrowed() == borrowed).count();
    }
}
