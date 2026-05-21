package fr.eletutour.generictutorial.repository;

import fr.eletutour.generictutorial.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findById(Long id);
    List<Author> findAll();
    Author save(Author author);
    void deleteById(Long id);
}
