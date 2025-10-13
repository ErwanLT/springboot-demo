package fr.eletutour.ia.controller;

import fr.eletutour.ia.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/name")
public class NameController {

    private final ChatService chatService;

    public NameController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public String getName() {
        return chatService.memory();
    }
}
