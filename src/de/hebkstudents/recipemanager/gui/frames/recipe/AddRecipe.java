package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import java.awt.*;

import de.hebkstudents.recipemanager.storage.StaticProperties.*;

public class AddRecipe extends AppFrame {
    private JPanel root;

    public AddRecipe(GUIController controller) {
        super(controller, buildFrameTitle("Rezept hinzufügen"), new Dimension(600, 400), true);
    }

    @Override
    protected void init() {

    }
}
