package fr.eletutour.ia.mcp.server.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ghost")
public record GhostProperties(
        String url,
        String adminApiKey) {
}