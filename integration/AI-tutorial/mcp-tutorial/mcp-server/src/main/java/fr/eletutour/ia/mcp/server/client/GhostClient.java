package fr.eletutour.ia.mcp.server.client;

import fr.eletutour.ia.mcp.server.configuration.GhostProperties;
import fr.eletutour.ia.mcp.server.service.GhostJwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GhostClient {

    private static final Logger log = LoggerFactory.getLogger(GhostClient.class);

    private final WebClient webClient;
    private final GhostJwtService jwtService;

    public GhostClient(
            GhostProperties ghostProperties,
            GhostJwtService jwtService
    ) {
        this.jwtService = jwtService;

        // Augmentation de la taille du buffer pour éviter la DataBufferLimitException (256Ko -> 2Mo)
        this.webClient = WebClient.builder()
                .baseUrl(ghostProperties.url() + "/ghost/api/admin")
                .defaultHeader("Accept-Version", "v5.0")
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(2 * 1024 * 1024))
                .build();
    }

    public String getPosts() {
        log.info("Fetching all recent posts from Ghost API");
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        // Le filtre "authors:erwan" a été supprimé !
                        .queryParam("limit", "15")
                        .queryParam("include", "tags,primary_tag")
                        .queryParam("fields", "id,title,status,slug")
                        .build())
                .header("Authorization", "Ghost " + jwtService.generateToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getPostsByAuthor(String author) {
        log.info("Fetching posts for author '{}' from Ghost API", author);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        .queryParam("filter", "authors:" + author) // La variable est injectée ici !
                        .queryParam("limit", "15")
                        .queryParam("include", "tags,primary_tag")
                        .queryParam("fields", "id,title,status,slug")
                        .build())
                .header("Authorization", "Ghost " + jwtService.generateToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getPostsByTag(String tag) {
        log.info("Fetching posts for tag '{}' from Ghost API", tag);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/")
                        .queryParam("filter", "tag:" + tag)
                        .queryParam("limit", "15")
                        .queryParam("fields", "id,title,status,slug") // Limite aussi le payload ici
                        .build())
                .header("Authorization", "Ghost " + jwtService.generateToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getPostBySlug(String slug) {
        log.info("Fetching post for slug '{}' from Ghost API", slug);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/slug/" + slug + "/")
                        .queryParam("formats", "html,plaintext") // Demande du format propre plutôt que du mobiledoc brut
                        .build())
                .header("Authorization", "Ghost " + jwtService.generateToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}