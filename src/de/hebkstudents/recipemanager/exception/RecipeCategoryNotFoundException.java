package de.hebkstudents.recipemanager.exception;

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
