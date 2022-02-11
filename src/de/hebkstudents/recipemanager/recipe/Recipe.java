package de.hebkstudents.recipemanager.recipe;

public class Recipe extends RecipeTemplate {
    //Attribute
    private int recipeID;

    //Konstruktor

    //Methoden
    private boolean save(){
        return false;
    }

    //get-/setter
    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
