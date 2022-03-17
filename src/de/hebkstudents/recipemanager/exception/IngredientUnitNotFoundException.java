package de.hebkstudents.recipemanager.exception;

/**
 * Exception is thrown if a unit of an ingredient can not be found but is requested
 */
public class IngredientUnitNotFoundException extends Exception {

    /**
     * Constructs a new IngredientUnitNotFoundException object
     * @param message A String which contains a message
     */
    public IngredientUnitNotFoundException(String message)
    {
        super(message);
    }


}
