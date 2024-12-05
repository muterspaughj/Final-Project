/**
 * Final Project
 * 
 * CSC 1061 - Computer Science II - Java
 * 
 * @author Jack Muterspaugh
 * @version 1.0
 * @since 12-5-2024
 */

import java.util.List;

public class Recipe {
    private String name;
    private List<String> ingredients;
    private String instructions;

    public Recipe(String name, List<String> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return "Recipe: " + name + "\n" +
               "Ingredients: " + String.join(", ", ingredients) + "\n" +
               "Instructions: " + instructions;
    }
}


