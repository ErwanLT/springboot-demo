package fr.eletutour.sender.service;

import fr.eletutour.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerService.class);

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final String topic;

    public KafkaProducerService(KafkaTemplate<String, Message> kafkaTemplate,
                                @Value("${app.kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendMessage(Message message) {
        LOG.info("Envoi du message : {}", message);

        kafkaTemplate.executeInTransaction(kafkaOperations -> {
            kafkaOperations.send(topic, message)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            LOG.error("Erreur lors de l'envoi du message : {}", message, ex);
                        } else {
                            LOG.info("Message envoyé : topic={}, partition={}, offset={}",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        }
                    });
            return true;
        });
    }
}
