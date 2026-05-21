package fr.eletutour.generictutorial.repository;

import fr.eletutour.generictutorial.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(Long id);
}