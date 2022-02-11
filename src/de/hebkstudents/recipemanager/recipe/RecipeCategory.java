package de.hebkstudents.recipemanager.recipe;

public class RecipeCategory {
    //Attribute
    private String name;
    private String taste;
    private int time;
    private int difficulty;
    //private CustomList ingredients;
    private int defaultQuantity;
    private String description;

    //Konstruktor
    //Methoden
    private RecipeCategory getCategory() {
        return null;
    }

    //get-/setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(int defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
