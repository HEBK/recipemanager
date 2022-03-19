package de.hebkstudents.recipemanager.exception;

public class InvalidMethodParameterException extends Exception {

    /**
     * Constructs a new InvalidRecipeException object
     * @param message A String which contains a message
     */
    public InvalidMethodParameterException(String message)
    {
        super(message);
    }
}
