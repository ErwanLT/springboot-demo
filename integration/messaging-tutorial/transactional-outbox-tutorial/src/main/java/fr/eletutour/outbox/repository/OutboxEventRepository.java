package fr.eletutour.outbox.repository;

import fr.eletutour.outbox.entity.OutboxEvent;
import fr.eletutour.outbox.entity.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus status);

    Optional<OutboxEvent> findByIdAndStatus(Long id, OutboxStatus status);
}
