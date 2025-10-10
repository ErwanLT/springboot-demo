package fr.eletutour.designpattern.strategy;

import org.springframework.stereotype.Component;

@Component
public class RomanCoteStrategy implements BookCoteStrategy {

    @Override
    public String generateCote(String author) {
        return "R-" + author.substring(0, Math.min(4, author.length())).toUpperCase();
    }

    @Override
    public String getType() {
        return "Roman";
    }
}
