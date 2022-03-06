package de.hebkstudents.recipemanager.storage;

import de.hebkstudents.recipemanager.RecipeManager;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static de.hebkstudents.recipemanager.storage.StaticProperties.STORAGE_PATH;

public class DatabaseController {
    private static Connection connection;

    public static void initConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + STORAGE_PATH + "/data/mainRCMGRDB.db");
        } catch (SQLException e) {
            Logger.log(LogType.CRITICAL, "Database connection could not be initialized!");
            Logger.logException(e);
            JOptionPane.showMessageDialog(null, "A critical error occoured:\n\n" + ((e.getMessage().isEmpty() ? "Exception message is empty and can not be loaded!" : e.getMessage())) + "\n\nA log file for this incident is available at:\n" + STORAGE_PATH + "/logs", "A critical error occoured", JOptionPane.ERROR_MESSAGE);
            RecipeManager.shutdownApp(-1);
        }
    }


    public static void closeConnection() {
        Logger.log(LogType.SYSTEM, "Closing SQLite database connection ...");
        if (connection == null) {
            Logger.log(LogType.WARNING, "Connection could not be closed because connection has not been initialized before!");
            return;
        }
        try {
            connection.close();
            Logger.log(LogType.SYSTEM, "SQLite database connection has been closed successfully!");
        } catch (SQLException e) {
            Logger.log(LogType.ERROR, "An error has been occoured while trying to close SQLite database connection! (SQLException)");
            Logger.logException(e);
        }
    }
}
