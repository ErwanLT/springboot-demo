package fr.eletutour.ia.tools;

import dev.langchain4j.agent.tool.Tool;
import fr.eletutour.ia.service.RecipeService;
import fr.eletutour.ia.service.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CookingTools {

    private final Logger logger = LoggerFactory.getLogger(CookingTools.class);

    private final RecipeService recipeService;

    public CookingTools(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Tool
    public Recipe getRecipeByName(String name) {
        logger.info("get recipe by name");
        return recipeService.findRecipeByName(name);
    }

    @Tool
    public String suggestRecipe(String ingredient) {
        logger.info("get recipe for ingredient");
        return recipeService.suggestRecipeForIngredient(ingredient);
    }
}
