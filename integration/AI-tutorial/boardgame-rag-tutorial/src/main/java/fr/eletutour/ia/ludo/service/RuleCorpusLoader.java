package fr.eletutour.ia.ludo.service;

import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class RuleCorpusLoader {

    public List<Document> loadAllRules() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath:/rules/*.md");
            List<Document> documents = new ArrayList<>();
            for (Resource resource : resources) {
                String content = resource.getContentAsString(StandardCharsets.UTF_8);
                String filename = resource.getFilename() != null ? resource.getFilename() : "unknown";
                String game = gameNameFromFilename(filename);
                documents.addAll(toSectionDocuments(content, game, filename));
            }
            return documents;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load boardgame rules corpus", e);
        }
    }

    private List<Document> toSectionDocuments(String markdown, String game, String source) {
        List<Document> documents = new ArrayList<>();
        Map<String, StringBuilder> sections = splitMarkdownBySection(markdown);

        for (Map.Entry<String, StringBuilder> entry : sections.entrySet()) {
            String section = entry.getKey();
            String text = entry.getValue().toString().trim();
            if (text.isBlank()) {
                continue;
            }

            Document doc = new Document(text).mutate()
                    .metadata("game", game)
                    .metadata("source", source)
                    .metadata("section", section)
                    .build();
            documents.add(doc);
        }
        return documents;
    }

    private Map<String, StringBuilder> splitMarkdownBySection(String markdown) {
        Map<String, StringBuilder> sections = new LinkedHashMap<>();
        String currentSection = "general";
        sections.put(currentSection, new StringBuilder());

        for (String line : markdown.split("\\R")) {
            if (line.startsWith("## ")) {
                currentSection = line.substring(3).trim();
                sections.putIfAbsent(currentSection, new StringBuilder());
                continue;
            }
            if (line.startsWith("# ")) {
                continue;
            }
            sections.get(currentSection).append(line).append('\n');
        }
        return sections;
    }

    private String gameNameFromFilename(String filename) {
        String base = filename.replace(".md", "");
        return base.replace('-', ' ').trim();
    }
}
