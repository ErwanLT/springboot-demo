package fr.eletutour.designpattern.strategy;

public interface BookCoteStrategy {
    String generateCote(String author);

    // Identifiant de la stratégie (clé utilisée pour la Map)
    String getType();
}
