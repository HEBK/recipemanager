package de.hebkstudents.recipemanager.storage;

import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {
    private Connection connection;

    public static void initConnection(){
        try {
            DriverManager.getConnection("jdbc:sqlite:resources/data.db");
        } catch (SQLException e) {
            Logger.log(LogType.CRITICAL, "Database connection could not be initialized!");
        }
    }
}
