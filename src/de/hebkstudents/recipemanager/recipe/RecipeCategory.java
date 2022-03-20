package de.hebkstudents.recipemanager.recipe;

/**
 * Class for RecipeCategory objects
 */
public class RecipeCategory {

    /**
     * ID of the RecipeCategory
     */
    private Integer categoryID;

    /**
     * Name of the RecipeCategory
     */
    private String name;

    /**
     * Constructs a new category
     * @param name Name of the category
     */
    public RecipeCategory(String name)
    {
        this.name = name;
    }

    /**
     * Constructs a new category
     * @param categoryID ID of the category
     * @param name Name of the category
     */
    public RecipeCategory(int categoryID, String name)
    {
        this.categoryID = categoryID;
        this.name = name;
    }

    /**
     * Gets the ID of the category
     * @return ID of the category
     */
    public Integer getCategoryID() {
        return categoryID;
    }

    /**
     * Sets the name for the category
     * @param name Name of the catgory
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the category
     * @return Name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * @return Name of the category
     */
    @Override
    public String toString() {
        return getName();
    }
}
