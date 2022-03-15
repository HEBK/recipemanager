package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientUnitNotFoundException;

public class IngredientUnit {

    private Integer unitID;
    private String unit;
    private String name;

    public IngredientUnit(String name, String unit)
    {
        this.name   = name;
        this.unit   = unit;
    }
    public IngredientUnit(int unitID, String name, String unit)
    {
        this.unitID = unitID;
        this.name   = name;
        this.unit   = unit;
    }
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUnitID() {
        return unitID;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return getName();
    }
}
