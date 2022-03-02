package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;

public class ShowIngredients extends AppFrame {
    private JPanel root;
    private JButton filterButton;
    private JButton addIngredientButton;
    private JButton deleteIngredientButton;
    private JButton updateIngredientButton;
    private JTable table1;

    public ShowIngredients(GUIController controller) {
        super(controller, buildFrameTitle("Zutatenliste"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
    }
}
