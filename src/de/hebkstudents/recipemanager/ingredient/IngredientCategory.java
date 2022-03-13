package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientCategoryNotFoundException;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientCategory {
    //Attribute
    private String name;
    private int categoryID;

    //Konstruktor
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
    public IngredientCategory(int categoryID, String name)
    {
        this.categoryID = categoryID;
        this.name = name;
    }

    //Methoden

    //get-/setter
    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
