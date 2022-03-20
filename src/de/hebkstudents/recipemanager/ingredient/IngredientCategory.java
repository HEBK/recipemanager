package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientCategoryNotFoundException;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for IngredientCategory objects
 */
public class IngredientCategory {

    /**
     * Name of the category
     */
    private String name;

    /**
     * ID of the category
     */
    private int categoryID;

    /**
     * Constructor to construct an existing category from its ID
     * @param categoryID ID of the category
     * @throws IngredientCategoryNotFoundException If the category does not exist
     */
    public IngredientCategory(int categoryID) throws IngredientCategoryNotFoundException {
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM IngredientCategory WHERE categoryID = ?");
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.categoryID = categoryID;
                this.name = rs.getString(2);

            } else {
                throw new IngredientCategoryNotFoundException("The requested IngredientCategory with ID '" + categoryID + "' can not be found in database!");
            }
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "Cannot get IngredientCategory with ID '" + categoryID + "' from database!");
            Logger.logException(e);
        }
    }

    /**
     * Constructs a new Category by name and ID
     * @param categoryID ID of category
     * @param name Name of category
     */
    public IngredientCategory(int categoryID, String name)
    {
        this.categoryID = categoryID;
        this.name = name;
    }

    /**
     * Gets the categoryID
     * @return categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * Gets the name of the category
     * @return name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the category
     * @param name name of the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Overwritten method
     * @return Category name
     */
    @Override
    public String toString() {
        return getName();
    }
}
