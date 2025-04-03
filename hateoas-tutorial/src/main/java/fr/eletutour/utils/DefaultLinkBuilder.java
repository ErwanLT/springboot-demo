package fr.eletutour.utils;

import fr.eletutour.controller.ArticleController;
import fr.eletutour.controller.AuthorController;
import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

@Component
public class DefaultLinkBuilder implements LinkBuilder {
    @Override
    public Link authorSelfLink(Long authorId) throws AuthorNotFoundException {
        return linkTo(methodOn(AuthorController.class).getAuthorById(authorId)).withSelfRel();
    }

    @Override
    public Link authorsListLink() throws AuthorNotFoundException, ArticleNotFoundException {
        return linkTo(methodOn(AuthorController.class).getAuthors()).withRel("authors");
    }

    @Override
    public Link articleSelfLink(Long articleId) throws ArticleNotFoundException {
        return linkTo(methodOn(ArticleController.class).getArticleById(articleId)).withSelfRel();
    }

    @Override
    public Link articlesListLink() {
        return linkTo(methodOn(ArticleController.class).getArticles()).withRel("articles");
    }
}