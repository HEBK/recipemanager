package de.hebkstudents.recipemanager;

import com.bulenkov.darcula.DarculaLookAndFeelInfo;
import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.exception.InvalidMethodParameterException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frames.ingredient.EditIngredient;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import de.hebkstudents.recipemanager.storage.DefaultConfig;
import de.hebkstudents.recipemanager.storage.StorageBackend;
import de.hebkstudents.recipemanager.utils.UpdateChecker;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.cr4zyfl1x.logger.exception.InvalidLoggerException;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;

public class RecipeManager {

    private static RecipeManager manager;

    /**
     * Main method
     * @param args Startup arguments
     * @throws SQLException if driver registration fails
     */
    public static void main(String[] args) throws SQLException, InvalidLoggerException {

        // Storage backend
        initializeStorageBackend();

        // Initialize logger
        initLogger();

        // Initialize configurations
        initializeConfiguration();

        // Load LookAndFeel
        loadLookAndFeel(DEFAULT_CONFIG.read("designClass") == null ? "com.bulenkov.darcula.DarculaLaf" : DEFAULT_CONFIG.read("designClass"));

        // Register SQL Drivers
        registerDrivers(new Driver[]{
                new org.sqlite.JDBC(),
                new com.mysql.cj.jdbc.Driver()
        });

        // Create instance of App
        manager = new RecipeManager();

        // Initialize database connection
        initializeDatabase();

        // Check for updates
        checkForUpdates();

        // Create & run GUI instance
        manager.setController(new GUIController(manager));
        manager.getController().run();

        // Log end of main
        Logger.log(LogType.SYSTEM, "App " + APPNAME + " successfully loaded!");
    }

    /**
     * Initialize FileSystem Storage Backend
     */
    private static void initializeStorageBackend() throws InvalidLoggerException {
        // Temporary logger
        Logger.load(new Logger("RM_TEMP", new Date()));
        Logger.log(LogType.SYSTEM, "Initializing filesystem storage backend ...");
        STORAGE_BACKEND = new StorageBackend(STORAGE_PATH, new String[]{"config", "cache", "data", "logs", "images/recipes"});
        DB_STRUCTURE_INITIALIZED = STORAGE_BACKEND.directoriesExist();
        if (!DB_STRUCTURE_INITIALIZED) {
            Logger.log(LogType.SYSTEM, "Creating necessary directories in '" + STORAGE_BACKEND.getRootDirectory() + "' ...");
            for (File dir: STORAGE_BACKEND.getSubDirectories()) {
                Logger.log(LogType.INFORMATION, "-> " + dir);
            }
            STORAGE_BACKEND.createDirectories();
        }
    }

    /**
     * Initializes the configuration files
     */
    private static void initializeConfiguration() {
        try {
            DEFAULT_CONFIG = new DefaultConfig(new File(STORAGE_PATH + "/config/general.properties"), "General configuration file for App " + APPNAME);

            if (!DEFAULT_CONFIG.exists()) {
                if (DEFAULT_CONFIG.createConfigFile()) {
                    boolean updateCheck = JOptionPane.showConfirmDialog(null, "Do you want to check for newer versions of " + APPNAME + " at startup?", APPNAME + " | " + "Update Checker", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                    DEFAULT_CONFIG.write("checkForUpdates", String.valueOf(updateCheck));
                    DEFAULT_CONFIG.write("designClass", "com.bulenkov.darcula.DarculaLaf");
                    DEFAULT_CONFIG.write("animatedMenuLogo", "false");
                    return;
                }
            } else {
                return;
            }
        } catch (InvalidMethodParameterException e) {
            Logger.log(LogType.CRITICAL, "Unable to initialize general configuration. Quitting ...");
            Logger.logException(e);
        }
        JOptionPane.showMessageDialog(null, "A critical error occurred!\nMore information in the logfile.");
        shutdownApp(500);
    }

    /**
     * Checks in a new Thread for updates to not influence the performance
     */
    private static void checkForUpdates()
    {
        if (Boolean.parseBoolean(DEFAULT_CONFIG.read("checkForUpdates"))) {
            new Thread(() -> {
                UpdateChecker.logUpdateCheck();
                UpdateChecker.showInformationPane(false);
            }).start();
        }
    }

    /**
     * Initialize Database
     */
    private static void initializeDatabase()
    {
        DatabaseController.initConnection();
        if (!DB_STRUCTURE_INITIALIZED) {
            DatabaseController.executeStatementsFromFile(new File(DB_STRUCTURE_TEMPLATE), false, false);
            DatabaseController.initConnection();
            DatabaseController.executeStatementsFromFile(new File(DB_DATA_TEMPLATE), false, false);
        }
    }

    /**
     * Initialize Logger
     */
    private static void initLogger() {
        Logger logger = new Logger("RecipeManager", new Date(), STORAGE_PATH + "/logs");
        logger.load();
        Logger.log(LogType.SYSTEM, "Logger initialization completed!");
        Logger.log(LogType.INFORMATION, APPNAME + " - Version: " + VERSION);
    }

    /**
     * Load LookAndFeel
     * @param lookAndFeelClass LookAndFeel ClassName
     */
    public static void loadLookAndFeel(String lookAndFeelClass) {
        try {
            UIManager.setLookAndFeel(lookAndFeelClass);
            Logger.log(LogType.SYSTEM, "LookAndFeel " + UIManager.getLookAndFeel().getName() + " loaded successfully!");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            Logger.log(LogType.CRITICAL, "Unable to load LookAndFeel!");
            Logger.logException(e);
            JOptionPane.showMessageDialog(null, "We're unable to load the LookAndFeel for this Application.\n\nPlease review log file for more information!", "Critical application error!", JOptionPane.ERROR_MESSAGE);
            System.exit(500);
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

    /**
     * Sends shutdown request with specific exit code
     * @param status Exit code
     */
    public static void shutdownApp(int status)
    {
        if (status == -1) {
            Logger.log(LogType.CRITICAL, "An critical error occoured. Exiting process immediately ...");
            System.exit(status);
        }
        Logger.log(LogType.SYSTEM, "Received app shutdown request ... (Status code: " + status + ")");
        DatabaseController.closeConnection();

        Logger.log(LogType.SYSTEM, "Finishing process with exit code " + status + " ...");
        System.exit(status);
    }

    public static RecipeManager getManager() {
        return manager;
    }

    /*
     * ---------------------------------
     */

    private GUIController controller;
    public void setController(GUIController controller) {
        this.controller = controller;
    }
    public GUIController getController() {
        return controller;
    }
}
