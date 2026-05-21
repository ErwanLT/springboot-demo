package fr.eletutour.generictutorial.domain;

public class Book {

    private Long id;
    private String title;

    public Book(Long id, String title) {
        this.title = title;
        validate();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void rename(String newTitle) {
        this.title = newTitle;
        validate();
    }

    private void validate() {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
}