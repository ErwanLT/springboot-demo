package fr.eletutour.ia.controller;

import dev.langchain4j.model.chat.ChatModel;
import fr.eletutour.ia.assistant.*;
import fr.eletutour.ia.service.model.Sentiment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IAController {

    private final ChatModel chatModel;
    private final PoliteAssistant politeAssistant;
    private final AngryAssistant angryAssistant;
    private final PirateAssistant pirateAssistant;
    private final CookingAssistant cookingAssistant;
    private final SentimentAnalyzer sentimentAnalyzer;

    public IAController(ChatModel chatModel, PoliteAssistant politeAssistant, AngryAssistant angryAssistant, PirateAssistant pirateAssistant, CookingAssistant cookingAssistant, SentimentAnalyzer sentimentAnalyzer) {
        this.chatModel = chatModel;
        this.politeAssistant = politeAssistant;
        this.angryAssistant = angryAssistant;
        this.pirateAssistant = pirateAssistant;
        this.cookingAssistant = cookingAssistant;
        this.sentimentAnalyzer = sentimentAnalyzer;
    }

    @GetMapping("/chat")
    public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
        return chatModel.chat(message);
    }

    @GetMapping("/polite")
    public String politeAssistant() {
        return politeAssistant.chat("Hello");
    }

    @GetMapping("/angry")
    public String angryAssistant() {
        return angryAssistant.chat("hello");
    }

    @GetMapping("/pirate")
    public String pirateAssistant(){
        return pirateAssistant.chat("Bonjour capitaine");
    }

    @GetMapping("/cook")
    public String chat(@RequestParam(defaultValue = "Donne-moi une recette de crêpes") String message) {
        return cookingAssistant.chat(message);
    }

    @GetMapping("/sentiment")
    public Sentiment analyze(@RequestParam String message) {
        return sentimentAnalyzer.analyzeSentimentOf(message);
    }

    @GetMapping("/sentiment/positive")
    public boolean isPositive(@RequestParam String message) {
        return sentimentAnalyzer.isPositive(message);
    }

}
