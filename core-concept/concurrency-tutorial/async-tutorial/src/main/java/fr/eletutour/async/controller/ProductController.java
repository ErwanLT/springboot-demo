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
package fr.eletutour.async.controller;

import fr.eletutour.async.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}/details")
    public CompletableFuture<String> getProductDetails(@PathVariable Long id) {
        return productService.getFullProductDetails(id);
    }

    @GetMapping("/{id}/details-fail-fast")
    public CompletableFuture<String> getProductDetailsOrFailFast(@PathVariable Long id) {
        return productService.getFullProductDetailsOrFailFast(id);
    }
}
