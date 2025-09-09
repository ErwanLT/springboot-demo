/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of kafka-receiver.
 *
 * kafka-receiver is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * kafka-receiver is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with kafka-receiver.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.receiver.service;

import fr.eletutour.model.Message;
import fr.eletutour.receiver.entity.ProcessedMessage;
import fr.eletutour.receiver.repository.ProcessedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KafkaConsumerService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerService.class);
    private final ProcessedMessageRepository processedMessageRepository;

    public KafkaConsumerService(ProcessedMessageRepository processedMessageRepository) {
        this.processedMessageRepository = processedMessageRepository;
    }

    @Transactional
    @KafkaListener(
            topics = "${app.kafka.topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(Message message, Acknowledgment acknowledgment) {
        if (message == null || message.id() == null) {
            LOG.warn("Received message or message ID is null, cannot process for idempotence.");
            acknowledgment.acknowledge();
            return;
        }

        if (processedMessageRepository.existsById(message.id())) {
            LOG.info("Message with ID {} has already been processed, skipping.", message.id());
            acknowledgment.acknowledge();
            return;
        }

        LOG.info("Message reçu : {}", message);

        // Simuler une erreur pour les messages contenant "erreur"
        if (message.message() != null && message.message().contains("erreur")) {
            LOG.error("Simulating processing error for message: {}", message);
            throw new RuntimeException("Erreur de traitement simulée pour le message: " + message);
        }

        // Traitement du message (si pas d'erreur)
        LOG.info("Message traité avec succès : {}", message);
        processedMessageRepository.save(new ProcessedMessage(message.id()));
        acknowledgment.acknowledge();
    }

    @Transactional
    @KafkaListener(
            topics = "${app.kafka.dlt-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listenDlt(Message message, Acknowledgment acknowledgment) {
        if (message == null || message.id() == null) {
            LOG.warn("Received DLT message or message ID is null, cannot process for idempotence.");
            acknowledgment.acknowledge();
            return;
        }

        if (processedMessageRepository.existsById(message.id())) {
            LOG.warn("DLT Message with ID {} has already been processed, skipping.", message.id());
            acknowledgment.acknowledge();
            return;
        }

        LOG.warn("Message reçu de la DLQ : {}", message);

        try {
            LOG.info("Tentative de re-traitement du message de la DLQ : {}", message);

            if (message.message() != null && message.message().contains("re-erreur")) {
                throw new RuntimeException("Échec du re-traitement simulé pour le message: " + message);
            }

            LOG.info("Message de la DLQ re-traité avec succès : {}", message);
            processedMessageRepository.save(new ProcessedMessage(message.id()));
            acknowledgment.acknowledge();
        } catch (Exception e) {
            LOG.error("Échec du re-traitement du message de la DLQ : {}", message, e);
            acknowledgment.acknowledge();
        }
    }
}
