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
package fr.eletutour.service;

import fr.eletutour.dao.ArticleRepository;
import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Article;
import fr.eletutour.model.Author;
import fr.eletutour.utils.LinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des articles.
 * Ce service fournit :
 * <ul>
 *     <li>La récupération des articles</li>
 *     <li>La création d'articles</li>
 *     <li>La suppression d'articles</li>
 *     <li>L'ajout de liens HATEOAS</li>
 * </ul>
 */
@Service
public class ArticleService {
    private static final String ARTICLE_NOT_FOUND_MESSAGE = "Article non trouvé pour l'id : ";
    private final ArticleRepository articleRepository;
    private final AuthorService authorService;
    private final LinkBuilder linkBuilder;

    /**
     * Constructeur du service.
     *
     * @param articleRepository Le repository des articles
     * @param authorService Le service de gestion des auteurs
     * @param linkBuilder Le constructeur de liens HATEOAS
     */
    public ArticleService(ArticleRepository articleRepository,
                          AuthorService authorService,
                          LinkBuilder linkBuilder) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
        this.linkBuilder = linkBuilder;
    }

    /**
     * Récupère la liste de tous les articles avec leurs liens HATEOAS.
     *
     * @return La liste des articles avec leurs liens
     */
    public List<Article> getArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(this::addArticleLinks)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un article par son identifiant avec ses liens HATEOAS.
     *
     * @param id L'identifiant de l'article
     * @return L'article trouvé avec ses liens
     * @throws ArticleNotFoundException Si l'article n'est pas trouvé
     */
    public Article getArticleById(Long id) throws ArticleNotFoundException {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ARTICLE_NOT_FOUND_MESSAGE + id));
        return addArticleLinks(article);
    }

    /**
     * Crée un nouvel article.
     *
     * @param title Le titre de l'article
     * @param content Le contenu de l'article
     * @param authorId L'identifiant de l'auteur
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    public void createArticle(String title, String content, Long authorId) throws AuthorNotFoundException {
        Author author = authorService.getAuthorById(authorId);
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(author);
        articleRepository.save(article);
    }

    /**
     * Ajoute les liens HATEOAS à un article.
     *
     * @param article L'article à enrichir
     * @return L'article avec ses liens
     */
    private Article addArticleLinks(Article article) {
        article.add(linkBuilder.articleSelfLink(article.getId()));
        article.add(linkBuilder.articlesListLink());
        article.add(linkBuilder.authorSelfLink(article.getAuthor().getId()));
        article.add(linkBuilder.articleDeleteLink(article.getId()));
        return article;
    }

    /**
     * Supprime un article par son identifiant.
     *
     * @param id L'identifiant de l'article à supprimer
     * @throws ArticleNotFoundException Si l'article n'est pas trouvé
     */
    public void deleteArticle(Long id) {
        var article = articleRepository.findById(id);
        if (article.isPresent()) {
            articleRepository.delete(article.get());
        } else {
            throw new ArticleNotFoundException(ARTICLE_NOT_FOUND_MESSAGE + id);
        }
    }
}