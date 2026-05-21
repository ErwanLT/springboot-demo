package fr.eletutour.generictutorial.repository;

import fr.eletutour.generictutorial.repository.dao.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, Long> {
}
