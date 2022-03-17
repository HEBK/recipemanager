package de.hebkstudents.recipemanager.exception;

/**
 * Exception is thrown if an ingredient can not be found but is requested
 */
public class IngredientNotFoundException extends Exception {

    /**
     * Constructs a new IngredientNotFoundException object
     * @param message A String which contains a message
     */
    public IngredientNotFoundException(String message)
    {
        super(message);
    }
}
