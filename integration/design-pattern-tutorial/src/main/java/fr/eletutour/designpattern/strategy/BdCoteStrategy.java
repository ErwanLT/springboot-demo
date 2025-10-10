package fr.eletutour.designpattern.strategy;


import org.springframework.stereotype.Component;

@Component
public class BdCoteStrategy implements BookCoteStrategy {

    @Override
    public String generateCote(String author) {
        return "BD-" + author.substring(0, Math.min(4, author.length())).toUpperCase();
    }

    @Override
    public String getType() {
        return "BD";
    }
}
