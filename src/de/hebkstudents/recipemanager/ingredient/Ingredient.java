package de.hebkstudents.recipemanager.ingredient;

public class Ingredient extends IngredientTemplate {
    //Attribute
    private int ingredientID;

    // Konstruktor
    public Ingredient(String name, boolean isVegan, boolean isVegetarian, IngredientCategory category) {
        super(name, isVegan, isVegetarian, category);
    }
    public Ingredient(int ingredientID, String name, boolean isVegan, boolean isVegetarian, IngredientCategory category) {
        super(name, isVegan, isVegetarian, category);
        this.ingredientID = ingredientID;
    }

    //Methoden
    private boolean save() {
        return false;
    }

    //get-/setter
    public int getIngredientID() {
        return ingredientID;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", isVegan: " + isVegan() + ", isVegetarian: " + isVegetarian() + ", Category: " + getIngredientCategory();
    }
}
