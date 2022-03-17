package de.hebkstudents.recipemanager.exception;

/**
 * Exception is thrown if a recipe object has missing values that are needed to work properly or if the recipe obejct is null
 */
public class InvalidRecipeException extends Exception {

    /**
     * Constructs a new InvalidRecipeException object
     * @param message A String which contains a message
     */
    public InvalidRecipeException(String message)
    {
        super(message);
    }
}
