package fr.eletutour.sender.controller;

import fr.eletutour.model.Message;
import fr.eletutour.sender.service.KafkaProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final KafkaProducerService producerService;

    public MessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        producerService.sendMessage(message);
    }
}
