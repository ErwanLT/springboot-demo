package fr.eletutour.integration.grpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the gRPC tutorial.
 */
@SpringBootApplication
public class GrpcTutorialApplication {

    /**
     * Main method to run the Spring Boot application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(GrpcTutorialApplication.class, args);
    }
}
