/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jms-tutorial.
 *
 * jms-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jms-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jms-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.jms.service;

import fr.eletutour.jms.exception.MessageProcessingException;
import fr.eletutour.jms.model.ChatMessage;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumerService.class);

    @Value("${app.jms.retry.max-redeliveries}")
    private int maxRedeliveries;

    @JmsListener(destination = "${app.jms.queue-name}", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(ChatMessage chatMessage, Message jmsMessage) throws JMSException {
        int deliveryCount = jmsMessage.getIntProperty("JMSXDeliveryCount");
        // Total attempts = 1 (initial) + maxRedeliveries
        int maxDeliveries = maxRedeliveries + 1;

        log.info("Message reçu (tentative {} sur {}): {}", deliveryCount, maxDeliveries, chatMessage);

        if (chatMessage.getContent().contains("ERROR")) {
            if (deliveryCount == maxDeliveries) {
                log.warn("Dernière tentative de rejeu ({} sur {}). Le message sera envoyé à la DLQ en cas d'échec.", deliveryCount, maxDeliveries);
            }
            log.error("Erreur simulée (tentative {} sur {}).", deliveryCount, maxDeliveries);
            throw new MessageProcessingException("Erreur simulée pour déclencher le rejeu.");
        }

        log.info("Message traité avec succès: {}", chatMessage);
    }
}
