package de.hebkstudents.recipemanager.gui.frames;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;

public class Menu extends AppFrame {
    private JPanel root;
    private JLabel appnameLabel;
    private JButton addRecipeButton;
    private JButton showRecipesButton;
    private JButton filterRecipesButton;
    private JButton ingredientsButton;
    private JButton closeButton;
    private JLabel versionLabel;
    private JButton developerConsoleButton;


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



        showRecipesButton.setName("buttonMenuShowRecipes");
        showRecipesButton.addActionListener(controller);
        ingredientsButton.setName("buttonMenuShowIngredients");
        ingredientsButton.addActionListener(controller);
        addRecipeButton.setName("buttonMenuAddRecipe");
        addRecipeButton.addActionListener(controller);
        filterRecipesButton.setName("buttonRecipeFilter");
        filterRecipesButton.addActionListener(controller);
    }
}
