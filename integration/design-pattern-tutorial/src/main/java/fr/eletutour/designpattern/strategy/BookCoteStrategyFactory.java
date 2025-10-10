package fr.eletutour.designpattern.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class BookCoteStrategyFactory {

    private final Map<String, BookCoteStrategy> strategies;

    // Spring injecte automatiquement toutes les stratégies connues
    public BookCoteStrategyFactory(Map<String, BookCoteStrategy> strategies) {
        this.strategies = strategies;
    }

    public BookCoteStrategy getStrategy(String type) {
        // Recherche par type
        return strategies.values().stream()
                .filter(s -> s.getType().equalsIgnoreCase(type))
                .findFirst()
                .orElseGet(() -> strategies.values().stream()
                        .filter(s -> "Default".equalsIgnoreCase(s.getType()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Default strategy not found")));
    }
}
