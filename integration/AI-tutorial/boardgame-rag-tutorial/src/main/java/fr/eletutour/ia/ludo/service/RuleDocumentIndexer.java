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

    public RuleDocumentIndexer(RuleCorpusLoader ruleCorpusLoader,
                               VectorStore vectorStore,
                               @Value("${app.rag.reindex-on-startup:true}") boolean reindexOnStartup) {
        this.ruleCorpusLoader = ruleCorpusLoader;
        this.vectorStore = vectorStore;
        this.reindexOnStartup = reindexOnStartup;
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
                .withChunkSize(500)
                .withMinChunkSizeChars(150)
                .withMinChunkLengthToEmbed(80)
                .withMaxNumChunks(10_000)
                .withKeepSeparator(true)
                .build();

        List<Document> chunks = new ArrayList<>();
        for (Document sectionDoc : loadedRules) {
            List<Document> split = splitter.split(sectionDoc);
            for (int i = 0; i < split.size(); i++) {
                Document chunk = split.get(i).mutate()
                        .id(sectionDoc.getMetadata().get("source") + "#"
                                + sectionDoc.getMetadata().get("section") + "#" + i)
                        .metadata("chunk", i)
                        .build();
                chunks.add(chunk);
            }
        }

        vectorStore.add(chunks);
        LOGGER.info("Indexed {} chunks from {} rule sections", chunks.size(), loadedRules.size());
    }
}
