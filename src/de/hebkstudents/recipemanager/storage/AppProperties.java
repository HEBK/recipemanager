package de.hebkstudents.recipemanager.storage;

import de.hebkstudents.recipemanager.design.LaF;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class for general, static & dynamic, properties which are accessed during runtime and can not be changed by user
 */
public class AppProperties {

    /**
     * Appname
     */
    public static final String APPNAME = "RecipeManager";

    /**
     * Frame Icon
     */
    public static final ImageIcon FRAME_ICON = new ImageIcon("resources/images/logo/logo_64x.png");

    /**
     * Default Frame Dimension
     */
    public static final Dimension DEFAULT_DIMENSION = new Dimension(600, 400);

    /**
     * Frame header image icon
     */
    public static final ImageIcon HEADER_IMAGEICON = new ImageIcon("resources/images/logo/logo_128x.png");

    /**
     * Storagepath for configs, data & logs
     */
    public static final String STORAGE_PATH = System.getProperty("user.home") + "/RecipeManager";

    /**
     * App version string
     */
    public static final String VERSION = "1.0.0-rc2";

    /**
     * Latest Version String
     * Server URL
     */
    public static final String VERSION_STRING_SERVER_URL = "https://cdn.kleine-vorholt.eu/software/hebk/recipemanager/dl/version.php?type=raw";

    /**
     * Server-URL to the latest installer
     */
    public static final String LATEST_VERSION_INSTALLER_URL = "https://cdn.sarpex.eu/software/hebk/recipemanager/dl/download.php?type=exe";

    /**
     * GitHub Repo URL
     */
    public static final String GITHUB_REPO_URL = "https://github.com/HEBK/recipemanager";

    /**
     * Path to the main SQLite database
     */
    protected static final String MAIN_DATABASE_PATH = STORAGE_PATH + "/data/main.db";

    /**
     * Path to the database structure SQL File
     */
    public static final String DB_STRUCTURE_TEMPLATE = "resources/db/db_structure.sql";

    /**
     * Path to the database structure SQL File
     */
    public static final String DB_DATA_TEMPLATE = "resources/db/db_data.sql";

    /**
     * Bidirectional HashMap with all compatible LookAndFeels --> LIB: Apache Commons Collections
     */
    public static final ArrayList<LaF> LOOK_AND_FEEL_TPS = new ArrayList<>(Arrays.asList(
            new LaF("com.bulenkov.darcula.DarculaLaf", "Darcula"),
            new LaF("javax.swing.plaf.nimbus.NimbusLookAndFeel", "Nimbus"),
            new LaF("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", "Windows Classic"),
            new LaF(UIManager.getSystemLookAndFeelClassName(), "System default")
    ));

    /**
     * Boolean which defines if the database structure was already initialized or not
     */
    public static boolean DB_STRUCTURE_INITIALIZED;

    /**
     * Default Config Object for all general configuration values
     */
    public static DefaultConfig DEFAULT_CONFIG;

    /**
     * Defines whether the StorageBackend and SQL is initialized or not
     */
    public static StorageBackend STORAGE_BACKEND;
}
