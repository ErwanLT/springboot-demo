package fr.eletutour.repository;

import fr.eletutour.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> findAll();
    long count();
    long countByBorrowed(boolean borrowed);
}
