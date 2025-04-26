package fr.eletutour.gatling.repository;


import fr.eletutour.gatling.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
