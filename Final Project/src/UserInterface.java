/**
 * Final Project
 * 
 * CSC 1061 - Computer Science II - Java
 * 
 * @author Jack Muterspaugh
 * @version 1.0
 * @since 12-5-2024
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javafx.scene.image.ImageView;

// GUI for Cookbook
public class UserInterface extends Application {
    private static final String RECIPES_FOLDER_PATH = "C:\\Users\\muter\\Desktop\\CompSci2\\Final Project\\recipes"; // File path is computer specific
    private Cookbook cookbook;
    private VBox recipeDisplayArea;

     
    @Override
    public void start(Stage primaryStage) {
        cookbook = new Cookbook();
        cookbook.loadRecipesFromDirectory(RECIPES_FOLDER_PATH);

        primaryStage.setTitle("Cookbook");

        BorderPane mainLayout = new BorderPane();
        recipeDisplayArea = new VBox(10);
        recipeDisplayArea.setPadding(new Insets(10));
        ScrollPane scrollPane = new ScrollPane(recipeDisplayArea);

        Label titleLabel = new Label("Flavors of the World");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
        titleLabel.setPadding(new Insets(10));
        
        ImageView imageView = new ImageView("file:resources/logo.jpg");
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(175);
        
        VBox topLayout = new VBox(5, titleLabel, imageView);
        topLayout.setPadding(new Insets(5));
        topLayout.setStyle("-fx-alignment: center;");

        Button showAllButton = new Button("Show All Recipes");
        showAllButton.setOnAction(_ -> showAllRecipes());

        Button searchButton = new Button("Search Recipe");
        searchButton.setOnAction(_ -> searchRecipe(primaryStage));

        Button fuzzySearchButton = new Button("Fuzzy Search Recipe");
        fuzzySearchButton.setOnAction(_ -> fuzzySearchRecipe(primaryStage));

        Button filterButton = new Button("Filter by Ingredient");
        filterButton.setOnAction(_ -> filterRecipes(primaryStage));

        Button addRecipeButton = new Button("Add New Recipe");
        addRecipeButton.setOnAction(_ -> addNewRecipe(primaryStage));

        VBox controls = new VBox(10, showAllButton, searchButton, fuzzySearchButton, filterButton, addRecipeButton);
        controls.setPadding(new Insets(10));
        controls.setStyle("-fx-background-color: #f4f4f4;");

        mainLayout.setTop(topLayout);
        mainLayout.setLeft(controls);
        mainLayout.setCenter(scrollPane);

        Scene scene = new Scene(mainLayout, 1920, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private void showAllRecipes() {
        recipeDisplayArea.getChildren().clear();
        List<Recipe> recipes = cookbook.getAllRecipes();
        if (recipes.isEmpty()) {
            recipeDisplayArea.getChildren().add(new Label("No recipes found."));
        } else {
            recipes.forEach(recipe -> recipeDisplayArea.getChildren().add(createRecipeCard(recipe)));
        }
    }

    private void filterRecipes(Stage stage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Filter by Ingredient");
        dialog.setHeaderText("Enter an ingredient to filter recipes:");
        dialog.showAndWait().ifPresent(ingredient -> {
            List<Recipe> filteredRecipes = cookbook.filterByIngredient(ingredient);
            displayResults(filteredRecipes);
        });
    }

    private void searchRecipe(Stage stage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Recipe");
        dialog.setHeaderText("Enter recipe name to search:");
        dialog.showAndWait().ifPresent(query -> {
            List<Recipe> results = cookbook.searchRecipe(query);
            displayResults(results);
        });
    }

    private void fuzzySearchRecipe(Stage stage) {
        TextInputDialog queryDialog = new TextInputDialog();
        queryDialog.setTitle("Fuzzy Search Recipe");
        queryDialog.setHeaderText("Enter recipe name to fuzzy search:");

        queryDialog.showAndWait().ifPresent(query -> {
            TextInputDialog distanceDialog = new TextInputDialog("2");
            distanceDialog.setTitle("Fuzzy Search Distance");
            distanceDialog.setHeaderText("Enter max distance for fuzzy search:");

            distanceDialog.showAndWait().ifPresent(distance -> {
                int maxDistance = Integer.parseInt(distance);
                List<Recipe> results = cookbook.fuzzySearchRecipe(query, maxDistance);
                displayResults(results);
            });
        });
    }

    private void addNewRecipe(Stage stage) {
        Dialog<Recipe> dialog = new Dialog<>();
        dialog.setTitle("Add New Recipe");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nameField = new TextField();
        nameField.setPromptText("Recipe Name");
        TextField ingredientsField = new TextField();
        ingredientsField.setPromptText("Ingredients (comma-separated)");
        TextArea instructionsArea = new TextArea();
        instructionsArea.setPromptText("Instructions");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Ingredients:"), 0, 1);
        grid.add(ingredientsField, 1, 1);
        grid.add(new Label("Instructions:"), 0, 2);
        grid.add(instructionsArea, 1, 2);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                String name = nameField.getText();
                List<String> ingredients = List.of(ingredientsField.getText().split(","));
                String instructions = instructionsArea.getText();
                return new Recipe(name, ingredients, instructions);
            }
            return null;
        });

        dialog.showAndWait().ifPresent(recipe -> {
            cookbook.addRecipe(recipe);

            File recipesFolder = new File(RECIPES_FOLDER_PATH);
            if (!recipesFolder.exists()) {
                recipesFolder.mkdirs();
            }

            String fileName = sanitizeFileName(recipe.getName()) + ".txt";
            File recipeFile = new File(recipesFolder, fileName);

            try (PrintWriter writer = new PrintWriter(recipeFile)) {
                writer.println(recipe.getName());
                writer.println(String.join(",", recipe.getIngredients()));
                writer.println(recipe.getInstructions());
            } catch (Exception e) {
                showErrorDialog("Error Saving Recipe", "Could not save the recipe to a file.");
            }
            showAllRecipes();
        });
    }

    private String sanitizeFileName(String name) {
        return name.replaceAll("[^a-zA-Z0-9\\-_ ]", "").replace(" ", "_");
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    

    private void displayResults(List<Recipe> results) {
        recipeDisplayArea.getChildren().clear();
        if (results.isEmpty()) {
            recipeDisplayArea.getChildren().add(new Label("No recipes found."));
        } else {
            results.forEach(recipe -> recipeDisplayArea.getChildren().add(createRecipeCard(recipe)));
        }
    }

    private VBox createRecipeCard(Recipe recipe) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-background-color: #fafafa;");
        card.setPrefWidth(600);
        card.setPrefHeight(500);

        Label nameLabel = new Label(recipe.getName());
        Label ingredientsLabel = new Label("Ingredients: " + String.join(", ", recipe.getIngredients()));
        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setPrefHeight(100);
        TextArea instructionsArea = new TextArea(recipe.getInstructions());
        instructionsArea.setWrapText(true);
        instructionsArea.setEditable(false);
        instructionsArea.setPrefHeight(300);

        card.getChildren().addAll(nameLabel, ingredientsLabel, instructionsArea);
        return card;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

