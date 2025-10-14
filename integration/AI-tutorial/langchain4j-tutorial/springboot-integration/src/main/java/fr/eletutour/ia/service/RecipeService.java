package fr.eletutour.ia.service;

import fr.eletutour.ia.service.model.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    public Recipe findRecipeByName(String name) {
        // Dans un vrai cas, on interrogerait une base de données ou un fichier
        if (name.equalsIgnoreCase("chocolat chaud")) {
            return new Recipe(
                    "Chocolat chaud",
                    List.of("250 ml de lait", "40 g de chocolat noir", "1 c. à café de sucre"),
                    List.of("Faire chauffer le lait", "Ajouter le chocolat et le sucre", "Fouetter jusqu'à consistance lisse")
            );
        }
        if (name.equalsIgnoreCase("crêpes")) {
            return new Recipe(
                    "Crêpes",
                    List.of("250 g de farine", "3 œufs", "50 cl de lait", "1 pincée de sel", "1 c. à soupe d'huile"),
                    List.of("Mélanger les ingrédients", "Laisser reposer la pâte 30 min", "Cuire dans une poêle chaude")
            );
        }
        return new Recipe(name, List.of(), List.of("Désolé, je ne connais pas encore cette recette."));
    }

    public String suggestRecipeForIngredient(String ingredient) {
        return switch (ingredient.toLowerCase()) {
            case "œuf" -> "Omelette à la ciboulette";
            case "pomme" -> "Tarte aux pommes rustique";
            case "chocolat" -> "Mousse au chocolat maison";
            default -> "Je n’ai pas de recette précise pour cet ingrédient, mais tu peux l’ajouter à un gratin ou une salade.";
        };
    }
}
