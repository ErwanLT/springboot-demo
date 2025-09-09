/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of modulith-tutorial.
 *
 * modulith-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * modulith-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with modulith-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
