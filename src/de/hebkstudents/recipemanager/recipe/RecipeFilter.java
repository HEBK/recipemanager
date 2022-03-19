package de.hebkstudents.recipemanager.recipe;

import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.interfaces.SimpleFilter;

public class RecipeFilter implements SimpleFilter {

    private final String query;
    private final RecipeCategory category;
    private final Integer time;
    private final Integer difficulty;
    private final Boolean isVegetarian;
    private final Boolean isVegan;
    private final Ingredient[] ingredients;

    private String sqlOptions;

    public RecipeFilter(String query, Integer time, Integer difficulty, Boolean isVegetarian, Boolean isVegan, Ingredient[] ingredients, RecipeCategory category)
    {
        this.query          = query;
        this.category       = category;
        this.time           = time;
        this.difficulty     = difficulty;
        this.isVegetarian   = isVegetarian;
        this.isVegan        = isVegan;
        this.ingredients    = ingredients;
    }

    public RecipeFilter(String sqlOptions)
    {
        this.query          = null;
        this.category       = null;
        this.time           = null;
        this.difficulty     = null;
        this.isVegetarian   = null;
        this.isVegan        = null;
        this.ingredients    = null;
        this.sqlOptions = sqlOptions;
    }

    @Override
    public String getSQLOptions() {

        if (sqlOptions != null) return sqlOptions;
        if (query == null && category == null && time == null && difficulty == null && isVegetarian == null && isVegan == null && ingredients == null) return null;

        String categoryVal = (category != null) ? String.valueOf(category.getCategoryID()) : "";

        String[] stmts = {
                "r.name LIKE '%" + query + "%'",
                "r.category = " + categoryVal,
                "r.time <= " + time,
                "r.difficulty <= " + difficulty,

        };

        return null;
    }

    @Override
    public String getSQLQuery() {
        return null;
    }

    public Boolean getVegan() {
        return isVegan;
    }

    public Boolean getVegetarian() {
        return isVegetarian;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Integer getTime() {
        return time;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public String getQuery() {
        return query;
    }
}
