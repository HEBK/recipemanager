package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;

public class ShowRecipes extends AppFrame {
    private JPanel root;
    private JTable table1;

    public ShowRecipes(GUIController controller) {
        super(controller, buildFrameTitle("Rezepte anzeigen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
    }
}
