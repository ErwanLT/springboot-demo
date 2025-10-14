package fr.eletutour.ia.controller;

import dev.langchain4j.model.chat.ChatModel;
import fr.eletutour.ia.assistant.CookingAssistant;
import fr.eletutour.ia.assistant.PoliteAssistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IAController {

    private final ChatModel chatModel;
    private final PoliteAssistant politeAssistant;
    private final CookingAssistant cookingAssistant;

    public IAController(ChatModel chatModel, PoliteAssistant politeAssistant, CookingAssistant cookingAssistant) {
        this.chatModel = chatModel;
        this.politeAssistant = politeAssistant;
        this.cookingAssistant = cookingAssistant;
    }

    @GetMapping("/chat")
    public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
        return chatModel.chat(message);
    }

    @GetMapping("/polite")
    public String politeAssistant() {
        return politeAssistant.chat("Hello");
    }

    @GetMapping("/cook")
    public String chat(@RequestParam(defaultValue = "Donne-moi une recette de crêpes") String message) {
        return cookingAssistant.chat(message);
    }

}
