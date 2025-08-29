package fr.eletutour.repository;

import fr.eletutour.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long>, RevisionRepository<Stock, Long, Integer> {
}
