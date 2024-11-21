package fr.eletutour.controller;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Article;
import fr.eletutour.service.ArticleService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @MutationMapping
    public Article createArticle(@Argument String title, @Argument String content, @Argument Long authorId) throws AuthorNotFoundException {
        return articleService.createArticle(title, content, authorId);
    }

    @QueryMapping
    public List<Article> getArticles() {
        return articleService.getArticles();
    }

    @QueryMapping
    public Article getArticleById(@Argument Long id) throws ArticleNotFoundException {
        return articleService.getArticleById(id);
    }
}
