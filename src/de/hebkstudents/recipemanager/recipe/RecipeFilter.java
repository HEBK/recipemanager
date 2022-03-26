package de.hebkstudents.recipemanager.recipe;

import de.hebkstudents.recipemanager.exception.InvalidRecipeException;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.interfaces.SimpleFilter;

/**
 * RecipeFilter
 * Not yet in use
 */
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

    public boolean matchesFilter(Recipe recipe) throws InvalidRecipeException {
        if (recipe == null || recipe.getName() == null || recipe.getCategory() == null || recipe.getIngredients() == null) {
            throw new InvalidRecipeException("The given Recipe object is invalid. One or more values are missing or the object is corrupted!");
        }

        if (getQuery() != null) {
            if (!recipe.getName().matches(".*" + getQuery() + ".*")) {
                return false;
            }
        }
        if (getCategory() != null) {
            if (!recipe.getCategory().getName().equals(getCategory().getName())) {
                return false;
            }
        }
        if (getTime() != null) {
            if (!(recipe.getTime() <= getTime())) {
                return false;
            }
        }
        if (getDifficulty() != null) {
            if (!(recipe.getDifficulty() <= getDifficulty())) {
                return false;
            }
        }
        if (isVegan() != null) {
            if (recipe.isVegan() != isVegan()) {
                return false;
            }
        }
        if (isVegetarian() != null) {
            if (recipe.isVegetarian() != isVegetarian()) {
                return false;
            }
        }
        if (getIngredients() != null) {
            if (!recipe.hasIngredients(getIngredients())) {
                return false;
            }
        }
        return true;
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

    public Boolean isVegan() {
        return isVegan;
    }

    public Boolean isVegetarian() {
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
