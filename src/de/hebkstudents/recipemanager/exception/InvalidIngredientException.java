package de.hebkstudents.recipemanager.exception;

/**
 * Exception is thrown if an ingredient object has missing values that are needed to work properly or if the ingredient object is null
 */
public class InvalidIngredientException extends Exception {

    /**
     * Constructs a new InvalidIngredientException object
     * @param message A String which contains a message
     */
    public InvalidIngredientException(String message)
    {
        super(message);
    }

}
