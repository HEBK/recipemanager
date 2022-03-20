package de.hebkstudents.recipemanager.interfaces;

/**
 * Interface for simple SQL row filters
 */
public interface SimpleFilter {

    /**
     * Gets the filter options as SQL "WHERE ..." String
     * @return SQL Conditional String
     */
    public String getSQLOptions();

    /**
     * Gets the whole SQL-Statement to get the filtered rows
     * @return SQL-Query-Statement to get the filtered rows
     */
    public String getSQLQuery();

}
