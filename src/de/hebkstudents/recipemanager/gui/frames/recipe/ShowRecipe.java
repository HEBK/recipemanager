package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;

public class ShowRecipe extends AppFrame {
    private JPanel root;
    private JTextArea recipeName;
    private JTextArea description;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;
    private JButton convertButton;
    private JComboBox portionComboBox;
    private JTable ingredientsTable;
    private JButton closeButton;

    public ShowRecipe(GUIController controller) {
        super(controller, buildFrameTitle("Rezept"), new Dimension(600, 400), true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750, 550));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
