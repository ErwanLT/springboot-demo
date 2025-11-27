package fr.eletutour.dynamic.repository;


import fr.eletutour.dynamic.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
