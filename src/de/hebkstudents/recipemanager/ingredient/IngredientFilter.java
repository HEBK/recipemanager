package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.interfaces.SimpleFilter;

public class IngredientFilter implements SimpleFilter {

    private final String query;
    private final Boolean isVegan;
    private final Boolean isVegetarian;
    private final IngredientCategory category;
    private final Integer ingredientID;

    public IngredientFilter(String query, Boolean isVegan, Boolean isVegetarian, IngredientCategory category, Integer ingredientID)
    {
        this.query = query;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
        this.category = category;
        this.ingredientID = ingredientID;
    }

    @Override
    public String getSQLOptions()
    {
        if (query == null && isVegetarian == null && isVegan == null && category == null) return null;

        String categoryVal = (category != null) ? String.valueOf(category.getCategoryID()) : "";

        String[] stmts = {
                "name LIKE '%"+query+"%'",
                "isVegetarian = " + isVegetarian,
                "isVegan = " + isVegan,
                "category = " + categoryVal,
                "ingredientID = " + ingredientID
        };

        StringBuilder options = new StringBuilder().append("WHERE ");
        boolean optionAdded = false;
        if (query != null) {
            options.append(stmts[0]);
            optionAdded = true;
        }
        if (isVegetarian != null) {
            if (optionAdded) options.append(" AND ");
            options.append(stmts[1]);
            optionAdded = true;
        }
        if (isVegan != null) {
            if (optionAdded) options.append(" AND ");
            options.append(stmts[2]);
            optionAdded = true;
        }
        if (category != null) {
            if (optionAdded) options.append(" AND ");
            options.append(stmts[3]);
            optionAdded = true;
        }
        if (ingredientID != null) {
            if (optionAdded) options.append(" AND ");
            options.append(stmts[4]);
        }
        return options.toString();
    }

    @Override
    public String getSQLQuery() {
        return "SELECT * FROM Ingredient " + getSQLOptions();
    }

    public String getQuery() {
        return query;
    }

    public Integer getIngredientID() {
        return ingredientID;
    }

    public Boolean isVegan()
    {
        return isVegan;
    }

    public Boolean IsVegetarian()
    {
        return isVegetarian;
    }

    public IngredientCategory getCategory()
    {
        return category;
    }
}
