package fr.eletutour.service;

import fr.eletutour.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WarehouseService {

    private final Map<String, Product> products = new ConcurrentHashMap<>();

    @PostConstruct
    private void loadInitialData() {
        addProduct("L001", "Laptop Pro");
        updateStock("L001", 15);

        addProduct("S001", "Smartphone X");
        updateStock("S001", 30);

        addProduct("M001", "Wireless Mouse");
        updateStock("M001", 75);
    }

    public Product addProduct(String id, String name) {
        if (products.containsKey(id)) {
            throw new IllegalArgumentException("Product with ID '" + id + "' already exists.");
        }
        Product newProduct = new Product(id, name);
        products.put(id, newProduct);
        return newProduct;
    }

    public Optional<Product> findProduct(String id) {
        return Optional.ofNullable(products.get(id));
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Product updateStock(String id, int quantityChange) {
        Product product = findProduct(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID '" + id + "' not found."));

        int newQuantity = product.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new IllegalStateException("Not enough stock for product '" + product.getName() + "'. Current stock: " + product.getQuantity());
        }
        product.setQuantity(newQuantity);
        return product;
    }
}