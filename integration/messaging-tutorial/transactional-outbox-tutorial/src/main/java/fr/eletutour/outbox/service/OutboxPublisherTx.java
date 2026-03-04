package fr.eletutour.outbox.service;

import fr.eletutour.outbox.entity.OutboxEvent;
import fr.eletutour.outbox.entity.OutboxStatus;
import fr.eletutour.outbox.repository.OutboxEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class OutboxPublisherTx {

    private static final Logger LOG = LoggerFactory.getLogger(OutboxPublisherTx.class);

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public OutboxPublisherTx(OutboxEventRepository outboxEventRepository,
                             KafkaTemplate<String, String> kafkaTemplate,
                             @Value("${app.kafka.topic}") String topic) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publishOne(Long outboxEventId) throws Exception {
        OutboxEvent event = outboxEventRepository.findByIdAndStatus(outboxEventId, OutboxStatus.PENDING)
                .orElse(null);

        if (event == null) {
            return;
        }

        kafkaTemplate.send(topic, event.getAggregateId(), event.getPayload())
                .get(10, TimeUnit.SECONDS);

        event.setStatus(OutboxStatus.SENT);
        event.setSentAt(Instant.now());

        LOG.info("Published outbox event id={} aggregateId={} topic={} ",
                event.getId(), event.getAggregateId(), topic);
    }
}
