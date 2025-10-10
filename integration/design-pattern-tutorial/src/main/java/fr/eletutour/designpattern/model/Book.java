// src/main/java/com/example/library/model/Book.java
package fr.eletutour.designpattern.model;

public class Book {
    private final long id;
    private final String title;
    private final String author;
    private final String type;
    private final String cote;

    private Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.type = builder.type;
        this.cote = builder.cote;
    }

    public static class Builder {
        private long id;
        private String title;
        private String author;
        private String type;
        private String cote;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder cote(String cote) {
            this.cote = cote;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    // Getters classiques
    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getType() { return type; }
    public String getCote() { return cote; }
}
