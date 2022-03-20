package de.hebkstudents.recipemanager.recipe;

import de.hebkstudents.recipemanager.exception.RecipeCategoryNotFoundException;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class which handles the RecipeCategories
 */
public class RecipeCategoryController {

    /**
     * Checks whether a category exists or not
     * @param categoryID ID of the category
     * @return true if category exists
     */
    public static boolean categoryExists(int categoryID)
    {
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM RecipeCategory WHERE categoryID = ?");
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.logException(e);
            return false;
        }
    }

    /**
     * Gets all RecipeCategories as Objects in an RecipeCategory Array
     * @return RecipeCategory Array
     */
    public static RecipeCategory @NotNull [] getAllCategories()
    {
        ArrayList<RecipeCategory> categories = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM RecipeCategory");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(new RecipeCategory(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            Logger.logException(e);
        }
        RecipeCategory[] categoryArray = new RecipeCategory[categories.size()];
        categories.toArray(categoryArray);
        return categoryArray;
    }

    /**
     * Gets a RecipeCategory by its ID from the database
     * @param categoryID ID of the category
     * @return Object of RecipeCategory if category exists
     * @throws RecipeCategoryNotFoundException If Category does not exist
     */
    public static RecipeCategory getCategory(int categoryID) throws RecipeCategoryNotFoundException {
        if (!categoryExists(categoryID)) {
            throw new RecipeCategoryNotFoundException("A recipe category with ID '" + categoryID + "' does not exist!");
        }
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM RecipeCategory WHERE categoryID = ?");
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new RecipeCategory(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            Logger.logException(e);
        }
        return null;
    }
}
