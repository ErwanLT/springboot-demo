package fr.eletutour.generictutorial.domain;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private Long id;
    private String name;
    private List<Book> books = new ArrayList<>();

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
        validate();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException("Author name must be between 2 and 100 characters");
        }
    }
}
