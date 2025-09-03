package fr.eletutour.modulith;

import fr.eletutour.modulith.product.Product;
import fr.eletutour.modulith.product.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Spring Modulith tutorial.
 * This class serves as the entry point for the Spring Boot application.
 */
@SpringBootApplication
public class Application {

    /**
     * The main method that starts the Spring Boot application.
     * It also retrieves the ProductService bean and creates a sample product.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args)
                .getBean(ProductService.class)
                .create(new Product("Erwan", "tutorial", 10));
    }
}
