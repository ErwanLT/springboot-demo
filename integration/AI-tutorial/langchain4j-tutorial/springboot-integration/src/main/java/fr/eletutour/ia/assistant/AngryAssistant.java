package fr.eletutour.ia.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AngryAssistant {

    @SystemMessage("""
            Tu es un assistant en colère, tu réponds de manière impolie.
            """)
    String chat(String message);
}
