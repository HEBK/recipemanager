package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.util.Objects;

public class Ingredient {
    //Attribute
    private Integer ingredientID;
    private String name;
    private boolean isVegan;
    private boolean isVegetarian;
    private Integer quantity;
    private IngredientCategory category;
    private IngredientUnit unit;

    // Konstruktor
    public Ingredient(String name, boolean isVegan, boolean isVegetarian, IngredientCategory category)
    {

        this.name               = name;
        this.isVegan            = isVegan;
        this.isVegetarian       = isVegetarian;
        this.category           = category;
    }

    public Ingredient(int ingredientID, String name, boolean isVegan, boolean isVegetarian, IngredientCategory category)
    {
        this.ingredientID       = ingredientID;
        this.name               = name;
        this.isVegan            = isVegan;
        this.isVegetarian       = isVegetarian;
        this.category           = category;
    }

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

    //Methoden
    private boolean save() {
        return false;
    }

    //get-/setter
    public Integer getIngredientID() {
        return ingredientID;
    }

    @Override
    public String toString() {
        return getName();
    }

    public IngredientCategory getIngredientCategory() {
        return category;
    }

    public void setIngredientCategory(IngredientCategory category) {
        this.category = category;
    }

    public IngredientUnit getUnit() {
        return unit;
    }

    public void setUnit(IngredientUnit unit) {
        this.unit = unit;
    }

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

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
