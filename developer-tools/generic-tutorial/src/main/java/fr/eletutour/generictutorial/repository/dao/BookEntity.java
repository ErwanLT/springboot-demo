package fr.eletutour.generictutorial.repository.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String authorName;

    protected BookEntity() {}

    public BookEntity(Long id, String title, Long authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Long getAuthorId() { return authorId; }
    public String getAuthorName() { return authorName; }
}
