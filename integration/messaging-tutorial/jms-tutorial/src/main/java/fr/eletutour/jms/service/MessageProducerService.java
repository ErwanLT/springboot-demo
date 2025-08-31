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
