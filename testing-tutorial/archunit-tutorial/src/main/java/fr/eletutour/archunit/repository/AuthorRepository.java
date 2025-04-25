package fr.eletutour.archunit.repository;

import fr.eletutour.archunit.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
