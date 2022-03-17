package de.hebkstudents.recipemanager.gui.frames;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;

/**
 * ShowRecipes class
 * Class for the frame that shows the main menu of this application
 */
public class Menu extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * Label that contains the appname
     */
    private JLabel appnameLabel;

    /**
     * Button that opens the frame to add a recipe
     */
    private JButton addRecipeButton;

    /**
     * Button that opens the frame with a list of all recipes
     */
    private JButton showRecipesButton;

    /**
     * Button that opens the frame with a list of all ingredients
     */
    private JButton ingredientsButton;

    /**
     * Button that exits the program
     */
    private JButton closeButton;

    /**
     * Label that shows the current version number
     */
    private JLabel versionLabel;

    /**
     * Button that opens the developer console
     */
    private JButton developerConsoleButton;

    /**
     * Button that opens the folder which contains all custom application data
     */
    private JButton dataFolderButton;

    /**
     * Menu constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
    public Menu(GUIController controller)
    {
        super(controller, buildFrameTitle("Hauptmenü"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setDefaultCloseOperation(AppFrame.DO_NOTHING_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                RecipeManager.shutdownApp(0);
            }
        });

        setMinimumSize(new Dimension(750, 580));
        versionLabel.setText("v" + VERSION);
        appnameLabel.setText(APPNAME);

        closeButton.addActionListener(e -> {
            RecipeManager.shutdownApp(0);
        });

        developerConsoleButton.addActionListener(e -> getController().openDeveloperConsole());
        dataFolderButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(STORAGE_PATH));
                Logger.log(LogType.INFORMATION, "Opened storage directory '" +STORAGE_PATH + "' in explorer.");
            } catch (IOException exception) {
                Logger.log(LogType.ERROR, "Can not open storage directory!");
                Logger.logException(exception);
            }
        });

        showRecipesButton.setName("buttonMenuShowRecipes");
        showRecipesButton.addActionListener(controller);
        ingredientsButton.setName("buttonMenuShowIngredients");
        ingredientsButton.addActionListener(controller);
        addRecipeButton.setName("buttonMenuAddRecipe");
        addRecipeButton.addActionListener(controller);
    }
}
