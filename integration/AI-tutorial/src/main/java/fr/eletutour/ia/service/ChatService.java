package fr.eletutour.ia.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.model.mistralai.MistralAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.data.message.UserMessage.userMessage;
import static dev.langchain4j.model.mistralai.MistralAiChatModelName.MISTRAL_SMALL_LATEST;
import static dev.langchain4j.model.mistralai.MistralAiEmbeddingModelName.MISTRAL_EMBED;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    private final ChatModel model;
    private final EmbeddingModel embeddingModel;

    public ChatService(@Value("${mistral.api.key}") String KEY){
        model = MistralAiChatModel.builder()
                .apiKey(KEY)
                .modelName(MISTRAL_SMALL_LATEST)
                .logRequests(true)
                .logResponses(true)
                .build();

        embeddingModel = MistralAiEmbeddingModel.builder()
                .apiKey(KEY)
                .modelName(MISTRAL_EMBED)
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    public String chat(String subjet, String adjective) {
        PromptTemplate promptTemplate = PromptTemplate
                .from("Tell me a {{adjective}} joke about {{content}} in french..");
        Map<String, Object> variables = new HashMap<>();
        variables.put("adjective", adjective);
        variables.put("content", subjet);
        Prompt prompt = promptTemplate.apply(variables);

        return model.chat(prompt.text());
    }

    public String memory() {
        var chatMemory = MessageWindowChatMemory.withMaxMessages(5);

        chatMemory.add(userMessage("Hello, my name is Erwan"));
        AiMessage answer = model.chat(chatMemory.messages()).aiMessage();
        System.out.println(answer.text());
        chatMemory.add(answer);

        chatMemory.add(userMessage("What is my name?"));
        AiMessage answerWithName = model.chat(chatMemory.messages()).aiMessage();
        chatMemory.add(answerWithName);
        return answerWithName.text();
    }

    public String ragExample() throws Exception {
        Document document = loadDocument(
                toPath(),
                new TextDocumentParser()
        );

        DocumentSplitter splitter = DocumentSplitters.recursive(200, 0);
        List<TextSegment> segments = splitter.split(document);

        List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.addAll(embeddings, segments);

        String question = "Quel est le nom scientifique des axolotl ?";

        Embedding questionEmbedding = embeddingModel.embed(question).content();

        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(questionEmbedding)
                .maxResults(3)
                .minScore(0.7)
                .build();
        List<EmbeddingMatch<TextSegment>> relevantEmbeddings =
                embeddingStore.search(embeddingSearchRequest).matches();

        String information = relevantEmbeddings.stream()
                .map(match -> match.embedded().text())
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = PromptTemplate.from("""
                Voici des informations de contexte :
                ------------------
                {{information}}
                ------------------

                En te basant uniquement sur ces informations et sans connaissances externes,
                réponds à la question suivante :
                {{question}}

                Réponse :
                """);

        Map<String, Object> promptInputs = new HashMap<>();
        promptInputs.put("question", question);
        promptInputs.put("information", information);

        Prompt prompt = promptTemplate.apply(promptInputs);

        // 🔟 Envoi de la requête au modèle de chat
        AiMessage aiMessage = model.chat(prompt.toUserMessage()).aiMessage();
        return aiMessage.text();
    }

    static Path toPath() {
        try {
            URL fileUrl = ChatService.class.getResource("/" + "example-files/about-axolotl.txt");
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
