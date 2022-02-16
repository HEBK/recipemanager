package de.hebkstudents.recipemanager.recipe;

public class RecipeTemplate {
    //Attribute
    private String name;
    private String taste;
    private int time;
    private int dificulty;
    //private CostumList ingredients;
    private int defaultQuantity;
    private String description;

    //Konstruktor

    //Methoden
    private RecipeCategory getCategory(){
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

    public int getDificulty() {
        return dificulty;
    }

    public void setDificulty(int dificulty) {
        this.dificulty = dificulty;
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
