package fr.eletutour.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class JmsTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmsTutorialApplication.class, args);
    }

}
