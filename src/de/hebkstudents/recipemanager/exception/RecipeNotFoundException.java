package de.hebkstudents.recipemanager.exception;

/**
 * Exception is thrown if a recipe can not be found but is requested
 */
public class RecipeNotFoundException extends Exception {

    /**
     * Constructs a new RecipeNotFoundException object
     * @param message A String which contains a message
     */
    public RecipeNotFoundException(String message)
    {
        super(message);
    }
}
