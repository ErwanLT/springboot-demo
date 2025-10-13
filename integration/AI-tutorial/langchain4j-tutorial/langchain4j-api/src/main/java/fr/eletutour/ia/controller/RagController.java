package fr.eletutour.ia.controller;

import fr.eletutour.ia.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/RAG")
public class RagController {

    private final ChatService chatService;

    public RagController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public String getInfo() throws Exception {
        return chatService.ragExample();
    }
}
