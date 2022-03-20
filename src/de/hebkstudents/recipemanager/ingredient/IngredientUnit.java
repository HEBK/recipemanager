package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientUnitNotFoundException;

/**
 * Class to generate objects of units for ingredients
 */
public class IngredientUnit {

    /**
     * ID of unit
     */
    private Integer unitID;

    /**
     * Unit String
     * (e.g. 'g' or 'cl')
     */
    private String unit;

    /**
     * Full name of the unit as String
     */
    private String name;

    /**
     * Constructs a new IngredientUnit
     * @param name Full name of the unit as String
     * @param unit Unit String (e.g. 'g' or 'cl')
     */
    public IngredientUnit(String name, String unit)
    {
        this.name   = name;
        this.unit   = unit;
    }

    /**
     * Constructs a new IngredientUnit
     * @param unitID ID of the IngredientUnit
     * @param name Full name of the unit as String
     * @param unit Unit String (e.g. 'g' or 'cl')
     */
    public IngredientUnit(int unitID, String name, String unit)
    {
        this.unitID = unitID;
        this.name   = name;
        this.unit   = unit;
    }

    /**
     * Constructs an IngredientUnit object from an existing IngredientUnit in the database by its ID
     * @param unitID ID of the IngredientUnit
     * @throws IngredientUnitNotFoundException If the IngredientUnit does not exist
     */
    public IngredientUnit(int unitID) throws IngredientUnitNotFoundException {
        if (!IngredientUnitController.unitExists(unitID)) {
            throw new IngredientUnitNotFoundException("An ingredientUnit with ID '" + unitID + "' does not exist!");
        }

        IngredientUnit u    = IngredientUnitController.getUnit(unitID);
        assert u != null;
        this.unitID         = u.getUnitID();
        this.name           = u.getName();
        this.unit           = u.getUnit();
    }

    /**
     * Gets the name of the unit
     * @return Name of the unit
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the unit
     * @param name Name for the Unit
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the ID of the Unit
     * @return ID of the Unit
     */
    public Integer getUnitID() {
        return unitID;
    }

    /**
     * Gets the unit
     * @return unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @return Name of the unit
     */
    @Override
    public String toString() {
        return getName();
    }
}
