package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class IngredientFilter extends AppFrame {
    private JPanel root;
    private JButton searchButton;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;

    public IngredientFilter(GUIController controller) {
        super(controller, buildFrameTitle("Zutatenfilter"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750,550));
    }
}
