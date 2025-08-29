package fr.eletutour.async.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ProductService {

    private final ProductAsyncService asyncService;

    public ProductService(ProductAsyncService asyncService) {
        this.asyncService = asyncService;
    }

    public CompletableFuture<String> getFullProductDetails(Long productId) {
        CompletableFuture<String> price = asyncService.getPrice(productId)
                .exceptionally(ex -> "Prix indisponible");
        CompletableFuture<String> stock = asyncService.getStock(productId)
                .exceptionally(ex -> "Stock indisponible");
        CompletableFuture<String> reviews = asyncService.getReviews(productId)
                .exceptionally(ex -> "Avis indisponibles");

        return CompletableFuture.allOf(price, stock, reviews)
                .thenApply(v -> String.join(" | ",
                        price.join(), stock.join(), reviews.join()));
    }

    public CompletableFuture<String> getFullProductDetailsOrFailFast(Long productId) {
        CompletableFuture<String> price = asyncService.getPrice(productId);
        CompletableFuture<String> stock = asyncService.getStock(productId);
        CompletableFuture<String> reviews = asyncService.getReviews(productId);

        return CompletableFuture.allOf(price, stock, reviews)
                .thenApply(v -> String.join(" | ",
                        price.join(), stock.join(), reviews.join()));
    }
}
