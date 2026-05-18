package fr.eletutour.ia.mcp.server.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eletutour.ia.mcp.server.client.GhostClient;
import fr.eletutour.ia.mcp.server.models.BlogPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GhostService {

    private static final Logger log = LoggerFactory.getLogger(GhostService.class);

    private final GhostClient ghostClient;

    private final ObjectMapper mapper = new ObjectMapper();

    public GhostService(GhostClient ghostClient) {

        this.ghostClient = ghostClient;
    }

    public List<BlogPost> listRecentPosts() {

        try {
            log.info("Listing recent posts");
            String json = ghostClient.getPosts();

            return parsePosts(json);

        } catch (Exception e) {
            log.error("Error while retrieving Ghost posts", e);
            throw new RuntimeException(
                    "Unable to retrieve Ghost posts",
                    e
            );
        }
    }

    public List<BlogPost> findPostsByTag(String tag) {

        try {
            log.info("Finding posts for tag: {}", tag);
            String json = ghostClient.getPostsByTag(tag);

            return parsePosts(json);

        } catch (Exception e) {
            log.error("Error while retrieving Ghost posts for tag: {}", tag, e);
            throw new RuntimeException(
                    "Unable to retrieve Ghost posts for tag: " + tag,
                    e
            );
        }
    }

    public BlogPost getPostBySlug(String slug) {

        try {
            log.info("Getting post for slug: {}", slug);
            String json = ghostClient.getPostBySlug(slug);

            List<BlogPost> posts = parsePosts(json);

            if (posts.isEmpty()) {
                log.warn("No post found for slug: {}", slug);
                throw new RuntimeException("Post not found for slug: " + slug);
            }

            return posts.get(0);

        } catch (Exception e) {
            log.error("Error while retrieving Ghost post for slug: {}", slug, e);
            throw new RuntimeException(
                    "Unable to retrieve Ghost post for slug: " + slug,
                    e
            );
        }
    }

    private List<BlogPost> parsePosts(String json) throws Exception {

        JsonNode root = mapper.readTree(json);

        JsonNode postsNode = root.get("posts");

        List<BlogPost> posts = new ArrayList<>();

        if (postsNode == null || !postsNode.isArray()) {
            log.debug("No posts found in JSON response");
            return posts;
        }

        log.debug("Found {} posts in JSON response", postsNode.size());
        for (JsonNode node : postsNode) {

            posts.add(mapNodeToPost(node));
        }

        return posts;
    }

    private BlogPost mapNodeToPost(JsonNode node) {

        String publishedAtStr = node.path("published_at").asText(null);
        String title = node.path("title").asText();
        log.trace("Mapping node to BlogPost: {}", title);

        LocalDateTime publishedAt = null;

        if (publishedAtStr != null && !publishedAtStr.isBlank()) {

            try {

                publishedAt = LocalDateTime.parse(
                        publishedAtStr.replace("Z", "")
                );

            } catch (Exception e) {
                // Fallback or ignore
            }
        }

        return new BlogPost(

                node.path("id").asText(),

                node.path("title").asText(),

                node.path("slug").asText(),

                node.path("excerpt").asText(),

                node.path("html").asText(),

                extractPrimaryAuthor(node),

                extractTags(node),

                publishedAt,

                "draft".equalsIgnoreCase(
                        node.path("status").asText()
                )
        );
    }

    private String extractPrimaryAuthor(JsonNode node) {

        JsonNode authors = node.path("authors");

        if (authors.isArray() && !authors.isEmpty()) {

            return authors.get(0).path("name").asText();
        }

        return "Unknown";
    }

    private List<String> extractTags(JsonNode node) {

        List<String> tags = new ArrayList<>();

        JsonNode tagsNode = node.path("tags");

        if (tagsNode.isArray()) {

            for (JsonNode tag : tagsNode) {

                tags.add(tag.path("name").asText());
            }
        }

        return tags;
    }

    public List<BlogPost> findPostsByAuthor(String author) {
        try {
            log.info("Listing recent posts for author: {}", author);
            String json = ghostClient.getPostsByAuthor(author);
            return parsePosts(json);

        } catch (Exception e) {
            log.error("Error while retrieving Ghost posts", e);
            throw new RuntimeException(
                    "Unable to retrieve Ghost posts",
                    e
            );
        }
    }
}