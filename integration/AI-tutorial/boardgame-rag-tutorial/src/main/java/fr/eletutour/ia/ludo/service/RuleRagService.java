package fr.eletutour.ia.ludo.service;

import fr.eletutour.ia.ludo.dto.RuleAnswerResponse;
import fr.eletutour.ia.ludo.dto.RuleQuestionRequest;
import fr.eletutour.ia.ludo.dto.RuleSourceSnippet;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class RuleRagService {

    private static final String SYSTEM_PROMPT = """
            Nous sommes un arbitre de jeux de société.
            Nous répondons uniquement à partir du contexte fourni.
            Si le contexte ne permet pas de conclure, nous le disons explicitement.
            Nous ne supposons jamais une règle absente.
            """;

    private final VectorStore vectorStore;
    private final ChatClient chatClient;
    private final int retrievalTopK;
    private final int finalContextMaxTokens;

    public RuleRagService(VectorStore vectorStore,
                          ChatClient.Builder chatClientBuilder,
                          @Value("${app.rag.retrieval-top-k:20}") int retrievalTopK,
                          @Value("${app.rag.max-context-tokens:1200}") int finalContextMaxTokens) {
        this.vectorStore = vectorStore;
        this.chatClient = chatClientBuilder.build();
        this.retrievalTopK = retrievalTopK;
        this.finalContextMaxTokens = finalContextMaxTokens;
    }

    public RuleAnswerResponse ask(RuleQuestionRequest request) {
        String effectiveQuery = enrichQuery(request.question(), request.game());
        List<Document> candidates = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(effectiveQuery)
                        .topK(retrievalTopK)
                        .build()
        );

        List<Document> filtered = filterByGame(candidates, request.game());
        List<Document> deduplicated = deduplicate(filtered);
        List<Document> ranked = rankByScore(deduplicated);
        List<Document> selected = selectWithinTokenBudget(ranked, finalContextMaxTokens);

        String context = selected.stream()
                .map(this::formatContextBlock)
                .reduce((left, right) -> left + "\n\n---\n\n" + right)
                .orElse("Aucun extrait de règle trouvé.");

        String userPrompt = """
                Question utilisateur: %s
                Jeu ciblé: %s

                Contexte de règles:
                %s

                Consigne:
                - Répondre de manière opérationnelle.
                - Citer explicitement si l'action est autorisée, interdite ou ambiguë.
                - Si ambigu, proposer la vérification manuelle de la règle officielle.
                """.formatted(
                request.question(),
                request.game() == null || request.game().isBlank() ? "non précisé" : request.game(),
                context
        );

        String answer = chatClient.prompt()
                .system(SYSTEM_PROMPT)
                .user(userPrompt)
                .call()
                .content();

        List<RuleSourceSnippet> sources = selected.stream()
                .map(doc -> new RuleSourceSnippet(
                        String.valueOf(doc.getMetadata().getOrDefault("game", "inconnu")),
                        String.valueOf(doc.getMetadata().getOrDefault("source", "source inconnue")),
                        String.valueOf(doc.getMetadata().getOrDefault("section", "general")),
                        excerpt(doc.getText()),
                        doc.getScore()
                ))
                .toList();

        return new RuleAnswerResponse(
                request.question(),
                request.game(),
                answer,
                selected.size(),
                sources
        );
    }

    private List<Document> filterByGame(List<Document> candidates, String game) {
        if (game == null || game.isBlank()) {
            return candidates;
        }
        String expected = normalize(game);
        List<Document> filtered = candidates.stream()
                .filter(doc -> normalize(String.valueOf(doc.getMetadata().getOrDefault("game", ""))).contains(expected)
                        || normalize(String.valueOf(doc.getMetadata().getOrDefault("source", ""))).contains(expected))
                .toList();

        return filtered.isEmpty() ? candidates : filtered;
    }

    private List<Document> deduplicate(List<Document> documents) {
        Map<String, Document> unique = new LinkedHashMap<>();
        for (Document doc : documents) {
            String key = normalize(doc.getText());
            if (key.length() > 180) {
                key = key.substring(0, 180);
            }
            unique.putIfAbsent(key, doc);
        }
        return new ArrayList<>(unique.values());
    }

    private List<Document> rankByScore(List<Document> documents) {
        return documents.stream()
                .sorted(Comparator.comparing(
                                (Document d) -> d.getScore() == null ? Double.NEGATIVE_INFINITY : d.getScore())
                        .reversed())
                .toList();
    }

    private List<Document> selectWithinTokenBudget(List<Document> rankedDocuments, int maxTokens) {
        List<Document> selected = new ArrayList<>();
        int used = 0;
        for (Document doc : rankedDocuments) {
            int estimate = estimateTokens(formatContextBlock(doc));
            if (!selected.isEmpty() && used + estimate > maxTokens) {
                break;
            }
            selected.add(doc);
            used += estimate;
        }
        return selected;
    }

    private String formatContextBlock(Document doc) {
        String game = String.valueOf(doc.getMetadata().getOrDefault("game", "inconnu"));
        String source = String.valueOf(doc.getMetadata().getOrDefault("source", "source inconnue"));
        String section = String.valueOf(doc.getMetadata().getOrDefault("section", "general"));
        return "[game=" + game + ", source=" + source + ", section=" + section + "]\n" + doc.getText();
    }

    private int estimateTokens(String text) {
        return Math.max(1, text.length() / 4);
    }

    private String enrichQuery(String question, String game) {
        if (game == null || game.isBlank()) {
            return question;
        }
        return "Jeu: " + game + " | Question: " + question;
    }

    private String excerpt(String text) {
        if (text == null) {
            return "";
        }
        return text.length() > 220 ? text.substring(0, 220) + "..." : text;
    }

    private String normalize(String value) {
        if (value == null) {
            return "";
        }
        String ascii = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return ascii.toLowerCase(Locale.ROOT).trim().replace('-', ' ');
    }
}
