package fr.eletutour.utils;

import fr.eletutour.controller.ArticleController;
import fr.eletutour.controller.AuthorController;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

@Component
public class DefaultLinkBuilder implements LinkBuilder {
    @Override
    public Link authorSelfLink(Long authorId) {
        return linkTo(methodOn(AuthorController.class).getAuthorById(authorId)).withSelfRel();
    }

    @Override
    public Link authorsListLink() {
        return linkTo(methodOn(AuthorController.class).getAuthors()).withRel("authors");
    }

    @Override
    public Link authorDeleteLink(Long authorId) {
        return Link.of(linkTo(methodOn(AuthorController.class).deleteAuthor(authorId)).toUri().toString())
                .withRel("delete")
                .withType("DELETE");
    }

    @Override
    public Link articleSelfLink(Long articleId) {
        return linkTo(methodOn(ArticleController.class).getArticleById(articleId)).withSelfRel();
    }

    @Override
    public Link articlesListLink() {
        return linkTo(methodOn(ArticleController.class).getArticles()).withRel("articles");
    }

    @Override
    public Link articleDeleteLink(Long articleId) {
        return Link.of(linkTo(methodOn(ArticleController.class).deleteArticle(articleId)).toUri().toString())
                .withRel("delete")
                .withType("DELETE");
    }
}