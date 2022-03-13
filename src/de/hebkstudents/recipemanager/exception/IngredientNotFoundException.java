package de.hebkstudents.recipemanager.exception;

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
