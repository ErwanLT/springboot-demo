package fr.eletutour.caffeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CaffeineCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaffeineCacheApplication.class);
    }
}
