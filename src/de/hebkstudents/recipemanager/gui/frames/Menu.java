package de.hebkstudents.recipemanager.gui.frames;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import javax.swing.text.IconView;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

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
    private JButton optionsButton;
    private JButton aboutButton;
    private JLabel iconLabel;

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
        optionsButton.setName("buttonMenuOptions");
        optionsButton.addActionListener(controller);
        aboutButton.setName("buttonMenuAbout");
        aboutButton.addActionListener(controller);

        // Animated logo
        Thread logoAnimator = new Thread(() -> {
            File folder = new File("resources/images/gif/page-turning-book-2/splits");
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            File[] files = new File[listOfFiles.length];
            for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
                if (listOfFiles[i].isFile()) {
                    files[i] = new File("resources/images/gif/page-turning-book-2/splits/" + listOfFiles[i].getName());
                }
            }
            for (int i = 0; i < files.length; i++) {
                iconLabel.setIcon(new ImageIcon(files[i].getPath()));
                try { Thread.sleep(40); } catch (InterruptedException ignored) {}
                if (i == files.length-1) i = 0;
            }
        });
        if (Boolean.parseBoolean(DEFAULT_CONFIG.read("animatedMenuLogo"))) logoAnimator.start();
    }
}
