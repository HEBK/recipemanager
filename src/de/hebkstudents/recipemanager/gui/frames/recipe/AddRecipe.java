package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;

public class AddRecipe extends AppFrame {
    private JPanel root;
    private JTable table1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JComboBox comboBox1;
    private JSpinner spinner1;
    private JButton addRecipeButton;

    public AddRecipe(GUIController controller) {
        super(controller, buildFrameTitle("Rezept hinzufügen"), new Dimension(600, 400), true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750,550));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
