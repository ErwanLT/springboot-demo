package fr.eletutour;

import fr.eletutour.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreetingApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(GreetingApplication.class);

    private final GreetingService greetingService;

    public GreetingApplication(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GreetingApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info(greetingService.greet());
    }
}
