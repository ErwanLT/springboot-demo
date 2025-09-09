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

import fr.eletutour.jms.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducerService {

    private static final Logger log = LoggerFactory.getLogger(MessageProducerService.class);

    private final JmsTemplate jmsTemplate;

    @Value("${app.jms.queue-name}")
    private String queueName;

    public MessageProducerService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(ChatMessage chatMessage) {
        jmsTemplate.convertAndSend(queueName, chatMessage);
        log.info("Message envoyé dans la file : {}", chatMessage);
    }
}
