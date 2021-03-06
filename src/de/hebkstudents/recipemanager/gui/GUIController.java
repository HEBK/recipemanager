package de.hebkstudents.recipemanager.gui;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.gui.frames.Menu;
import de.hebkstudents.recipemanager.gui.frames.ingredient.AddIngredient;
import de.hebkstudents.recipemanager.gui.frames.ingredient.EditIngredient;
import de.hebkstudents.recipemanager.gui.frames.ingredient.IngredientFilterFrame;
import de.hebkstudents.recipemanager.gui.frames.ingredient.ShowIngredients;
import de.hebkstudents.recipemanager.gui.frames.other.About;
import de.hebkstudents.recipemanager.gui.frames.other.DeveloperConsole;
import de.hebkstudents.recipemanager.gui.frames.other.Options;
import de.hebkstudents.recipemanager.gui.frames.recipe.*;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.ingredient.IngredientFilter;
import de.hebkstudents.recipemanager.recipe.Recipe;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class to handle frames
 */
public class GUIController implements ActionListener {

    /**
     * App-Object (RecipeManager) where the GUIController is referring to
     */
    private RecipeManager app;

    /**
     * Defines if the controller is running
     */
    private boolean running = false;

    /**
     * Object for Menu Frame
     */
    private AppFrame menu;

    /**
     * Object for Recipe list frame
     */
    private AppFrame showRecipes;

    /**
     * Object for frame that shows a specific recipe
     */
    private ShowRecipe showRecipe;

    /**
     * Object for Ingredient list frame
     */
    private ShowIngredients showIngredients;

    /**
     * Object for recipe filter frame
     */
    private AppFrame recipeFilterFrame;

    /**
     * Object for frame to add a new recipe
     */
    private AddRecipe addRecipe;

    /**
     * Object for DeveloperConsole frame
     */
    private AppFrame developerConsole;

    /**
     * Object for ingredient filter frame
     */
    private AppFrame ingredientFilterFrame;

    /**
     * Object for frame to add a new ingredient
     */
    private AppFrame addIngredientFrame;

    /**
     * Object for frame to add an ingredient to a recipe
     */
    private AppFrame addRecipeIngredientFrame;

    /**
     * Object for options frame
     */
    private AppFrame options;

    /**
     * Object for frame to edit an ingredient
     */
    private AppFrame editIngredient;

    /**
     * Object for frame to show details about the software and its licenses
     */
    private AppFrame aboutFrame;

    /**
     * GUIController constructor
     * @param app App(RecipeManager) object
     */
    public GUIController(RecipeManager app)
    {
        init(app, false);
    }

    /**
     * GUIController constructor
     * @param app App(RecipeManager) object
     * @param run Run the GUIController
     */
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

        this.app = app;

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

        if (app == null) {
            Logger.log(LogType.ERROR, "Cannot run GUIController! App was not set!");
            return;
        }

