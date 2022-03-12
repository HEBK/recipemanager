package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import java.awt.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;

public class RecipeFilter extends AppFrame {
    private JPanel root;
    private JCheckBox vegetarischCheckBox;
    private JCheckBox veganCheckBox;
    private JButton suchenButton;
    private JComboBox comboBox6;
    private JComboBox comboBox7;
    private JComboBox comboBox3;
    private JComboBox comboBox4;
    private JComboBox comboBox5;
    private JComboBox comboBox1;
    private JComboBox comboBox8;
    private JComboBox comboBox2;
    private JTextField textField1;

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
