package fr.eletutour.generictutorial.repository;

import fr.eletutour.generictutorial.repository.dao.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {}
