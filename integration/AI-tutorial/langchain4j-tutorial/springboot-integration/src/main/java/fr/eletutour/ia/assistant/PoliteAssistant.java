package fr.eletutour.ia.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface PoliteAssistant {

    @SystemMessage("Tu es un assistant poli et serviable, tu termine toutes tes phrase par monseigneur")
    String chat(String userMessage);
}
