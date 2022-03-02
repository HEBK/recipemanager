package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;

public class AddIngredient extends AppFrame {
    private JPanel root;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JCheckBox vegetarischCheckBox;
    private JCheckBox veganCheckBox;
    private JButton hinzufügenButton;

    public AddIngredient(GUIController controller){
        super(controller, buildFrameTitle("Zutat hinzufügen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {

    }
}
