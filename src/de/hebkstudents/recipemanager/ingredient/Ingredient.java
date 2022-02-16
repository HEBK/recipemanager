package de.hebkstudents.recipemanager.ingredient;

public class Ingredient {
    //Attribute
    private int ingredientID;

    //Konstruktor

    //Methoden
    private boolean save() {
        return false;
    }
    //get-/setter
    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }
}
