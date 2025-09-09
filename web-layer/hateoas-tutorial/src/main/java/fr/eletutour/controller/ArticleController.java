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
package fr.eletutour.controller;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.model.Article;
import fr.eletutour.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des articles.
 * Ce contrôleur fournit :
 * <ul>
 *     <li>La récupération de tous les articles</li>
 *     <li>La récupération d'un article par son ID</li>
 *     <li>La suppression d'un article</li>
 *     <li>Des liens HATEOAS pour la navigation</li>
 * </ul>
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    /**
     * Constructeur du contrôleur.
     *
     * @param articleService Le service de gestion des articles
     */
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Récupère la liste de tous les articles.
     * Les réponses incluent des liens HATEOAS pour la navigation.
     *
     * @return La liste des articles
     */
    @GetMapping
    public List<Article> getArticles() {
        return articleService.getArticles();
    }

    /**
     * Récupère un article par son identifiant.
     * La réponse inclut des liens HATEOAS pour la navigation.
     *
     * @param id L'identifiant de l'article
     * @return L'article trouvé
     * @throws ArticleNotFoundException Si l'article n'est pas trouvé
     */
    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id) throws ArticleNotFoundException {
        return articleService.getArticleById(id);
    }

    /**
     * Supprime un article par son identifiant.
     *
     * @param id L'identifiant de l'article à supprimer
     * @return Une réponse vide avec le code 204 (No Content)
     * @throws ArticleNotFoundException Si l'article n'est pas trouvé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) throws ArticleNotFoundException {
        articleService.deleteArticle(id);
        return ResponseEntity.noContent().build();
    }
}
