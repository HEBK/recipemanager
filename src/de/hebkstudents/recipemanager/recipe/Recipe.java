package de.hebkstudents.recipemanager.recipe;

import de.hebkstudents.recipemanager.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to generate objects of recipes
 */
public class Recipe {

    /**
     * ID of the recipe
     */
    private Integer recipeID;

    /**
     * Name of the recipe
     */
    private String name;

    /**
     * Needed time for the recipe in minutes
     */
    private int time;

    /**
     * Difficulty of the recipe (0 - 2)
     */
    private int difficulty;

    /**
     * Array of all needed Ingredients including the quantities and units
     */
    private Ingredient[] ingredients;

    /**
     * Default amount of portions
     */
    private int defaultQuantity;

    /**
     * Description of the recipe
     */
    private String description;

    /**
     * Category object of this recipe
     */
    private RecipeCategory category;

    /**
     * Constructs a new recipe
     * @param name Name of the recipe
     * @param description Description of the recipe
     * @param ingredients Array of all needed Ingredients including the quantities and units
     * @param defaultQuantity Default amount of portions
     * @param time Needed time for the recipe in minutes
     * @param difficulty Difficulty of the recipe (0 - 2)
     * @param category Category object of this recipe
     */
    public Recipe(String name, String description, Ingredient[] ingredients, int defaultQuantity, int time, int difficulty, RecipeCategory category)
    {
        this.name               = name;
        this.description        = description;
        this.ingredients        = ingredients;
        this.defaultQuantity    = defaultQuantity;
        this.time               = time;
        this.difficulty         = difficulty;
        this.category           = category;
    }

    /**
     * Constructs a new recipe
     * @param recipeID ID of the recipe
     * @param name Name of the recipe
     * @param description Description of the recipe
     * @param ingredients Array of all needed Ingredients including the quantities and units
     * @param defaultQuantity Default amount of portions
     * @param time Needed time for the recipe in minutes
     * @param difficulty Difficulty of the recipe (0 - 2)
     * @param category Category object of this recipe
     */
    public Recipe(int recipeID, String name, String description, Ingredient[] ingredients, int defaultQuantity, int time, int difficulty, RecipeCategory category)
    {
        this.recipeID           = recipeID;
        this.name               = name;
        this.description        = description;
        this.ingredients        = ingredients;
        this.defaultQuantity    = defaultQuantity;
        this.time               = time;
        this.difficulty          = difficulty;
        this.category           = category;
    }

    /**
     * Checks if all ingredients are vegan
     * -> Checks if the recipe is vegan
     * @return true if the recipe is vegan
     */
    public boolean isVegan()
    {
        return new ArrayList<>(Arrays.asList(getIngredients())).stream().allMatch(Ingredient::isVegan);
    }

    /**
     * Checks if all ingredients are vegetarian
     * -> Checks if the recipe is vegetarian
     * @return true if the recipe is vegetarian
     */
    public boolean isVegetarian()
    {
        return new ArrayList<>(Arrays.asList(getIngredients())).stream().allMatch(Ingredient::isVegetarian);
    }

    /**
     * Gets the ID of the recipe
     * @return ID of the recipe
     */
    public Integer getRecipeID() {
        return recipeID;
    }

    /**
     * Gets all Ingredients for this recipe as array
     * @return Ingredients-Array
     */
    public Ingredient[] getIngredients() {
        return ingredients;
    }

    /**
     * Sets the ingredients for this recipe
     * @param ingredients Ingredients-Array
     */
    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Gets the default portion count
     * @return default portion count
     */
    public int getDefaultQuantity() {
        return defaultQuantity;
    }

    /**
     * Sets the default portion count
     * @param defaultQuantity  default portion count
     */
    public void setDefaultQuantity(int defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    /**
     * Gets the difficulty of the recipe as integer
     * @return Difficulty Integer
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the needed minutes as Integer
     * @return Time in Minutes
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets the time in minutes for this recipe
     * @param time Time in minutes
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Gets the description of the recipe
     * @return Description of the recipe
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the name of the Recipe
     * @return name of the recipe
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the recipe
     * @param name Name of the recipe
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the category of the recipe
     * @return RecipeCategory Object
     */
    public RecipeCategory getCategory() {
        return category;
    }

    /**
     * Sets the category of the recipe
     * @param category RecipeCategory Object
     */
    public void setCategory(RecipeCategory category) {
        this.category = category;
    }
}