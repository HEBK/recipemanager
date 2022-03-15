package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientCategoryNotFoundException;
import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.exception.IngredientUnitNotFoundException;
import de.hebkstudents.recipemanager.exception.RecipeNotFoundException;
import de.hebkstudents.recipemanager.recipe.RecipeController;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientController {

    public static boolean addIngredient(Ingredient template)
    {
        if (!IngredientCategoryController.categoryExists(template.getIngredientCategory())) {
            Logger.log(LogType.ERROR, "Ingredient could not be added because IngredientCategory '" + template.getIngredientCategory().getCategoryID() + "' does not exist!");
            return false;
        }
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("INSERT INTO Ingredient (name, isVegan, isVegetarian, category) VALUES (?, ?, ?, ?)");
            ps.setString(1, template.getName());
            ps.setBoolean(2, template.isVegan());
            ps.setBoolean(3, template.isVegetarian());
            ps.setInt(4, template.getIngredientCategory().getCategoryID());

            if (ps.executeUpdate() > 0) {
                Logger.log(LogType.INFORMATION, "Ingredient '" + template.getName() + "' has been added successfully!");
                return true;
            }
            Logger.log(LogType.ERROR, "Ingredient '" + template.getName() + "' could not be added! Maybe it already exists.");
            return false;
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Ingredient could not be added because of an SQLException. Further information below.");
            Logger.logException(e);
            return false;
        }
    }

    public static boolean deleteIngredient(Ingredient ingredient) throws IngredientNotFoundException {
        if (ingredient != null) {
            return deleteIngredient(ingredient.getIngredientID());
        }
        return false;
    }

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


    public static Ingredient[] getIngredients()
    {
        return getIngredients(null);
    }


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
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IngredientNotFoundException e) {
            e.printStackTrace();
        } catch (IngredientUnitNotFoundException e) {
            e.printStackTrace();
        }
        Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
        ingredients.toArray(ingredientsArray);
        return  ingredientsArray;
    }





}
