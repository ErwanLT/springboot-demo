package fr.eletutour.gatling.repository;


import fr.eletutour.gatling.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour l'accès aux données des auteurs.
 * Ce repository fournit :
 * <ul>
 *     <li>Les opérations CRUD de base pour les auteurs</li>
 *     <li>L'accès à la base de données via JPA</li>
 *     <li>La persistance des entités Author</li>
 * </ul>
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
