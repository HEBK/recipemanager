package de.hebkstudents.recipemanager.recipe;

import de.hebkstudents.recipemanager.exception.InvalidRecipeException;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.interfaces.SimpleFilter;

/**
 * RecipeFilter
 * Class to generate RecipeFilter objects from to filter Recipes
 */
public class RecipeFilter implements SimpleFilter {

    /**
     * Query string to filter recipe names
     * (null -> All recipe names)
     */
    private final String query;

    /**
     * RecipeCategory object to filter only for Recipes of the same category
     * (null -> All categories)
     */
    private final RecipeCategory category;

    /**
     * Maximum time of recipe to filter for
     * (null -> All times)
     */
    private final Integer time;

    /**
     * Difficulty Integer - Same and below
     * (null -> All difficulties)
     */
    private final Integer difficulty;

    /**
     * Boolean to filter only for vegetarian recipes
     * (null -> No matter if vegetarian or not)
     */
    private final Boolean isVegetarian;

    /**
     * Boolean to filter only for vegan recipes
     * (null -> No matter if vegan or not)
     */
    private final Boolean isVegan;

    /**
     * Array of Ingredient Object that must be part of the recipe
     * (null -> Ingredients are ignored)
     */
    private final Ingredient[] ingredients;

    /**
     * SQL-Options String to directly filter using SQL-Statement
     */
    private String sqlOptions = null;

    /**
     * Constructs a new RecipeFilter
     * @param query Query string to filter recipe names (null -> All recipe names)
     * @param time Maximum time of recipe to filter for (null -> All times)
     * @param difficulty Difficulty Integer - Same and below (null -> All difficulties)
     * @param isVegetarian Boolean to filter only for vegetarian recipes (null -> No matter if vegetarian or not)
     * @param isVegan Boolean to filter only for vegan recipes (null -> No matter if vegan or not)
     * @param ingredients Array of Ingredient Object that must be part of the recipe (null -> Ingredients are ignored)
     * @param category RecipeCategory object to filter only for Recipes of the same category (null -> All categories)
     */
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

    /**
     * Constructs a new RecipeFilter
     * @param sqlOptions SQL-Options String to directly filter using SQL-Statement (e.g. "WHERE ...")
     */
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

    /**
     * Checks whether a recipe matches the filter or not
     * @param recipe Valid Recipe-Object
     * @return true if filter matches
     * @throws InvalidRecipeException If Recipe-Object is invalid
     */
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

    /**
     * Gets the filter options as SQL "WHERE ..." String
     * @return SQL Conditional String
     */
    @Override
    public String getSQLOptions() {

        return sqlOptions;
    }

    @Override
    public String getSQLQuery() {
        return "SELECT * FROM Recipe " + getSQLOptions();
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
