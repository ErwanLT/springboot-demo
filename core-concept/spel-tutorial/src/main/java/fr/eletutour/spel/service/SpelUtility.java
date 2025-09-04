package fr.eletutour.spel.service;

import org.springframework.stereotype.Component;

@Component("spelUtility")
public class SpelUtility {

    public boolean isGreaterThan(int number, int target) {
        return number > target;
    }
}
