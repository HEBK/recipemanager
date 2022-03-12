package de.hebkstudents.recipemanager.storage;

import de.hebkstudents.recipemanager.RecipeManager;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import org.apache.ibatis.jdbc.ScriptRunner;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static de.hebkstudents.recipemanager.storage.AppProperties.MAIN_DATABASE_PATH;
import static de.hebkstudents.recipemanager.storage.AppProperties.STORAGE_PATH;

public class DatabaseController {

    /**
     * Database connection object
     */
    private static Connection connection;

    /**
     * Initializes the Connection
     */
    public static void initConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + MAIN_DATABASE_PATH);
        } catch (SQLException e) {
            Logger.log(LogType.CRITICAL, "Database connection could not be initialized!");
            Logger.logException(e);
            JOptionPane.showMessageDialog(null, "A critical error occoured:\n\n" + ((e.getMessage().isEmpty() ? "Exception message is empty and can not be loaded!" : e.getMessage())) + "\n\nA log file for this incident is available at:\n" + STORAGE_PATH + "/logs", "A critical error occoured", JOptionPane.ERROR_MESSAGE);
            RecipeManager.shutdownApp(-1);
        }
    }

    /**
     * Checks if the database connection is initialized
     * @return true if database is initialized
     */
    public static boolean isInitialized()
    {
        return connection != null;
    }

    /**
     * Closes the database connection if initialized
     */
    public static void closeConnection() {
        if (!isInitialized()) return;

        Logger.log(LogType.SYSTEM, "Closing SQLite database connection ...");
        if (connection == null) {
            Logger.log(LogType.WARNING, "Connection could not be closed because connection has not been initialized before!");
            return;
        }
        try {
            connection.close();
            connection = null;
            Logger.log(LogType.SYSTEM, "SQLite database connection has been closed successfully!");
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "An error has been occoured while trying to close SQLite database connection! (SQLException)");
            Logger.logException(e);
        }
    }

    /**
     * Creates the database structure from a template file
     */
    public static void executeStatementsFromFile(File file, boolean escapeProcessing, boolean stopOnError)
    {
        try {
            Logger.log(LogType.INFORMATION, "Executing statements from File '" + file + "' ...");
            ScriptRunner runner = new ScriptRunner(getConnection());
            runner.setEscapeProcessing(escapeProcessing);
            runner.setStopOnError(stopOnError);
            runner.setDelimiter(";");
            runner.runScript(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException | SQLException exception) {
            Logger.log(LogType.ERROR, "Error while executing statements.");
            Logger.logException(exception);
        }
    }

    /**
     * Gets the current Connection object
     * @return Connection object
     */
    public static Connection getConnection() throws SQLException {
        if (!isInitialized()) throw new SQLException("Cannot get database connection! Connection has not been initialized!");
        return connection;
    }
}
