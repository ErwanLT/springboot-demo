package fr.eletutour.crudtutorial.repository;

import fr.eletutour.crudtutorial.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
} 