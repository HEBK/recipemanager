package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientCategoryNotFoundException;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientController {
    //Attribute

    //Konstruktor

    //Methoden
    public static boolean addIngredient(IngredientTemplate template)
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

    public static boolean deleteIngredient(Ingredient ingredient)
    {
        if (ingredient != null) {
            return deleteIngredient(ingredient.getIngredientID());
        }
        return false;
    }

    public static boolean deleteIngredient(int ingredientID) {

        if (!ingredientExists(ingredientID)) {
            Logger.log(LogType.ERROR, "An ingredient with ID '" + ingredientID + "' does not exist and can therefore not get deleted!");
            return false;
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

    public static Ingredient getIngredient(int ingredientID){
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
}
