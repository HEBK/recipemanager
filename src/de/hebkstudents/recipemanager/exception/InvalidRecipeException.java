package de.hebkstudents.recipemanager.exception;

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
