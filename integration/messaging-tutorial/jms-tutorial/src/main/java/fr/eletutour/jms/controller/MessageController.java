package fr.eletutour.jms.controller;

import fr.eletutour.jms.model.ChatMessage;
import fr.eletutour.jms.service.MessageProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducerService producerService;

    public MessageController(MessageProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public String sendMessage(@RequestBody ChatMessage message) {
        producerService.sendMessage(message);
        return "Message envoyé : " + message;
    }
}
