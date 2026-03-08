package fr.eletutour.ia.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String isbn;

    @Enumerated(EnumType.STRING)
    private LiteraryGenre genre;

    @Enumerated(EnumType.STRING)
    private BookType type;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    protected Book() {
    }

    public Book(String title, String isbn, LiteraryGenre genre, BookType type) {
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public LiteraryGenre getGenre() {
        return genre;
    }

    public BookType getType() {
        return type;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
