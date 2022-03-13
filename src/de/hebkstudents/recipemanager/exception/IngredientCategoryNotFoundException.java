package de.hebkstudents.recipemanager.exception;

public class IngredientCategoryNotFoundException extends Exception {

    /**
     * Constructs a new IngredientCategoryNotFoundException object
     * @param message A String which contains a message
     */
    public IngredientCategoryNotFoundException(String message)
    {
        super(message);
    }
}
