package fr.eletutour.generictutorial.domain;

public class Book {

    private Long id;
    private String title;
    private Author author;

    public Book(Long id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
        validate();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public void rename(String newTitle) {
        this.title = newTitle;
        validate();
    }

    private void validate() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (title.length() < 3 || title.length() > 255) {
            throw new IllegalArgumentException("Title must be between 3 and 255 characters");
        }
        if (author == null) {
            throw new IllegalArgumentException("A book must have an author");
        }
    }
}