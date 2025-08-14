package fr.eletutour.receiver.configuration;

import fr.eletutour.model.Message;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;

@Configuration
public class KafkaConsumerConfig {

    @Value("${app.kafka.dlt-topic}")
    private String dltTopic;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory( // Changé à Message
                                                                                                   ConsumerFactory<String, Message> consumerFactory,
                                                                                                   KafkaTemplate<String, Message> kafkaTemplate) { // Changé à Message

        ConcurrentKafkaListenerContainerFactory<String, Message> factory = // Changé à Message
                new ConcurrentKafkaListenerContainerFactory<>();
        
        factory.setConsumerFactory(consumerFactory);
        
        // Active le mode Ack manuel
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        // Configure le DefaultErrorHandler avec DeadLetterPublishingRecoverer
        // FixedBackOff(0L, 0) signifie pas de re-tentative avant d'envoyer au DLT
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
            new DeadLetterPublishingRecoverer(kafkaTemplate, (consumerRecord, e) -> {
                // Définir le topic DLQ et la partition (null pour laisser Kafka choisir)
                return new TopicPartition(dltTopic, -1);
            }),
            new FixedBackOff(0L, 0)
        );
        factory.setCommonErrorHandler(errorHandler);
        
        return factory;
    }
}
