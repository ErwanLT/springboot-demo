package fr.eletutour.outbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransactionalOutboxApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionalOutboxApplication.class, args);
    }
}
