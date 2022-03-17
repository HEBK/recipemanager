package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.recipe.Recipe;

import javax.swing.*;
import java.awt.*;

public class ShowRecipe extends AppFrame {
    private JPanel root;
    private JTextArea description;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;
    private JButton convertButton;
    private JTable ingredientsTable;
    private JSpinner spinner1;
    private JButton schließenButton;


    private Recipe recipe;

    public ShowRecipe(GUIController controller, Recipe recipe) {
        super(controller, buildFrameTitle("Rezept"), new Dimension(600, 400), true);
        this.recipe = recipe;
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750, 550));
        initializeComponents();
    }

    private void initializeComponents(){
        schließenButton.addActionListener(e -> dispose());
    }

    private void createUIComponents() {
    }
}
