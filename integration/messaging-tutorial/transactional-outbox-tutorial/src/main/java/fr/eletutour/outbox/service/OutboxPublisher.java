package fr.eletutour.outbox.service;

import fr.eletutour.outbox.entity.OutboxStatus;
import fr.eletutour.outbox.repository.OutboxEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(OutboxPublisher.class);

    private final OutboxEventRepository outboxEventRepository;
    private final OutboxPublisherTx outboxPublisherTx;

    public OutboxPublisher(OutboxEventRepository outboxEventRepository,
                           OutboxPublisherTx outboxPublisherTx) {
        this.outboxEventRepository = outboxEventRepository;
        this.outboxPublisherTx = outboxPublisherTx;
    }

    @Scheduled(fixedDelayString = "${app.outbox.publish-delay-ms:3000}")
    public void publishPending() {
        outboxEventRepository.findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING)
                .forEach(event -> {
                    try {
                        outboxPublisherTx.publishOne(event.getId());
                    } catch (Exception exception) {
                        LOG.warn("Outbox event {} will be retried later", event.getId(), exception);
                    }
                });
    }
}
