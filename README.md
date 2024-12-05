# Cookbook
## Overview:
The Cookbook Application is a JavaFX-based desktop application that allows users to store, view, and manage recipes. It features functionalities like adding new recipes, searching for recipes by name, filtering recipes by ingredients, and performing fuzzy searches. Recipes are saved as individual .txt files in a dedicated recipes folder.

## Features:
-   View All Recipes: Display all stored recipes in a scrollable interface.
-   Search Recipes: Search for recipes by their exact name.
-   Fuzzy Search: Perform a fuzzy search for recipes with a specified tolerance for name mismatches.
-   Filter by Ingredients: Filter recipes based on included ingredients.
-   Add New Recipe: Add new recipes via a user-friendly interface. Recipes are saved as .txt files in the recipes folder.
-   Dynamic Loading: Recipes are dynamically loaded from the recipes folder.



## File Structure
The workspace contains several folders of importance, where:

-   `resources`: this folder contains resources needed for the application
-   `src`: this folder contains the source code for the project
-   `Recipes`: This folder contains .txt files, each representing a recipe

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json`
> and update the related settings there.


## How to Use
### Prerequisites
-   Java JDK 22 or higher
-   JavaFX SDK: Ensure JavaFX is properly configured in you IDE or build system.

### Running the Application
-   Clone or download this repository.
-   Open the project in your IDE and configure the JavaFX SDK.
-   Run the `UserInterface` class.

### Adding Recipes
-  Click `Add New Recipe`
-  Fill in the recipe name, ingredients, and instructions.
-  Click OK. The recipe will be saved as a `.txt` file in the `recipes` folder and added to the list.

The path to the `Recipes` folder is defined as a constant in the `UserInterface` class:
-   `private static final String RECIPES_FOLDER_PATH = "C:\\path\\to\\recipes";`
Update this path as needed to reflect your system's directory structure.

## License
This project is open-source and available under the MIT License. Feel free to contribute or modify as needed.

## Author
Jack Muterspaugh
