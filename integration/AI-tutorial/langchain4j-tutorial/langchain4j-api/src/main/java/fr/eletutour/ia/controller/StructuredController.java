package fr.eletutour.ia.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.eletutour.ia.model.Book;
import fr.eletutour.ia.service.ChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/structured")
public class StructuredController {

    private final ChatService chatService;


    public StructuredController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping
    public Book getBookInformation() throws JsonProcessingException {
        return chatService.structuredOutput();
    }
}
