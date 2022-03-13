package de.hebkstudents.recipemanager.gui;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.gui.frames.Menu;
import de.hebkstudents.recipemanager.gui.frames.ingredient.AddIngredient;
import de.hebkstudents.recipemanager.gui.frames.ingredient.IngredientFilterFrame;
import de.hebkstudents.recipemanager.gui.frames.ingredient.ShowIngredients;
import de.hebkstudents.recipemanager.gui.frames.other.DeveloperConsole;
import de.hebkstudents.recipemanager.gui.frames.recipe.AddRecipe;
import de.hebkstudents.recipemanager.gui.frames.recipe.RecipeFilterFrame;
import de.hebkstudents.recipemanager.gui.frames.recipe.ShowRecipes;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.IngredientFilter;
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
    private AppFrame recipeFilterFrame;
    private AppFrame addRecipe;
    private AppFrame developerConsole;
    private AppFrame ingredientFilterFrame;
    private AppFrame addIngredientFrame;

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

    public void openDeveloperConsole()
    {
        if (!focusFrame(developerConsole)) {
            developerConsole = new DeveloperConsole(this);
        }
    }

    public void openFrameShowRecipes()
    {
        if(!focusFrame(showRecipes)) {
            showRecipes = new ShowRecipes(this);
        }
    }

    public void openFrameShowIngredients(IngredientFilter filter)
    {
        if (ingredientFilterFrame != null) {
            ingredientFilterFrame.dispose();
            ingredientFilterFrame = null;
        }
        if (addIngredientFrame != null) {
            addIngredientFrame.dispose();
            addIngredientFrame = null;
        }
        if(!focusFrame(showIngredients)) {
            showIngredients = new ShowIngredients(this, (filter != null) ? filter : new IngredientFilter(null, null, null, null, null));
        }
    }

    public void openFrameAddRecipe()
    {
        if (!focusFrame(addRecipe)){
            addRecipe = new AddRecipe(this);
        }
    }

    public void openFrameRecipeFilter()
    {
        if (!focusFrame(recipeFilterFrame)) {
            recipeFilterFrame = new RecipeFilterFrame(this);
        }
    }

    private void openFrameIngredientFilter()
    {
        if (showIngredients != null) {
            showIngredients.dispose();
            showIngredients = null;
        }
        if (!focusFrame(ingredientFilterFrame)) {
            ingredientFilterFrame = new IngredientFilterFrame(this);
        }
    }

    private void openFrameAddIngredient()
    {
        if (showIngredients != null) {
            showIngredients.dispose();
            showIngredients = null;
        }
        if (!focusFrame(addIngredientFrame)) {
            addIngredientFrame = new AddIngredient(this);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String componentID = ((Component) e.getSource()).getName();
        switch (componentID) {
            case "buttonMenuShowRecipes" -> openFrameShowRecipes();
            case "buttonMenuShowIngredients" -> openFrameShowIngredients(null);
            case "buttonMenuAddRecipe" -> openFrameAddRecipe();
            case "buttonRecipeFilter" -> openFrameRecipeFilter();
            case "buttonIngredientFilter" -> openFrameIngredientFilter();
            case "buttonIngredientsAddIngredient" -> openFrameAddIngredient();
            default -> JOptionPane.showMessageDialog(null, "Action not found!\n\nAction: " + componentID, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
