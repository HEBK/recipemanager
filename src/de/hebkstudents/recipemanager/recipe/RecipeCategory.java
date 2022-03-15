package de.hebkstudents.recipemanager.recipe;

public class RecipeCategory {

    //Attribute
    private Integer categoryID;
    private String name;

    //Konstruktor
    public RecipeCategory(String name)
    {
        this.name = name;
    }

    public RecipeCategory(int categoryID, String name)
    {
        this.categoryID = categoryID;
        this.name = name;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
