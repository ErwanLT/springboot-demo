package fr.eletutour.receiver.repository;

import fr.eletutour.receiver.entity.ProcessedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, Integer> {
}
