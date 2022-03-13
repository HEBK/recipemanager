package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.interfaces.SimpleFilter;

public class IngredientCategoryFilter implements SimpleFilter {

    private final String query;
    private final Integer categoryID;

    public IngredientCategoryFilter(String query, Integer categoryID)
    {
        this.query = query;
        this.categoryID = categoryID;
    }

    @Override
    public String getSQLOptions()
    {
        if (query == null && categoryID == null) return null;

        String[] stmts = {
                "name LIKE '%"+query+"%'",
                "categoryID = " + categoryID
        };

        StringBuilder options = new StringBuilder().append("WHERE ");
        boolean optionAdded = false;
        if (query != null && !query.isEmpty()) {
            options.append(stmts[0]);
            optionAdded = true;
        }
        if (categoryID != null) {
            if (optionAdded) options.append(" AND ");
            options.append(stmts[1]);
        }
        return options.toString();
    }

    @Override
    public String getSQLQuery() {
        return "SELECT * FROM IngredientCategory " + getSQLOptions();
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public String getQuery() {
        return query;
    }
}
