package de.hebkstudents.recipemanager.storage;

import javax.swing.*;

public class StaticProperties {

    /**
     * Appname
     */
    public static final String APPNAME = "RecipeManager";

    /**
     * Frame Icon
     */
    public static final ImageIcon FRAME_ICON = new ImageIcon("ressources/images/logo/frame-logo.png");

    /**
     * Frame header image icon
     */
    public static final ImageIcon HEADER_IMAGEICON = new ImageIcon("ressources/images/logo/light/sizes/logo/125wh.png");

    /**
     * Storagepath for data & configs
     */
    public static final String STORAGE_PATH = System.getProperty("user.home") + "/RecipeManager";

    /**
     * App version string
     */
    public static final String VERSION = "0.0.1";

}
