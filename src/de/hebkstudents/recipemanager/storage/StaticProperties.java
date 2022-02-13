package de.hebkstudents.recipemanager.storage;

import javax.swing.*;
import java.awt.*;

public class StaticProperties {

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
     * Storagepath for data & configs
     */
    public static final String STORAGE_PATH = System.getProperty("user.home") + "/RecipeManager";

    /**
     * App version string
     */
    public static final String VERSION = "0.0.1";

}
