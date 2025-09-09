/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of chaos-monkey-tutorial.
 *
 * chaos-monkey-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * chaos-monkey-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with chaos-monkey-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.service;

import fr.eletutour.dao.ArticleRepository;
import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Article;
import fr.eletutour.model.Author;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final AuthorService authorService;

    public ArticleService(ArticleRepository articleRepository, AuthorService authorService) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) throws ArticleNotFoundException {
        return articleRepository.findById(id).orElseThrow( () -> new ArticleNotFoundException("Article non trouvé pour l'id : " + id, id));
    }

    public Article createArticle(String title, String content, Long authorId) throws AuthorNotFoundException {
        Author author = authorService.getAuthorById(authorId);
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(author);
        return articleRepository.save(article);
    }

    @PostConstruct
    private void init() throws AuthorNotFoundException {
        createArticle("Harry Potter", "Harry Potter is a series of seven fantasy novels written by British author J. K. Rowling.", 1L);
        createArticle("The Shining", "The Shining is a horror novel by American author Stephen King.", 2L);
    }
}