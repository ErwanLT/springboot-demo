package fr.eletutour.ia.mcp.server.models;

import java.time.LocalDateTime;
import java.util.List;

public record BlogPost(
        String id,
        String title,
        String slug,
        String excerpt,
        String content,
        String author,
        List<String> tags,
        LocalDateTime publishedAt,
        boolean draft) {
}