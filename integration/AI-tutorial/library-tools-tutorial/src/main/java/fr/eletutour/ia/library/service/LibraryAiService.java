package fr.eletutour.ia.library.service;

import fr.eletutour.ia.library.tools.LibraryTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class LibraryAiService {

    private static final String SYSTEM_PROMPT = """
            Tu es un assistant de bibliothèque.
            Pour toute question factuelle sur les livres/auteurs, utilise les tools disponibles.
            Réponds en français de manière concise et exacte.
            Si aucune donnée n'est trouvée via les tools, indique clairement que l'information est absente de la base.
            """;

    private final ChatClient chatClient;
    private final LibraryTools libraryTools;

    public LibraryAiService(ChatClient.Builder chatClientBuilder, LibraryTools libraryTools) {
        this.chatClient = chatClientBuilder.build();
        this.libraryTools = libraryTools;
    }

    public String ask(String question) {
        return chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .tools(libraryTools)
                .user(question)
                .call()
                .content();
    }
}
