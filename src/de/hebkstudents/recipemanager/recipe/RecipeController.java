package de.hebkstudents.recipemanager.recipe;

import de.hebkstudents.recipemanager.exception.InvalidRecipeException;
import de.hebkstudents.recipemanager.exception.RecipeCategoryNotFoundException;
import de.hebkstudents.recipemanager.exception.RecipeNotFoundException;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class which handles the Recipes
 */
public class RecipeController {

    /**
     * Adds a new Recipe to the database using its object
     * @param recipe Valid Recipe Object
     * @return true if recipe was added successfully
     * @throws InvalidRecipeException If a required property of Recipe is missing
     */
    public static boolean addRecipe (Recipe recipe) throws InvalidRecipeException {

        ArrayList<Boolean> status = new ArrayList<>();

        if (recipe == null) {
            throw new InvalidRecipeException("The recipe object is broken an can therefore not get added!");
        }
        if (recipe.getRecipeID() != null) {
            Logger.log(LogType.ERROR, "Cannot add recipe! (Recipe has an ID)");
            return false;
        }

        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("INSERT INTO Recipe (name, time, difficulty, description, defaultQuantity, category) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, recipe.getName());
            ps.setInt(2, recipe.getTime());
            ps.setInt(3, recipe.getDifficulty());
            ps.setString(4, recipe.getDescription());
            ps.setInt(5, recipe.getDefaultQuantity());
            ps.setInt(6, recipe.getCategory().getCategoryID());

            status.add(ps.executeUpdate() > 0);

            if (status.get(0) && getLastID() != null) {
                for (Ingredient i : recipe.getIngredients()) {
                    PreparedStatement preparedStatement = DatabaseController.getConnection().prepareStatement("INSERT INTO RecipeIngredient (recipeID, ingredientID, unitID, quantity) VALUES (?, ?, ?, ?)");
                    preparedStatement.setInt(1, getLastID());
                    preparedStatement.setInt(2, i.getIngredientID());
                    preparedStatement.setInt(3, i.getUnit().getUnitID());
                    preparedStatement.setInt(4, i.getQuantity());
                    status.add(preparedStatement.executeUpdate() > 0);
                }

                if (status.stream().allMatch(j -> j)) Logger.log(LogType.INFORMATION, "Added recipe '" + recipe.getName() + "' with " + recipe.getIngredients().length + " ingredients to the database!");
                else Logger.log(LogType.WARNING, "Added recipe '" + recipe.getName() + "' to the database but only " + (Collections.frequency(status, true)-1) + " of " + (status.size()-1) + " could be added to the recipe!");
                return true;
            } else {
                Logger.log(LogType.ERROR, "Cannot add Recipe '"+recipe.getName()+"' to the database!");
                return false;
            }
        } catch (SQLException e) {
            Logger.logException(e);
        }
        return false;
    }

