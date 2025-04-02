package fr.eletutour.model;

import fr.eletutour.books.consumer.model.Book;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private Long id;
    private String name;
    private String bio;
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(Long id, String name, String bio, List<Book> books) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
