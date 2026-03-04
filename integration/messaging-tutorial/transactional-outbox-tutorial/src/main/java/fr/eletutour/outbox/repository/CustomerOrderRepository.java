package fr.eletutour.outbox.repository;

import fr.eletutour.outbox.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {
}
