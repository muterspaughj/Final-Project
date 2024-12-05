/**
 * Final Project
 * 
 * CSC 1061 - Computer Science II - Java
 * 
 * @author Jack Muterspaugh
 * @version 1.0
 * @since 12-5-2024
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Cookbook {
    private List<Recipe> recipes;

    public Cookbook() {
        recipes = new ArrayList<>();
    }

    public void loadRecipesFromDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists() || !dir.isDirectory()) return;
    
        for (File file : dir.listFiles((_, name) -> name.endsWith(".txt"))) {
            try (Scanner scanner = new Scanner(file)) {
                String name = scanner.nextLine().trim();
                List<String> ingredients = List.of(scanner.nextLine().trim().split(","));
                String instructions = scanner.useDelimiter("\\A").next().trim();
                recipes.add(new Recipe(name, ingredients, instructions));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void saveRecipeToFile(Recipe recipe, String directoryPath) {
        String fileName = recipe.getName().replaceAll("\\s+", "_") + ".txt";
        Path filePath = Paths.get(directoryPath, fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("# Recipe Name\n" + recipe.getName() + "\n\n");
            writer.write("# Ingredients\n" + String.join(", ", recipe.getIngredients()) + "\n\n");
            writer.write("# Instructions\n" + recipe.getInstructions());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public List<Recipe> searchRecipe(String query) {
        return recipes.stream()
                .filter(recipe -> recipe.getName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Recipe> fuzzySearchRecipe(String query, int maxDistance) {
        return recipes.stream()
                .map(recipe -> new AbstractMap.SimpleEntry<>(recipe, FuzzySearch.levenshteinDistance(recipe.getName().toLowerCase(), query.toLowerCase())))
                .filter(entry -> entry.getValue() <= maxDistance)
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
}

    public List<Recipe> filterByIngredient(String ingredient) {
        return recipes.stream()
                .filter(recipe -> recipe.getIngredients().stream()
                        .anyMatch(ing -> ing.toLowerCase().contains(ingredient.toLowerCase())))
                .collect(Collectors.toList());
    }
}



