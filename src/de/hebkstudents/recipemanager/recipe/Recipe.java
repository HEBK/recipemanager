package de.hebkstudents.recipemanager.recipe;

import de.hebkstudents.recipemanager.ingredient.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {

    //Attribute
    private Integer recipeID;
    private String name;
    private int time;
    private int difficulty;
    private Ingredient[] ingredients;
    private int defaultQuantity;
    private String description;
    private RecipeCategory category;

    //Konstruktor
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

    public Recipe(int recipeID)
    {

    }

    //Konstruktor

    //Methoden
    private boolean save() {
        if (recipeID == null) {
            return false;
        }
        return false;
    }

    public boolean isVegan()
    {
        return new ArrayList<>(Arrays.asList(getIngredients())).stream().allMatch(Ingredient::isVegan);
    }

    public boolean isVegetarian()
    {
        return new ArrayList<>(Arrays.asList(getIngredients())).stream().allMatch(Ingredient::isVegetarian);
    }

    //get-/setter
    public Integer getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public int getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(int defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public void setCategory(RecipeCategory category) {
        this.category = category;
    }
}