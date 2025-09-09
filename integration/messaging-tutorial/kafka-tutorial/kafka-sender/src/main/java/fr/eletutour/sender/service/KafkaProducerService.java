/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of kafka-sender.
 *
 * kafka-sender is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kafka-sender is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with kafka-sender.  If not, see <http://www.gnu.org/licenses/>.
 */
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
