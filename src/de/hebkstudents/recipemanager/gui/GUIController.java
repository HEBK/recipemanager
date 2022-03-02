package de.hebkstudents.recipemanager.gui;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.gui.frames.Menu;
import de.hebkstudents.recipemanager.gui.frames.ingredient.ShowIngredients;
import de.hebkstudents.recipemanager.gui.frames.recipe.RecipeFilter;
import de.hebkstudents.recipemanager.gui.frames.recipe.ShowRecipes;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIController implements ActionListener {

    private RecipeManager app;
    private boolean running = false;

    private AppFrame menu;
    private AppFrame showRecipes;
    private AppFrame showIngredients;
    private AppFrame recipeFilter;
    private AppFrame addRecipe;

    public GUIController(RecipeManager app)
    {
        init(app, false);
    }

    public GUIController(RecipeManager app, boolean run)
    {
        init(app, run);
    }

    /**
     * Initializes the GUIController
     * @param app App object
     * @param run Run GUIController after initialization?
     */
    private void init(RecipeManager app, boolean run)
    {
        if (app == null) {
            if (Logger.isLoaded()) {
                Logger.log(LogType.CRITICAL, "GUIController could not be initialized! (Invalid App instance)");
            }
            System.exit(-1);
        }

        if (run) run();
    }

    /**
     * Gets the App object for this GUIController
     * @return App object
     */
    public RecipeManager getApp()
    {
        return app;
    }

    /**
     * Runs the GUIController if it is not already running
     */
    public void run() {
        if (running) {
            Logger.log(LogType.WARNING, "GUIController is already running!");
            return;
        }
        running = true;
        Logger.log(LogType.INFORMATION, "Running GUI Controller...");
        menu = new Menu(this);
    }

    /**
     * Tries to focus a frame by its object
     * @param frame Object of the frame that should be focussed
     * @return true if frame was focussed
     */
    private boolean focusFrame(JFrame frame)
    {
        if (frame != null && frame.isDisplayable()) {

            // Set frame visible
            frame.setVisible(true);

            // Try focussing frame
            frame.requestFocus();
            return true;
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String componentID = ((Component) e.getSource()).getName();
        switch (componentID) {
            case "buttonMenuShowRecipes":
                if(!focusFrame(showRecipes)) {
                    showRecipes = new ShowRecipes(this);
                }
            case "buttonMenuShowIngredients":
                if(!focusFrame(showIngredients)) {
                    showIngredients = new ShowIngredients(this);
                }
            case "buttonMenuAddRecipe":
                if (!focusFrame(addRecipe)){
                    showRecipes = new ShowRecipes(this);
                }
            case "buttonRecipeFilter":
                if (!focusFrame(recipeFilter)) {
                    recipeFilter = new RecipeFilter(this);
                }
        }
    }
}
