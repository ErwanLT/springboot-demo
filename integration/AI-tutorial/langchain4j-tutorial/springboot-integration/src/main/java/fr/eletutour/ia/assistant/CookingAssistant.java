package fr.eletutour.ia.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface CookingAssistant {

    @SystemMessage("""
        Tu es un chef cuisinier français. 
        Si l'utilisateur demande une recette, tu dois utiliser les outils disponibles
        pour trouver ou proposer une recette adaptée.
        """)
    String chat(String userMessage);
}
