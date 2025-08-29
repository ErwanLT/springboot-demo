package fr.eletutour.async.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductAsyncService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductAsyncService.class);

    @Async
    public CompletableFuture<String> getPrice(Long productId) {
        LOGGER.info("get price for product {}", productId);
        simulateDelay();
        // Simuler une erreur pour un produit spécifique afin de tester la gestion d'erreur
        if (productId.equals(1L)) {
            LOGGER.error("Erreur simulée pour le produit 1");
            throw new RuntimeException("Erreur lors de la récupération du prix");
        }
        return CompletableFuture.completedFuture("Prix: 199€");
    }

    @Async
    public CompletableFuture<String> getStock(Long productId) {
        LOGGER.info("get stock");
        simulateDelay();
        return CompletableFuture.completedFuture("Stock: Disponible");
    }

    @Async
    public CompletableFuture<String> getReviews(Long productId) {
        LOGGER.info("get reviews");
        simulateDelay();
        return CompletableFuture.completedFuture("Avis: ★★★★☆");
    }

    private void simulateDelay() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
