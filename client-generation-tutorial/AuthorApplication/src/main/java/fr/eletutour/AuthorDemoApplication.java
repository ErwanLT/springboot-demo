package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorDemoApplication.class, args);
    }
}
