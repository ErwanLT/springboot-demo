package fr.eletutour.outbox.controller;

import fr.eletutour.outbox.entity.OutboxEvent;
import fr.eletutour.outbox.entity.OutboxStatus;
import fr.eletutour.outbox.repository.OutboxEventRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/outbox")
public class OutboxController {

    private final OutboxEventRepository outboxEventRepository;

    public OutboxController(OutboxEventRepository outboxEventRepository) {
        this.outboxEventRepository = outboxEventRepository;
    }

    @GetMapping
    public List<OutboxEvent> list(@RequestParam(defaultValue = "PENDING") OutboxStatus status) {
        return outboxEventRepository.findTop50ByStatusOrderByCreatedAtAsc(status);
    }
}
