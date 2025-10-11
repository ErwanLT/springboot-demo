package fr.eletutour.ia.controller;

import fr.eletutour.ia.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/streaming")
public class StreamingController {

    private final ChatService chatService;

    public StreamingController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public String getPoeme() {
        return chatService.streaming();
    }
}
