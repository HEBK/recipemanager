package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.interfaces.SimpleFilter;

/**
 * Class to generate IngredientCategoryFilter objects from to filter IngredientCategories using the IngredientCategoryController
 */
public class IngredientCategoryFilter implements SimpleFilter {

    /**
     * Query String to filter category names
     * (null -> All category names)
     */
    private final String query;

    /**
     * Integer to filter category id's
     * (null -> All category ID's)
     */
    private final Integer categoryID;


    /**
     * Constructs a new IngredientCategoryFilter
     * @param query Query String to filter category names (null -> All category names)
     * @param categoryID Integer to filter category id's (null -> All category ID's)
     */
    public IngredientCategoryFilter(String query, Integer categoryID)
    {
        this.query = query;
        this.categoryID = categoryID;
    }

    /**
     * Gets the filter options as SQL "WHERE ..." String
     * @return SQL Conditional String
     */
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

    /**
     * Gets the whole SQL-Statement to get the filtered Categories
     * @return SQL-Query-Statement to get the filtered categories
     */
    @Override
    public String getSQLQuery() {
        return "SELECT * FROM IngredientCategory " + getSQLOptions();
    }
}
