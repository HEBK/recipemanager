package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import java.awt.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;

public class RecipeFilter extends AppFrame {
    private JPanel root;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JCheckBox vegetarischCheckBox;
    private JCheckBox veganCheckBox;
    private JButton suchenButton;

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
