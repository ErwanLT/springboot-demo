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
