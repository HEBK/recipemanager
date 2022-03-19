package de.hebkstudents.recipemanager;

import com.bulenkov.darcula.DarculaLaf;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.storage.DatabaseController;
import de.hebkstudents.recipemanager.storage.StorageBackend;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;
import eu.cr4zyfl1x.logger.exception.InvalidLoggerException;

import javax.swing.*;
import java.io.File;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;

public class RecipeManager {

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

        // Load LookAndFeel
        loadLookAndFeel(new DarculaLaf());

        // Register SQL Drivers
        registerDrivers(new Driver[]{
                new org.sqlite.JDBC(),
                new com.mysql.cj.jdbc.Driver()
        });

        // Create instance of App
        RecipeManager manager = new RecipeManager();

        // Initialize database connection
        initializeDatabase();

        // Create & run GUI instance
        manager.setController(new GUIController(manager));
        manager.getController().run();

        // Log end of main
        Logger.log(LogType.SYSTEM, "App " + APPNAME + " successfully loaded!");

        if ("Mehl".matches(".*"+"hl"+".*")) System.out.println("yas");
    }

    /**
     * Initialize FileSystem Storage Backend
     */
    private static void initializeStorageBackend() throws InvalidLoggerException {
        // Temporary logger
        Logger.load(new Logger("RM_TEMP", new Date()));
        Logger.log(LogType.SYSTEM, "Initializing filesystem storage backend ...");
        STORAGE_BACKEND = new StorageBackend(STORAGE_PATH, new String[]{"config", "data", "logs", "images/recipes"});
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
