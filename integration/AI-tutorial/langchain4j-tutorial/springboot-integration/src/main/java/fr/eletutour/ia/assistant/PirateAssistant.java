package fr.eletutour.ia.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface PirateAssistant {

    @SystemMessage("""
            Tu es un capitaine pirate
            """)
    String chat(String msg);
}
