/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of hateoas-tutorial.
 *
 * hateoas-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * hateoas-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with hateoas-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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