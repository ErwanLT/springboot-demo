package fr.eletutour.outbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventsConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderEventsConsumer.class);

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String payload) {
        LOG.info("Received event from Kafka: {}", payload);
    }
}
