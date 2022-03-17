package de.hebkstudents.recipemanager.exception;

/**
 * Exception is thrown if a category of an ingredient can not be found but is requested
 */
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
