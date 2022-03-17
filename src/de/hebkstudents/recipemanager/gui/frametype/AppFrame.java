package de.hebkstudents.recipemanager.gui.frametype;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.storage.AppProperties;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.APPNAME;
import static de.hebkstudents.recipemanager.storage.AppProperties.FRAME_ICON;

public abstract class AppFrame extends JFrame {

    /**
     * GUIController for this Frame
     */
    protected GUIController controller = null;

    /**
     * Initializes the frame as JFrame
     * @param gc GUIController for this Frame
     * @param title Title for this frame
     * @param dim Dimension for the frame (as Dimension object)
     * @param visible Visibility of the frame
     */
    public AppFrame(GUIController gc, String title, Dimension dim, boolean visible) {

        // Set Frame title
        super(title);

        // Set Dimension of frame
        this.setSize(dim);

        // Frame icon
        this.setIconImage(FRAME_ICON.getImage());

        // Set Visibility
        this.setVisible(visible);

        // Set GUI Controller
        this.setController(gc);

        // Log Frame init
        frameInitLog();

    }

    /**
     * Initializes the frame as JFrame
     * @param gc GUIController for this Frame
     * @param title Title for this frame
     * @param dim Dimension for the frame (as width(0) * height(1) array)
     * @param visible Visibility of the frame
     */
    public AppFrame(GUIController gc, String title, int[] dim, boolean visible) {

        // Set Frame title
        super(title);

        // Set Dimension of frame
        if (dim.length == 2) this.setSize(dim[0], dim[1]);

        // Frame icon
        this.setIconImage(FRAME_ICON.getImage());

        // Set Visibility
        this.setVisible(visible);

        // Set GUI Controller
        this.setController(gc);

        // Log Frame init
        frameInitLog();

    }

    /**
     * Initializes the frame as JFrame
     * @param gc GUIController for this Frame
     * @param title Title for this frame
     * @param visible Visibility of the frame
     */
    public AppFrame(GUIController gc, String title, boolean visible) {

        // Set Frame title
        super(title);

        // Frame icon
        this.setIconImage(FRAME_ICON.getImage());

        // Set Visibility
        this.setVisible(visible);

        // Set GUI Controller
        this.setController(gc);

        // Log Frame init
        frameInitLog();
    }

    /**
     * Sets a new GUIController for this frame
     * @param gc GUIController object
     */
    protected void setController(GUIController gc)
    {
        this.controller = gc;
    }

    /**
     * Gets the GUIController for this frame
     * @return GUIController object
     */
    protected GUIController getController()
    {
        return this.controller;
    }

    /**
     * Used to generate a custom frame title including the appname
     * @param title Title of the frame
     * @return Title including the appname
     */
    protected static String buildFrameTitle(String title)
    {
        return APPNAME + " | " + title;
    }

    /**
     * Logs the initialization of the logger using Cr4zyFl1x's Logger library
     */
    private void frameInitLog()
    {
        if (Logger.isLoaded()) Logger.log(LogType.SYSTEM, "Initialized AppFrame '" + getName() + "[" + hashCode() + "]' as '" + getTitle() + "'");
    }

    /**
     * Custom implemented initialization method for the frame
     */
    protected abstract void init();

}
