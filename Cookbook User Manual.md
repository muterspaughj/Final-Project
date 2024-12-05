**Cookbook Application User Manual**

## **Introduction**

Welcome to the Cookbook Application\! This user-friendly tool allows you to browse, search, filter, and create recipes. Whether you're organizing family recipes or trying new ones, this application simplifies your cooking experience.

## **System Requirements**

* **Operating System**: Windows, macOS, or Linux  
* **Java Development Kit (JDK)**: Version 22.0.2 or higher  
* **JavaFX**: Installed and configured on your system

## **Installation Guide**

### **Step 1: Clone the Repository**

* Download the repository from this link:  
- https://github.com/muterspaughj/Final-Project

### **Step 2: Configure JavaFX**

Download and set up JavaFX from gluonhq.com/products/javafx/.

### **Step 3: Compile the Application**

1. Navigate to the `src` directory.  
2. Compile the program: javac \--module-path /path/to/javafx-sdk/lib \--add-modules javafx.controls UserInterface.java

**Step 4: Run the Application**

* java \--module-path /path/to/javafx-sdk/lib \--add-modules javafx.controls UserInterface

## **User Interface Overview**

### **Home Page**

* **Title**: "Flavors of the World" is displayed prominently at the top.  
* **Logo**: A decorative image adds a personal touch to the home page.  
* **Sidebar**: Contains buttons for navigation and performing actions.  
* **Recipe Display Area**: Lists all recipes or search results.

## **Features and Usage**

### **1\. Viewing All Recipes**

* Click **"Show All Recipes"** in the sidebar.  
* The recipe display area will list all recipes stored in the application.

### **2\. Searching for a Recipe**

* Click **"Search Recipe"**.  
* Enter the recipe name in the prompt and click **OK**.  
* Results matching the query will be displayed.

### **3\. Fuzzy Searching**

* Click **"Fuzzy Search Recipe"**.  
* Enter the approximate name of the recipe and a maximum distance for matching.  
* Recipes with similar names within the specified distance will be displayed.

### **4\. Filtering Recipes by Ingredient**

* Click **"Filter by Ingredient"**.  
* Enter an ingredient to filter recipes that include it.  
* The filtered results will appear in the display area.

### **5\. Adding a New Recipe**

* Click **"Add New Recipe"**.  
* Fill in the form with:  
  * **Recipe Name**  
  * **Ingredients** (comma-separated)  
  * **Instructions**  
* Click **OK** to save the recipe. It will be stored in the `recipes` folder as a text file.


