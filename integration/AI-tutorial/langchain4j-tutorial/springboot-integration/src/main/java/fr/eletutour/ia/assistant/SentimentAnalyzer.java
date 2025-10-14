package fr.eletutour.ia.assistant;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import fr.eletutour.ia.service.model.Sentiment;

@AiService
public interface SentimentAnalyzer {

    @UserMessage("Analyse le sentiment de ce texte et répond uniquement par POSITIVE, NEUTRAL ou NEGATIVE : {{it}}")
    Sentiment analyzeSentimentOf(String text);

    @UserMessage("Ce texte est-il positif ? Réponds uniquement par true ou false : {{it}}")
    boolean isPositive(String text);
}