package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientUnitNotFoundException;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import eu.cr4zyfl1x.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class which handles the IngredientUnits
 */
public class IngredientUnitController {

    /**
     * Gets all IngredientUnits as Objects in an IngredientUnit Array
     * @return IngredientUnit Array
     */
    public static IngredientUnit @NotNull [] getAllUnits()
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

    /**
     * Checks whether a unit exists or not
     * @param unitID ID of the unit
     * @return true if unit exists
     */
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

    /**
     * Gets a Unit by its ID from the database
     * @param unitID ID of the Unit
     * @return IngredientUnit object if unit exists
     * @throws IngredientUnitNotFoundException If unit does not exist
     */
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
