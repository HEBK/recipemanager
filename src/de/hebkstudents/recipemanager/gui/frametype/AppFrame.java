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

    protected void setController(GUIController gc)
    {
        this.controller = gc;
    }

    protected GUIController getController()
    {
        return this.controller;
    }

    protected static String buildFrameTitle(String title)
    {
        return APPNAME + " | " + title;
    }

    private void frameInitLog()
    {
        if (Logger.isLoaded()) Logger.log(LogType.SYSTEM, "Initialized AppFrame '" + getName() + "[" + hashCode() + "]' as '" + getTitle() + "'");
    }


    protected abstract void init();

}