        running = true;
        Logger.log(LogType.INFORMATION, "Running GUI Controller...");
        menu = new Menu(this);
    }

    /**
     * Stops/Reboots the GUI controller
     * @param restart Defines whether the GUI controller should bes stopped or restarted
     */
    public void stop(boolean restart)
    {
        Logger.log(LogType.SYSTEM, "Shutting down GUI Controller ...");

        if (menu != null) { menu.dispose(); }
        if (showRecipes != null) { showRecipes.dispose(); }
        if (showRecipe != null) { showRecipe.dispose(); }
        if (showIngredients != null) { showIngredients.dispose(); }
        if (recipeFilterFrame != null) { recipeFilterFrame.dispose(); }
        if (addRecipe != null) { addRecipe.dispose(); }
        if (developerConsole != null) { developerConsole.dispose(); }
        if (ingredientFilterFrame != null) { ingredientFilterFrame.dispose(); }
        if (addIngredientFrame != null) { addIngredientFrame.dispose(); }
        if (addRecipeIngredientFrame != null) { addRecipeIngredientFrame.dispose(); }
        if (options != null) { options.dispose(); }
        if (editIngredient != null) { editIngredient.dispose(); }
        if (aboutFrame != null) { aboutFrame.dispose(); }

        running = false;

        if (restart) {
            run();
        }
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

    /**
     * Opens the developer console if not already opened
     */
    public void openDeveloperConsole()
    {
        if (!focusFrame(developerConsole)) {
            developerConsole = new DeveloperConsole(this);
        }
    }

    /**
     * Opens the recipe list if not already opened
     */
    public void openFrameShowRecipes()
    {
        if (addRecipe != null) {
            addRecipe.dispose();
            addRecipe = null;
        }
        if(!focusFrame(showRecipes)) {
            showRecipes = new ShowRecipes(this);
        }
    }

    /**
     * Opens the ingredients list if not already opened
     */
    public void openFrameShowIngredients(IngredientFilter filter)
    {
        if (ingredientFilterFrame != null) {
            ingredientFilterFrame.dispose();
            ingredientFilterFrame = null;
        }
        if (editIngredient != null) {
            editIngredient.dispose();
            editIngredient = null;
        }
        if (addIngredientFrame != null) {
            addIngredientFrame.dispose();
            addIngredientFrame = null;
        }
        if(!focusFrame(showIngredients)) {
            showIngredients = new ShowIngredients(this, (filter != null) ? filter : new IngredientFilter(null, null, null, null, null));
        }
    }

    /**
     * Opens the frame to add new recipes if not already opened
     */
    public void openFrameAddRecipe()
    {
        if (!focusFrame(addRecipe)){
            addRecipe = new AddRecipe(this);
        }

    }

    /**
     * Open sthe frame to show a recipe
     * @param recipe Valid Recipe object
     */
    public void openFrameShowRecipe(Recipe recipe){
        if (!focusFrame(showRecipe)){
            showRecipe = new ShowRecipe(this, recipe);
        }
    }

    /**
     * Opens the recipe filter frame if not already opened
     */
    public void openFrameRecipeFilter()
    {
        if (!focusFrame(recipeFilterFrame)) {
            recipeFilterFrame = new RecipeFilterFrame(this);
        }
    }

    /**
     * Opens the ingredient filter if not already opened
     */
    private void openFrameIngredientFilter(IngredientFilter filter)
    {
        if (showIngredients != null) {
            showIngredients.dispose();
            showIngredients = null;
        }
        if (!focusFrame(ingredientFilterFrame)) {
            ingredientFilterFrame = new IngredientFilterFrame(this, filter);
        }
    }

    /**
     * Opens the frame to add new ingredients if not already opened
     */
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

    /**
     * Opens the frame to add an ingredient to a recipe
     */
    private void openFrameAddRecipeIngredient()
    {
        if (!focusFrame(addRecipeIngredientFrame)) {
            addRecipeIngredientFrame = new AddRecipeIngredient(this);
        }
    }

    /**
     * Closes the frame to add an ingredient to a recipe
     */
    public void closeFrameAddRecipeIngredient()
    {
        if (addRecipeIngredientFrame != null) {
            addRecipeIngredientFrame.dispose();
            addRecipeIngredientFrame = null;
        }
    }

    /**
     * Opens the frame to edit app options
     */
    private void openFrameOptions()
    {
        if (!focusFrame(options)) {
            options = new Options(this);
        }
    }

    /**
     * Adds a ingredient object to the recipe that is being created (FRAME!)
     * @param g Ingredient object
     * @return true if successfully added
     */
    public boolean addIngredientToRecipe(Ingredient g)
    {
        if (addRecipe != null) {
            return addRecipe.addIngredient(g);
        } else {
            new Thread(() -> {
                JOptionPane.showMessageDialog(null, "A critical error occoured!\n\nSee log for more information.", "Error", JOptionPane.ERROR_MESSAGE);
                RecipeManager.shutdownApp(500);
            });
            Logger.log(LogType.CRITICAL, "A critical error occoured while trying to add ingredient to recipe! Cannot add ingredient to recipe because no recipe is being created!");
        }
        return false;
    }

    /**
     * OPens the frame to edit an ingredient
     * @param ingredient Ingredient object to edit
     */
    public void openFrameEditIngredient(Ingredient ingredient)
    {
        if (showIngredients != null) {
            showIngredients.dispose();
            showIngredients = null;
        }
        if (!focusFrame(editIngredient)) {
            editIngredient = new EditIngredient(this, ingredient);
        }
    }

    /**
     * Opens the frame with details about the software and its licenses
     */
    public void openFrameAbout()
    {
        if (!focusFrame(aboutFrame)) {
            aboutFrame = new About(this);
        }
    }

    /**
     * ActionEvent Receiver that is may triggerred when a button or other components are referring to the controller to handle their events
     * @param e Triggered Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String componentID = ((Component) e.getSource()).getName();
        switch (componentID) {
            case "buttonMenuShowRecipes" -> openFrameShowRecipes();
            case "buttonMenuShowIngredients" -> openFrameShowIngredients(null);
            case "buttonMenuAddRecipe" -> openFrameAddRecipe();
            case "buttonRecipeFilter" -> openFrameRecipeFilter();
            case "buttonIngredientFilter" -> openFrameIngredientFilter(showIngredients != null && showIngredients.getFilter() != null ? showIngredients.getFilter() : null);
            case "buttonIngredientsAddIngredient" -> openFrameAddIngredient();
            case "buttonAddRecipeAddRecipeIngredient" -> openFrameAddRecipeIngredient();
            case "buttonMenuOptions" -> openFrameOptions();
            case "buttonMenuAbout" -> openFrameAbout();
            default -> JOptionPane.showMessageDialog(null, "Action not found!\n\nAction: " + componentID, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
