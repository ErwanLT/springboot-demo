package fr.eletutour.ia.ludo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RuleDocumentIndexer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleDocumentIndexer.class);

    private final RuleCorpusLoader ruleCorpusLoader;
    private final VectorStore vectorStore;
    private final boolean reindexOnStartup;
    private final int maxChunkChars;

    public RuleDocumentIndexer(RuleCorpusLoader ruleCorpusLoader,
                               VectorStore vectorStore,
                               @Value("${app.rag.reindex-on-startup:true}") boolean reindexOnStartup,
                               @Value("${app.rag.max-embedding-chunk-chars:1200}") int maxChunkChars) {
        this.ruleCorpusLoader = ruleCorpusLoader;
        this.vectorStore = vectorStore;
        this.reindexOnStartup = reindexOnStartup;
        this.maxChunkChars = maxChunkChars;
    }

    @Override
    public void run(String... args) {
        if (!reindexOnStartup) {
            LOGGER.info("RAG indexing skipped (app.rag.reindex-on-startup=false)");
            return;
        }

        List<Document> loadedRules = ruleCorpusLoader.loadAllRules();
        if (loadedRules.isEmpty()) {
            LOGGER.warn("No rule files found, indexing skipped");
            return;
        }

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(220)
                .withMinChunkSizeChars(80)
                .withMinChunkLengthToEmbed(40)
                .withMaxNumChunks(20_000)
                .withKeepSeparator(true)
                .build();

        List<Document> chunks = new ArrayList<>();
        for (Document sectionDoc : loadedRules) {
            List<Document> split = splitter.split(sectionDoc);
            int chunkIndex = 0;
            for (Document splitChunk : split) {
                for (String safeText : splitToMaxChars(splitChunk.getText(), maxChunkChars)) {
                    Document chunk = new Document(safeText).mutate()
                            .id(sectionDoc.getMetadata().get("source") + "#"
                                    + sectionDoc.getMetadata().get("section") + "#" + chunkIndex)
                            .metadata(sectionDoc.getMetadata())
                            .metadata("chunk", chunkIndex)
                            .build();
                    chunks.add(chunk);
                    chunkIndex++;
                }
            }
        }

        vectorStore.add(chunks);
        LOGGER.info("Indexed {} chunks from {} rule sections", chunks.size(), loadedRules.size());
    }

    private List<String> splitToMaxChars(String text, int maxChars) {
        String normalized = text == null ? "" : text.trim();
        if (normalized.isEmpty()) {
            return List.of();
        }
        if (normalized.length() <= maxChars) {
            return List.of(normalized);
        }

        List<String> parts = new ArrayList<>();
        int start = 0;
        int length = normalized.length();

        while (start < length) {
            int end = Math.min(start + maxChars, length);
            if (end < length) {
                int cut = normalized.lastIndexOf('\n', end);
                if (cut <= start) {
                    cut = normalized.lastIndexOf(' ', end);
                }
                if (cut > start) {
                    end = cut;
                }
            }

            String part = normalized.substring(start, end).trim();
            if (!part.isEmpty()) {
                parts.add(part);
            }
            start = end;

            while (start < length && Character.isWhitespace(normalized.charAt(start))) {
                start++;
            }
        }

        return parts;
    }
}
