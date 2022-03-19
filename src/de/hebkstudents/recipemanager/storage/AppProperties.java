package de.hebkstudents.recipemanager.storage;

import de.hebkstudents.recipemanager.design.LaF;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
    public static final String VERSION = "0.1.0";

    /**
     * Path to the main SQLite database
     */
    protected static final String MAIN_DATABASE_PATH = STORAGE_PATH + "/data/mainRCMGRDB.db";

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
            new LaF("javax.swing.plaf.metal.MetalLookAndFeel", "Metal"),
            new LaF("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel", "Windows Classic"),
            new LaF(UIManager.getSystemLookAndFeelClassName(), "System default")
    ));

    public static boolean DB_STRUCTURE_INITIALIZED;

    public static DefaultConfig DEFAULT_CONFIG;

    /**
     * Defines whether the StorageBackend and SQL is initialized or not
     */
    public static StorageBackend STORAGE_BACKEND;
    public static void setStorageBackend(StorageBackend storageBackend) {
        STORAGE_BACKEND = storageBackend;
    }
    public static StorageBackend getStorageBackend() {
        return STORAGE_BACKEND;
    }
}
