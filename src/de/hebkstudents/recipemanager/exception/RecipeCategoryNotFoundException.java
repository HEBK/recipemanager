package de.hebkstudents.recipemanager.exception;

/**
 * Exception is thrown if a category of a recipe can not be found but is requested
 */
public class RecipeCategoryNotFoundException extends Exception {

    /**
     * Constructs a new RecipeCategoryNotFoundException object
     * @param message A String which contains a message
     */
    public RecipeCategoryNotFoundException(String message)
    {
        super(message);
    }
}
