/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of graphql-tutorial.
 *
 * graphql-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * graphql-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with graphql-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
