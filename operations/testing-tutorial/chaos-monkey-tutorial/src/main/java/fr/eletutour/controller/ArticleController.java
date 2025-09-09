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
package fr.eletutour.controller;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.model.Article;
import fr.eletutour.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> getArticles() {
        return articleService.getArticles();
    }

    @Operation(summary = "recherche d'un article", description = "Recherche d'un article à partir de son id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Article trouvé avec succès",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Article.class))}),
            @ApiResponse(responseCode = "404", description = "Aucun article avec cet id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "408", description = "Time out de la requête",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleByIdAvecTimeout(@PathVariable Long id) throws TimeoutException, InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Article> future = executor.submit(() -> articleService.getArticleById(id));

        try {
            Article article = future.get(2, TimeUnit.SECONDS); // Timeout de 2 secondes
            return ResponseEntity.ok(article);
        } finally {
            executor.shutdownNow();
        }
    }

}
