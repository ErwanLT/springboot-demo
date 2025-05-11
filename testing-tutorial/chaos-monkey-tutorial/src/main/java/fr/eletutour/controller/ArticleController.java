package fr.eletutour.controller;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.model.Article;
import fr.eletutour.service.ArticleService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getArticleByIdAvecTimeout(@PathVariable Long id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Article> future = executor.submit(() -> articleService.getArticleById(id));

        try {
            Article article = future.get(2, TimeUnit.SECONDS); // Timeout de 2 secondes
            return ResponseEntity.ok(article);
        } catch (TimeoutException e) {
            future.cancel(true); // Interrompt le thread si encore actif
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                    .body("La récupération de l'article a dépassé le temps imparti.");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof ArticleNotFoundException) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Article non trouvé.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération de l'article.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // bonne pratique : réinterrompre le thread
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Le traitement a été interrompu.");
        } finally {
            executor.shutdownNow();
        }
    }

}
