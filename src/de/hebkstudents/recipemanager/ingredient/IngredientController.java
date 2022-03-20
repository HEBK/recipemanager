package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.*;
import de.hebkstudents.recipemanager.recipe.RecipeController;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class which handles the ingredients
 */
public class IngredientController {

    /**
     * Adds a new Ingredient to the database
     * @param ingredient Valid Ingredient object
     * @return true if successfully added
     */
    public static boolean addIngredient(Ingredient ingredient)
    {
        if (!IngredientCategoryController.categoryExists(ingredient.getIngredientCategory())) {
            Logger.log(LogType.ERROR, "Ingredient could not be added because IngredientCategory '" + ingredient.getIngredientCategory().getCategoryID() + "' does not exist!");
            return false;
        }
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("INSERT INTO Ingredient (name, isVegan, isVegetarian, category) VALUES (?, ?, ?, ?)");
            ps.setString(1, ingredient.getName());
            ps.setBoolean(2, ingredient.isVegan());
            ps.setBoolean(3, ingredient.isVegetarian());
            ps.setInt(4, ingredient.getIngredientCategory().getCategoryID());

            if (ps.executeUpdate() > 0) {
                Logger.log(LogType.INFORMATION, "Ingredient '" + ingredient.getName() + "' has been added successfully!");
                return true;
            }
            Logger.log(LogType.ERROR, "Ingredient '" + ingredient.getName() + "' could not be added! Maybe it already exists.");
            return false;
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Ingredient could not be added because of an SQLException. Further information below.");
            Logger.logException(e);
            return false;
        }
    }

    /**
     * Deletes an Ingredient from the database
     * @param ingredient Valid Ingredient object
     * @return true if ingredient was deleted successfully
     * @throws IngredientNotFoundException If Ingredient does not exist
     * @throws InvalidIngredientException If a required property of Ingredient is missing
     */
    public static boolean deleteIngredient(Ingredient ingredient) throws IngredientNotFoundException, InvalidIngredientException  {

        if (ingredient == null || ingredient.getIngredientID() == null) throw new InvalidIngredientException("It seems that your Ingredient Object is corrupted! Please check if it's not null and an ID exists!");
        return deleteIngredient(ingredient.getIngredientID());
    }

    /**
     * Deletes an Ingredient from the database
     * @param ingredientID Existing ingredient ID
     * @return true if ingredient was deleted successfully
     * @throws IngredientNotFoundException If Ingredient does not exist
     */
    public static boolean deleteIngredient(int ingredientID) throws IngredientNotFoundException {
        if (!ingredientExists(ingredientID)) {
            throw new IngredientNotFoundException("An ingredient with ID '" + ingredientID + "' does not exist and can therefore not get deleted!");
        }
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("DELETE FROM Ingredient WHERE ingredientID = ?");
            ps.setInt(1, ingredientID);
            if (ps.executeUpdate() > 0) {
                Logger.log(LogType.INFORMATION, "Successfully deleted Ingredient '" + ingredientID + "'");
                return true;
            }
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Cannot delete Ingredient '" + ingredientID + "'! Further information is listed below.");
            Logger.logException(e);
        }
        return false;
    }

    /**
     * Gets an Ingredient by its ID from the database
     * @param ingredientID Existing ingredientID
     * @return Ingredient object if ingredient exists and is not corrupted
     * @throws IngredientNotFoundException If Ingredient does not exist
     */
    public static Ingredient getIngredient(int ingredientID) throws IngredientNotFoundException {
        if (!ingredientExists(ingredientID)) {
            throw new IngredientNotFoundException("An ingredient with ID '" + ingredientID + "' does not exist!");
        }
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM Ingredient WHERE ingredientID = ?");
            ps.setInt(1, ingredientID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Ingredient(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getBoolean(4), new IngredientCategory(rs.getInt(5)));
            }
        } catch (SQLException e) {
            Logger.logException(e);
        } catch (IngredientCategoryNotFoundException e) {
            Logger.log(LogType.ERROR, "Cannot get Ingredient because referenced IngredientCategory does not exist!");
            Logger.logException(e);
        }
        return null;
    }

    /**
     * Gets all Ingredients as an Array of type Ingredient from the database
     * @return Ingredient Array
     */
    public static Ingredient[] getIngredients()
    {
        return getIngredients(null);
    }

    /**
     * Gets all or filtered Ingredients as Ingredient Array from the database
     * @param filter Valid IngredientFilter object or null (null -> no filter -> all ingredients)
     * @return Ingredient Array with all/filtered Ingredients
     */
    public static Ingredient[] getIngredients(IngredientFilter filter)
    {
        String options = filter != null ? " " + (filter.getSQLOptions() != null ? filter.getSQLOptions() : "") : "";
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            String stmt = "SELECT * FROM Ingredient " + options;
            PreparedStatement statement = DatabaseController.getConnection().prepareStatement(stmt);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ingredients.add(new Ingredient(rs.getInt(1), rs.getString(2), rs.getBoolean(3), rs.getBoolean(4), new IngredientCategory(rs.getInt(5))));
            }
        } catch (SQLException | IngredientCategoryNotFoundException e) {
            Logger.logException(e);
        }
        Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
        ingredients.toArray(ingredientsArray);
        return ingredientsArray;
    }

    /**
     * Checks whether an Ingredient exists or not
     * @param ingredientID ID of the ingredient
     * @return true if ingredient exists
     */
    public static boolean ingredientExists(int ingredientID)
    {
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM Ingredient WHERE ingredientID = ?");
            ps.setInt(1, ingredientID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.logException(e);
            return false;
        }
    }

    /**
     * Gets all ingredients for a specific recipe as Object-Array of type Ingredient
     * @param recipeID ID of the recipe
     * @return Ingredient-Object-Array
     * @throws RecipeNotFoundException If the recipe does not exist
     */
    public static Ingredient[] getIngredientsForRecipe(int recipeID) throws RecipeNotFoundException {

        if (!RecipeController.recipeExists(recipeID)) {
            throw new RecipeNotFoundException("A recipe with ID '" + recipeID + "' does not exist!");
        }

        ArrayList<Ingredient> ingredients = new ArrayList<>();

        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT ingredientID, unitID, quantity FROM RecipeIngredient WHERE recipeID = ?");
            ps.setInt(1, recipeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ingredients.add(new Ingredient(rs.getInt(1), rs.getInt(3), IngredientUnitController.getUnit(rs.getInt(2))));
            }
        } catch (SQLException | IngredientNotFoundException | IngredientUnitNotFoundException e) {
            Logger.log(LogType.ERROR, "There was an error while getting the ingredients of recipe " + recipeID);
            Logger.logException(e);
        }
        Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
        ingredients.toArray(ingredientsArray);
        return ingredientsArray;
    }

    /**
     * Updates an Ingredient using its changed object
     * @param ingredient Valid Ingredient object
     * @return true if the ingredient was successfully updated
     * @throws InvalidIngredientException If a required property of Ingredient is missing
     * @throws IngredientNotFoundException If an ingredient with its ID does not exist in the database
     */
    public static boolean updateIngredient(Ingredient ingredient) throws InvalidIngredientException, IngredientNotFoundException {

        if (ingredient == null || ingredient.getIngredientID() == null || ingredient.getIngredientCategory() == null) {
            throw new InvalidIngredientException("Cannot update ingredient object. The ingredient object is invalid!");
        }
        if (!ingredientExists(ingredient.getIngredientID())) {
            throw new IngredientNotFoundException("An ingredient with ID '" + ingredient.getIngredientID() + "' does not exist!");
        }

        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("UPDATE Ingredient SET name = ?, category = ?, isVegan = ?, isVegetarian = ? WHERE ingredientID = ?");
            ps.setString(1, ingredient.getName());
            ps.setInt(2, ingredient.getIngredientCategory().getCategoryID());
            ps.setBoolean(3, ingredient.isVegan());
            ps.setBoolean(4, ingredient.isVegetarian());
            ps.setInt(5, ingredient.getIngredientID());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Cannot update ingredient " + ingredient.getName() + " because of an SQLException.");
            Logger.logException(e);
            return false;
        }
    }

    /**
     * Checks if an ingredient is used by any recipe
     * @param ingredientID ID of the recipe
     * @return true if its used by an recipe and the ingredient exists
     * @throws IngredientNotFoundException If the ingredient does not exist
     * @throws SQLException If dependencies cannot be checked
     */
    public static boolean isInUse(int ingredientID) throws IngredientNotFoundException, SQLException {
        if (!ingredientExists(ingredientID)) throw new IngredientNotFoundException("An ingredient with ID '" + ingredientID + "' does not exist!");

        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT ingredientID FROM RecipeIngredient WHERE ingredientID = ?");
            ps.setInt(1, ingredientID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Cannot check if ingredient is in use! (SQLException)");
            throw e;
        }
    }
}
