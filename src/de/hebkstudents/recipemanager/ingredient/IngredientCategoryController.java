package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientCategoryNotFoundException;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientCategoryController {


    public static IngredientCategory[] getIngredientCategories()
    {
        return getIngredientCategories(null);
    }

    public static IngredientCategory[] getIngredientCategories(IngredientCategoryFilter filter)
    {
        String options = filter != null ? " " + (filter.getSQLOptions() != null ? filter.getSQLOptions() : "") : "";

        ArrayList<IngredientCategory> ingredientCategories = new ArrayList<>();
        try {
            String stmt = "SELECT * FROM IngredientCategory" + options;
            PreparedStatement statement = DatabaseController.getConnection().prepareStatement(stmt);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ingredientCategories.add(new IngredientCategory(rs.getInt(1)));
            }
        } catch (SQLException | IngredientCategoryNotFoundException e) {
            Logger.logException(e);
        }
        IngredientCategory[] ingredientCategoriesArray = new IngredientCategory[ingredientCategories.size()];
        ingredientCategories.toArray(ingredientCategoriesArray);
        return ingredientCategoriesArray;
    }

    public static boolean categoryExists(IngredientCategory category)
    {
        return category != null && categoryExists(category.getCategoryID());
    }

    public static boolean categoryExists(int categoryID)
    {
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM IngredientCategory WHERE categoryID = ?");
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.logException(e);
            return false;
        }
    }



}
