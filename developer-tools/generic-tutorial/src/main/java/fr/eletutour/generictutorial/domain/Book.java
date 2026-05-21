package fr.eletutour.generictutorial.domain;

public class Book {

    private Long id;
    private Title title;
    private Author author;

    public Book(Long id, String title, Author author) {
        this.id = id;
        this.title = new Title(title); // la validation est dans Title
        if (author == null) {
            throw new IllegalArgumentException("A book must have an author");
        }
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public void rename(String newTitle) {
        this.title = new Title(newTitle);
    }
}