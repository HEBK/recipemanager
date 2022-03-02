package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;

public class AddRecipe extends AppFrame {
    private JPanel root;
    private JTextArea textArea1;
    private JTable table1;
    private JButton addRecipeButton;
    private JComboBox difficultyComboBox;
    private JTextPane textPane1;
    private JTextArea textArea2;

    public AddRecipe(GUIController controller) {
        super(controller, buildFrameTitle("Rezept hinzufügen"), new Dimension(600, 400), true);
    }

    @Override
    protected void init() {

    }
}
