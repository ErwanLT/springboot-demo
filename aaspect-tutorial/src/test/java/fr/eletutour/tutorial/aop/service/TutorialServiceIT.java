package fr.eletutour.tutorial.aop.service;

import fr.eletutour.tutorial.aop.AopTutorial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AopTutorial.class)
class TutorialServiceIT {

    @Autowired
    private TutorialService tutorialService;

    @Test
    void should_log_hello_when_helloMethode_is_called() {
        tutorialService.sayHello("Erwan");
    }
}