package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.util.Objects;

public class Ingredient {

    /**
     * ID of the ingredient
     */
    private Integer ingredientID;

    /**
     * Name of the ingredient
     */
    private String name;

    /**
     * Boolean if the ingredient is vegan
     */
    private boolean isVegan;
    /**
     * Boolean if the ingredient is vegetarian
     */
    private boolean isVegetarian;

    /**
     * Quantity of the ingredient
     */
    private Integer quantity;

    /**
     * Category of the ingredient
     */
    private IngredientCategory category;

    /**
     * Unit of the ingredient
     */
    private IngredientUnit unit;

    /**
     * Ingredient cunstructor.
     * @param name Ingredient name
     * @param isVegan Is the ingredient vegan
     * @param isVegetarian Is the ingredient vegetarian
     * @param category Category object for the ingredient
     */
    public Ingredient(String name, boolean isVegan, boolean isVegetarian, IngredientCategory category)
    {

        this.name               = name;
        this.isVegan            = isVegan;
        this.isVegetarian       = isVegetarian;
        this.category           = category;
    }

    /**
     * Ingredient constructor.
     * @param ingredientID ID of the ingredient
     * @param name Ingredient name
     * @param isVegan Is the ingredient vegan
     * @param isVegetarian Is the ingredient vegetarian
     * @param category Category object for the ingredient
     */
    public Ingredient(int ingredientID, String name, boolean isVegan, boolean isVegetarian, IngredientCategory category)
    {
        this.ingredientID       = ingredientID;
        this.name               = name;
        this.isVegan            = isVegan;
        this.isVegetarian       = isVegetarian;
        this.category           = category;
    }

    /**
     * Constructs an ingredient from its ID in the database
     * @param ingredientID IngredientID
     * @param quantity Quantity for the ingredient
     * @param unit Unit for the ingredient
     * @throws IngredientNotFoundException If an ingredient is requested that does not exist!
     */
    public Ingredient(int ingredientID, int quantity, IngredientUnit unit) throws IngredientNotFoundException {
        if (!IngredientController.ingredientExists(ingredientID)) {
            Logger.log(LogType.ERROR, "Ingredient does not exist!");
             throw new IngredientNotFoundException("Cannot get ingredient with ID '" + ingredientID + "'. (NOT_FOUND)");
        }

        Ingredient i = IngredientController.getIngredient(ingredientID);
        assert i != null;
        this.ingredientID       = ingredientID;
        this.name               = i.getName();
        this.isVegan            = i.isVegan();
        this.isVegetarian       = i.isVegetarian();
        this.category           = i.getIngredientCategory();
        this.quantity           = quantity;
        this.unit               = unit;
    }

    /**
     * Gets the ID of the ingredient
     * @return ID
     */
    public Integer getIngredientID() {
        return ingredientID;
    }

    /**
     * @return Name of the ingredient
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Gets the category object
     * @return IngredientCategory Object
     */
    public IngredientCategory getIngredientCategory() {
        return category;
    }

    /**
     * Sets the category object
     * @param category IngredientCategory Object
     */
    public void setIngredientCategory(IngredientCategory category) {
        this.category = category;
    }

    /**
     * Gets the unit for the ingredient
     * @return unit object
     */
    public IngredientUnit getUnit() {
        return unit;
    }

    /**
     * Gets the ingredient name
     * @return ingredeint name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
