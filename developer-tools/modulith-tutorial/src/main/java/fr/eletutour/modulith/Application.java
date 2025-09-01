package fr.eletutour.modulith;

import fr.eletutour.modulith.product.Product;
import fr.eletutour.modulith.product.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args)
                .getBean(ProductService.class)
                .create(new Product("Erwan", "tutorial", 10));
    }
}