    /**
     * Gets the ID of the last added Recipe
     * @return ID of the last recipe
     */
    private static @Nullable Integer getLastID()
    {
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT max(recipeID) as `ID` FROM Recipe");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Cannot get last recipeID due to SQLException");
            Logger.logException(e);
        }
        return null;
    }

    /**
     * Gets all Recipes as Objects in a Recipe Array
     * @return Recipe Array
     */
    public static Recipe @NotNull [] getRecipes()
    {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM Recipe");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                recipes.add(new Recipe(rs.getInt(1), rs.getString(2), rs.getString(5), IngredientController.getIngredientsForRecipe(rs.getInt(1)), rs.getInt(6), rs.getInt(3), rs.getInt(4), RecipeCategoryController.getCategory(rs.getInt(7))));
            }
        } catch (SQLException e) {
            Logger.logException(e);
        } catch (RecipeCategoryNotFoundException | RecipeNotFoundException e) {
            e.printStackTrace();
        }
        Recipe[] recipesArray = new Recipe[recipes.size()];
        recipes.toArray(recipesArray);
        return recipesArray;
    }

    /**
     * Gets a recipe by its ID from the database
     * @param recipeID ID of the Recipe
     * @return Recipe-Object if recipe exists
     * @throws RecipeNotFoundException If recipe does not exist
     */
    public static Recipe getRecipe (int recipeID) throws RecipeNotFoundException {
        if (!recipeExists(recipeID)) {
            throw new RecipeNotFoundException("The recipe with ID '" + recipeID + "' could not be found!");
        }

        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM Recipe WHERE recipeID = ?");
            ps.setInt(1, recipeID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Recipe(rs.getInt(1), rs.getString(2), rs.getString(5), IngredientController.getIngredientsForRecipe(rs.getInt(1)), rs.getInt(6), rs.getInt(3), rs.getInt(4), RecipeCategoryController.getCategory(rs.getInt(7)));
            }
        } catch (SQLException e) {
            Logger.logException(e);
        } catch (RecipeCategoryNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks whether a recipe exists or not
     * @param recipeID ID of the recipe
     * @return true if the recipe exists
     */
    public static boolean recipeExists(int recipeID)
    {
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM Recipe WHERE recipeID = ?");
            ps.setInt(1, recipeID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.logException(e);
            return false;
        }
    }

    /**
     * Converts the difficulty integer of a recipe object into the difficulty string
     * @param difficulty Difficulty as integer
     * @return Difficulty as string
     */
    public static String getDifficultyByID(int difficulty)
    {
        return switch (difficulty) {
            case 0 -> "Anfänger";
            case 1 -> "Fortgeschritten";
            case 2 -> "Experte";
            default -> "n/A";
        };
    }

    /**
     * Deletes a recipe from the database
     * @param recipeID ID of the recipe
     * @return true if the recipe was deleted
     * @throws RecipeNotFoundException If the recipe does not exist
     */
    public static boolean deleteRecipe(int recipeID) throws RecipeNotFoundException
    {
        if (!recipeExists(recipeID)) throw new RecipeNotFoundException("The recipe with ID '" + recipeID + "' could not be found!");

        ArrayList<Ingredient> recipeIngredients = new ArrayList<>(Arrays.asList(IngredientController.getIngredientsForRecipe(recipeID)));
        ArrayList<Boolean> status = new ArrayList<>();
        for (Ingredient ingredient : recipeIngredients) {
            status.add(removeIngredientFromRecipe(recipeID, ingredient.getIngredientID()));
        }
        if (status.stream().allMatch(j -> j)) {
            try {
                PreparedStatement ps = DatabaseController.getConnection().prepareStatement("DELETE FROM Recipe WHERE recipeID = ?");
                ps.setInt(1, recipeID);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                Logger.log(LogType.ERROR, "Unnable to delete recipe '" + recipeID + "'! (SQLException)");
                Logger.logException(e);
                return false;
            }
        }
        Logger.log(LogType.ERROR, "Cannot delete recipe '" + recipeID + "'! Because the recipe depends on ingredients that can't get removed from it!");
        return false;
    }

    /**
     * Removes an Ingredient from a recipe
     * @param recipeID ID of the recipe
     * @param ingredientID ID of the ingredient
     * @return true if recipe has this ingredient and if the ingredient was deleted
     * @throws RecipeNotFoundException If the recipe does not exist
     */
    private static boolean removeIngredientFromRecipe(int recipeID, int ingredientID) throws RecipeNotFoundException {

        if (!recipeExists(recipeID)) throw new RecipeNotFoundException("The recipe with ID '" + recipeID + "' could not be found!");
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("DELETE FROM RecipeIngredient WHERE recipeID = ? AND ingredientID = ?");
            ps.setInt(1, recipeID);
            ps.setInt(2, ingredientID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Unnable to remove ingredient '" + ingredientID + "' from recipe '" + recipeID + "'!");
            Logger.logException(e);
            return false;
        }
    }



}
