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
        try {
            LOG.info("Message reçu : {}", message);
            // Traitement du message
            acknowledgment.acknowledge(); // Commit manuel de l'offset
        } catch (Exception e) {
            LOG.error("Erreur lors du traitement du message : {}", message, e);
        }
    }
}
