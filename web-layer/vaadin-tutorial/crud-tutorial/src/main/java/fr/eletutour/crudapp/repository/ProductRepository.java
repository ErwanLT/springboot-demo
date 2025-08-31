package fr.eletutour.crudapp.repository;

import fr.eletutour.crudapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
} 