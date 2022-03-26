package de.hebkstudents.recipemanager.exception;

public class InvalidRecipeFilterException extends Exception {

    /**
     * Constructs a new InvalidRecipeFilterException object
     * @param message A String which contains a message
     */
    public InvalidRecipeFilterException(String message)
    {
        super(message);
    }
}
