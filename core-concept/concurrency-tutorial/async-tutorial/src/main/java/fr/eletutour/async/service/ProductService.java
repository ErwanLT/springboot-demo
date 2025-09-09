/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of async-tutorial.
 *
 * async-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * async-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with async-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
