package de.hebkstudents.recipemanager;

import com.bulenkov.darcula.DarculaLaf;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.storage.StaticProperties;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static de.hebkstudents.recipemanager.storage.StaticProperties.APPNAME;
import static de.hebkstudents.recipemanager.storage.StaticProperties.STORAGE_PATH;

public class RecipeManager {

    public static void main(String[] args) throws SQLException {

        // Initialize logger
        initLogger();

        // Load LookAndFeel
        loadLookAndFeel(new DarculaLaf());

        // Register SQL Drivers
        registerDrivers(new Driver[]{
                new org.sqlite.JDBC(),
                new com.mysql.cj.jdbc.Driver()
        });

        // Create instance of App
        RecipeManager manager = new RecipeManager();

        // Create & run GUI instance
        manager.setController(new GUIController(manager));
        manager.getController().run();

        // Log end of main
        Logger.log(LogType.SYSTEM, "App " + APPNAME + " successfully loaded!");
    }

    /**
     * Initialize Logger
     */
    private static void initLogger() {
        Logger logger = new Logger("RecipeManager", new Date(), STORAGE_PATH + "/logs");
        logger.load();
        Logger.log(LogType.SYSTEM, "Logger initialization completed!");
        Logger.log(LogType.INFORMATION, StaticProperties.APPNAME + " - Version: " + StaticProperties.VERSION);
    }

    /**
     * Load LookAndFeel
     * @param l LookAndFeel Object
     */
    private static void loadLookAndFeel(LookAndFeel l) {
        try {
            UIManager.setLookAndFeel(l);
            Logger.log(LogType.SYSTEM, "LookAndFeel " + l.getName() + " loaded successfully!");
        } catch (UnsupportedLookAndFeelException e) {
            Logger.log(LogType.CRITICAL, "Unable to load LookAndFeel!");
            JOptionPane.showMessageDialog(null, "We're unable to load the LookAndFeel for this Application.", "Critical application error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    /**
     * Register drivers
     * @param drivers Drivers to register
     */
    private static void registerDrivers(Driver[] drivers)
    {
        if (Logger.isLoaded()) {
            Logger.log(LogType.SYSTEM, "Loading " + drivers.length + " drivers ...");
        }
        try {
            for (Driver d: drivers) {
                DriverManager.registerDriver(d);
            }
            Logger.log(LogType.SYSTEM, "All drivers were loaded successfully!");
        } catch (SQLException e) {
            if (Logger.isLoaded()) {
                Logger.log(LogType.ERROR, "One or more drivers could not be loaded!\n" + e.getMessage());
            }
        }
    }

    private GUIController controller;
    public void setController(GUIController controller) {
        this.controller = controller;
    }
    public GUIController getController() {
        return controller;
    }
}
