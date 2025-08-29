package fr.eletutour.utils;

import fr.eletutour.controller.ArticleController;
import fr.eletutour.controller.AuthorController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Implémentation par défaut du constructeur de liens HATEOAS.
 * Cette classe fournit :
 * <ul>
 *     <li>La création de liens pour les auteurs (self, list, delete)</li>
 *     <li>La création de liens pour les articles (self, list, delete)</li>
 *     <li>L'utilisation de WebMvcLinkBuilder pour générer les liens</li>
 * </ul>
 */
@Component
public class DefaultLinkBuilder implements LinkBuilder {
    /**
     * {@inheritDoc}
     */
    @Override
    public Link authorSelfLink(Long authorId) {
        return linkTo(methodOn(AuthorController.class).getAuthorById(authorId)).withSelfRel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Link authorsListLink() {
        return linkTo(methodOn(AuthorController.class).getAuthors()).withRel("authors");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Link authorDeleteLink(Long authorId) {
        return Link.of(linkTo(methodOn(AuthorController.class).deleteAuthor(authorId)).toUri().toString())
                .withRel("delete")
                .withType("DELETE");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Link articleSelfLink(Long articleId) {
        return linkTo(methodOn(ArticleController.class).getArticleById(articleId)).withSelfRel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Link articlesListLink() {
        return linkTo(methodOn(ArticleController.class).getArticles()).withRel("articles");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Link articleDeleteLink(Long articleId) {
        return Link.of(linkTo(methodOn(ArticleController.class).deleteArticle(articleId)).toUri().toString())
                .withRel("delete")
                .withType("DELETE");
    }
}