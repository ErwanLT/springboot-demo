package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application Spring Data REST.
 * Expose des repositories Spring Data en API REST sans controllers.
 */
@SpringBootApplication
public class DataRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataRestApplication.class, args);
    }
}
