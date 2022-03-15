package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientCategoryNotFoundException;
import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.exception.IngredientUnitNotFoundException;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientUnitController {

    public static IngredientUnit[] getAllUnits()
    {
        ArrayList<IngredientUnit> units = new ArrayList<>();
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM IngredientUnits");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                units.add(new IngredientUnit(rs.getInt(1), rs.getString(3), rs.getString(2)));
            }
        } catch (SQLException e) {
            Logger.logException(e);
        }
        IngredientUnit[] unitArray = new IngredientUnit[units.size()];
        units.toArray(unitArray);
        return unitArray;
    }

    public static boolean unitExists(int unitID)
    {
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM IngredientUnits WHERE unitID = ?");
            ps.setInt(1, unitID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.logException(e);
            return false;
        }
    }

    public static IngredientUnit getUnit(int unitID) throws IngredientUnitNotFoundException {
        if (!unitExists(unitID)) {
            throw new IngredientUnitNotFoundException("An ingredientUnit with ID '" + unitID + "' does not exist!");
        }
        try {
            PreparedStatement ps = DatabaseController.getConnection().prepareStatement("SELECT * FROM IngredientUnits WHERE unitID = ?");
            ps.setInt(1, unitID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new IngredientUnit(rs.getInt(1), rs.getString(3), rs.getString(2));
            }
        } catch (SQLException e) {
            Logger.logException(e);
        }
        return null;
    }
}
