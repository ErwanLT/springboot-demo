package fr.eletutour.ia.mcp.server.tools;

import fr.eletutour.ia.mcp.server.models.BlogPost;
import fr.eletutour.ia.mcp.server.service.GhostService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GhostTools {

    private final GhostService ghostService;

    public GhostTools(GhostService ghostService) {
        this.ghostService = ghostService;
    }

    @Tool(
        name = "listRecentPosts",
        description = """
            List the latest blog posts.
            """
    )
    public List<BlogPost> listRecentPosts() {

        return ghostService.listRecentPosts();
    }

    @Tool(
        name = "findPostsByTag",
        description = """
            Find blog posts using a specific tag.
            """
    )
    public List<BlogPost> findPostsByTag(String tag) {

        return ghostService.findPostsByTag(tag);
    }

    @Tool(
        name = "getPostBySlug",
        description = """
            Retrieve a complete blog post by its slug.
            """
    )
    public BlogPost getPostBySlug(String slug) {

        return ghostService.getPostBySlug(slug);
    }

    @Tool(
            name = "findPostsByAuthor",
            description = """
            Recherche les articles de blog écrits par un auteur spécifique (par exemple 'erwan').
            Retourne une liste contenant les métadonnées et le 'slug' des articles.
            """
    )
    public List<BlogPost> findPostsByAuthor(String author) {
        return ghostService.findPostsByAuthor(author);
    }
}