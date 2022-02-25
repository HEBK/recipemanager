package de.hebkstudents.recipemanager.gui.frames;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import java.awt.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.*;

public class Menu extends AppFrame {
    private JPanel root;
    private JLabel appnameLabel;
    private JButton addRecipeButton;
    private JButton showRecipesButton;
    private JButton filterRecipesButton;
    private JButton ingredientsButton;
    private JButton closeButton;
    private JLabel versionLabel;

    public Menu(GUIController controller)
    {
        super(controller, buildFrameTitle("Hauptmenü"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setDefaultCloseOperation(AppFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(750, 550));
        versionLabel.setText("v" + VERSION);
        appnameLabel.setText(APPNAME);
        showRecipesButton.setName("buttonMenuShowRecipes");
        showRecipesButton.addActionListener(controller);
    }
}
