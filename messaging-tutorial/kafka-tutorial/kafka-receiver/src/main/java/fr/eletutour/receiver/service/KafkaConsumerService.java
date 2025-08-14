package fr.eletutour.receiver.service;

import fr.eletutour.model.Message;
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
    public void listen(Message message, Acknowledgment acknowledgment) { // Changé de String à Message
        LOG.info("Message reçu : {}", message);
        
        // Simuler une erreur pour les messages contenant "erreur"
        if (message.message() != null && message.message().contains("erreur")) { // Accès au contenu de l'objet Message
            LOG.error("Simulating processing error for message: {}", message);
            // L'exception sera interceptée par le DefaultErrorHandler
            throw new RuntimeException("Erreur de traitement simulée pour le message: " + message);
        }

        // Traitement du message (si pas d'erreur)
        LOG.info("Message traité avec succès : {}", message);
        acknowledgment.acknowledge(); // Commit manuel de l'offset
    }

    // Listener pour la Dead Letter Queue (DLQ)
    @KafkaListener(
            topics = "${app.kafka.dlt-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenDlt(Message message, Acknowledgment acknowledgment) { // Changé de String à Message
        LOG.warn("Message reçu de la DLQ : {}", message);
        
        try {
            // Simuler une tentative de re-traitement
            LOG.info("Tentative de re-traitement du message de la DLQ : {}", message);
            
            // Pour la démonstration, si le message contient "re-erreur", il échoue à nouveau
            if (message.message() != null && message.message().contains("re-erreur")) { // Accès au contenu de l'objet Message
                throw new RuntimeException("Échec du re-traitement simulé pour le message: " + message);
            }

            // Si le re-traitement réussit
            LOG.info("Message de la DLQ re-traité avec succès : {}", message);
            acknowledgment.acknowledge(); // Acknowledge le message de la DLQ
        } catch (Exception e) {
            LOG.error("Échec du re-traitement du message de la DLQ : {}", message, e);
            acknowledgment.acknowledge();
        }
    }
}