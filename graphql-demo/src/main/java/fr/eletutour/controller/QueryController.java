package fr.eletutour.controller;

import fr.eletutour.model.Article;
import fr.eletutour.model.Author;
import fr.eletutour.service.ArticleService;
import fr.eletutour.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class QueryController {
    private final AuthorService authorService;
    private final ArticleService articleService;

    public QueryController(AuthorService authorService, ArticleService articleService) {
        this.authorService = authorService;
        this.articleService = articleService;
    }

    @QueryMapping
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long id) {
        return authorService.getAuthorById(id).orElse(null);
    }

    @QueryMapping
    public List<Article> getArticles() {
        return articleService.getArticles();
    }

    @QueryMapping
    public Article getArticleById(@Argument Long id) {
        return articleService.getArticleById(id).orElse(null);
    }
}
