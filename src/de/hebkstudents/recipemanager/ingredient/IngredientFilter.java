package de.hebkstudents.recipemanager.ingredient;

import de.hebkstudents.recipemanager.interfaces.SimpleFilter;

/**
 * Class to generate IngredientFilter objects from to filter Ingredients using the IngredientController
 */
public class IngredientFilter implements SimpleFilter {

    /**
     * Query string to filter ingredient names
     * (null -> All Ingredient names)
     */
    private final String query;

    /**
     * Boolean to filter only for vegan ingredients
     * (null -> No matter if vegan or not)
     */
    private final Boolean isVegan;

    /**
     * Boolean to filter only for vegetarian ingredients
     * (null -> No matter if vegetarian or not)
     */
    private final Boolean isVegetarian;

    /**
     * IngredientCategory object to filter only for Ingredients of the same category
     * (null -> All categories)
     */
    private final IngredientCategory category;

    /**
     * IngredientID to filter for a specific ID
     * (null -> All ID's)
     */
    private final Integer ingredientID;

    /**
     * SQL-Options String to directly filter using SQL-Statement
     */
    private String sqlOptions = null;

    /**
     * Constructs a new IngredientFilter
     * @param query Query string to filter ingredient names (null -> All Ingredient names)
     * @param isVegan Boolean to filter only for vegan ingredients (null -> No matter if vegan or not)
     * @param isVegetarian Boolean to filter only for vegetarian ingredients (null -> No matter if vegetarian or not)
     * @param category IngredientCategory object to filter only for Ingredients of the same category (null -> All categories)
     * @param ingredientID IngredientID to filter for a specific ID (null -> All ID's)
     */
    public IngredientFilter(String query, Boolean isVegan, Boolean isVegetarian, IngredientCategory category, Integer ingredientID)
    {
        this.query = query;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
        this.category = category;
        this.ingredientID = ingredientID;
    }

    /**
     * Constructs a new IngredientFilter
     * @param sqlOptions SQL-Options String to directly filter using SQL-Statement (e.g. "WHERE ...")
     */
    public IngredientFilter(String sqlOptions)
    {
        this.query = null;
        this.isVegan = null;
        this.isVegetarian = null;
        this.category = null;
        this.ingredientID = null;
        this.sqlOptions = sqlOptions;
    }

    /**
     * Gets the filter options as SQL "WHERE ..." String
     * @return SQL Conditional String
     */
    @Override
    public String getSQLOptions()
    {
        if (sqlOptions != null) return sqlOptions;
        if (query == null && isVegetarian == null && isVegan == null && category == null && ingredientID == null) return null;

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

    /**
     * Gets the whole SQL-Statement to get the filtered ingredients
     * @return SQL-Query-Statement to get the filtered ingredients
     */
    @Override
    public String getSQLQuery() {
        return "SELECT * FROM Ingredient " + getSQLOptions();
    }

    /**
     * Gets the filters query as String
     * @return Query as String
     */
    public String getQuery() {
        return query;
    }

    /**
     * Checks if the filter is set to only vegan ingredients
     * @return true if filter is set
     */
    public Boolean isVegan() {
        return isVegan;
    }

    /**
     * Checks if the filter is set to only vegetarian ingredients
     * @return true if filter is set
     */
    public Boolean isVegetarian() {
        return isVegetarian;
    }

    /**
     * Gets the filters category object
     * @return IngredientCategory Object
     */
    public IngredientCategory getCategory() {
        return category;
    }
}
