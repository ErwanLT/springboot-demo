// src/main/java/com/example/library/service/BookService.java
package fr.eletutour.designpattern.service;

import fr.eletutour.designpattern.factory.BookFactory;
import fr.eletutour.designpattern.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final BookFactory bookFactory;
    private final List<Book> books = new ArrayList<>(); // Simule une base de données
    private final AtomicLong counter = new AtomicLong();

    public BookService(BookFactory bookFactory) {
        this.bookFactory = bookFactory;
    }

    public Book createBook(String title, String author, String type) {
        long id = counter.incrementAndGet();
        Book book = bookFactory.createBook(id, title, author, type);
        books.add(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(long id) {
        return books.stream()
                    .filter(book -> book.getId() == id)
                    .findFirst();
    }

    public Optional<Book> updateBook(long id, String title, String author, String type) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                Book updatedBook = bookFactory.createBook(id, title, author, type);
                books.set(i, updatedBook);
                return Optional.of(updatedBook);
            }
        }
        return Optional.empty();
    }

    public boolean deleteBook(long id) {
        return books.removeIf(book -> book.getId() == id);
    }
}
