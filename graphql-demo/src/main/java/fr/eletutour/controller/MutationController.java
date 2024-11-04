package fr.eletutour.controller;

import fr.eletutour.model.Article;
import fr.eletutour.model.Author;
import fr.eletutour.service.ArticleService;
import fr.eletutour.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MutationController {
    private final AuthorService authorService;
    private final ArticleService articleService;

    public MutationController(AuthorService authorService, ArticleService articleService) {
        this.authorService = authorService;
        this.articleService = articleService;
    }

    @MutationMapping
    public Author createAuthor(@Argument String name, @Argument String bio) {
        return authorService.createAuthor(name, bio);
    }

    @MutationMapping
    public Article createArticle(@Argument String title, @Argument String content, @Argument Long authorId) {
        return articleService.createArticle(title, content, authorId);
    }
}
