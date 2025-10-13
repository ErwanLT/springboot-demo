package fr.eletutour.ia.controller;

import fr.eletutour.ia.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joke")
public class JokeController {

    private final ChatService chatService;

    public JokeController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public String tellJoke(@RequestParam String subject,
                           @RequestParam String adjective) {
        return chatService.chat(subject, adjective);
    }
}
