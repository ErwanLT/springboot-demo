package fr.eletutour.receiver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(
            topics = "${app.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(String message, Acknowledgment acknowledgment) {
        LOG.info("Message reçu : {}", message);

        if (message.contains("erreur")) {
            LOG.error("Simulating processing error for message: {}", message);
            throw new RuntimeException("Erreur de traitement simulée pour le message: " + message);
        }

        // Traitement du message (si pas d'erreur)
        LOG.info("Message traité avec succès : {}", message);
        acknowledgment.acknowledge(); // Commit manuel de l'offset
    }

    @KafkaListener(
            topics = "${app.kafka.dlt-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenDlt(String message, Acknowledgment acknowledgment) {
        LOG.warn("Message reçu de la DLQ : {}", message);
        acknowledgment.acknowledge();
    }
}
