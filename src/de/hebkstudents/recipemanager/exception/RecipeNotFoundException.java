package de.hebkstudents.recipemanager.exception;

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
