package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import java.awt.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;

public class RecipeFilter extends AppFrame {
    private JPanel root;
    private JTextField textField1;
    private JComboBox categoryComboBox;
    private JComboBox difficultyComboBox;
    private JComboBox timeComboBox;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;
    private JButton searchButton;
    private JList list1;

    public RecipeFilter(GUIController controller) {
        super(controller, buildFrameTitle("Rezeptefilter"), DEFAULT_DIMENSION, true);
        init();
    }
    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750,550));
    }
}
