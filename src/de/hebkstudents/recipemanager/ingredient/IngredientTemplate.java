package de.hebkstudents.recipemanager.ingredient;

public class IngredientTemplate {
    //Attribute
    private String name;
    private boolean isVegan;
    private boolean isVegetarian;


    //Konstruktor

    //Methoden
    private IngredientCategory getIngredientCategory() {
        return null;
    }

    //get-/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }
}
