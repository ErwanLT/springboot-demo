package fr.eletutour.ia.mcp.server.configuration;

import fr.eletutour.ia.mcp.server.tools.GhostTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Bean
    public ToolCallbackProvider ghostToolsProvider(GhostTools ghostTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(ghostTools)
                .build();
    }
}