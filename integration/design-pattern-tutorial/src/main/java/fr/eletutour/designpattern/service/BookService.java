// src/main/java/com/example/library/service/BookService.java
package fr.eletutour.designpattern.service;

import fr.eletutour.designpattern.factory.BookFactory;
import fr.eletutour.designpattern.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookFactory bookFactory;
    private final List<Book> books = new ArrayList<>(); // Simule une base de données

    public BookService(BookFactory bookFactory) {
        this.bookFactory = bookFactory;
    }

    public Book createBook(String title, String author, String type) {
        Book book = bookFactory.createBook(title, author, type);
        books.add(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return books;
    }
}
