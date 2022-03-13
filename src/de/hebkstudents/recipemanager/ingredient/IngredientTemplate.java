package de.hebkstudents.recipemanager.ingredient;

public abstract class IngredientTemplate {
    //Attribute
    private String name;
    private boolean isVegan;
    private boolean isVegetarian;
    private final IngredientCategory category;


    //Konstruktor
    public IngredientTemplate(String name, boolean isVegan, boolean isVegetarian, IngredientCategory category)
    {
        this.name = name;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
        this.category = category;
    }

    //Methoden
    public IngredientCategory getIngredientCategory() {
        return category;
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
