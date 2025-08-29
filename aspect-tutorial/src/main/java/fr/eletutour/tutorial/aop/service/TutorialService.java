package fr.eletutour.tutorial.aop.service;

import org.springframework.stereotype.Service;

@Service
public class TutorialService {

    public String sayHello(String name) {
        return "Hello " + name;
    }
}
