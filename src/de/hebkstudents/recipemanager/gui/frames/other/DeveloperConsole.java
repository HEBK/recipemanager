package de.hebkstudents.recipemanager.gui.frames.other;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;

public class DeveloperConsole extends AppFrame {
    private JPanel root;
    private JLabel appnameLabel;
    private JTextPane consolePane;
    private JButton closeConsoleButton;
    private JScrollPane consoleScrollPane;

    public DeveloperConsole (GUIController controller)
    {
        super(controller, buildFrameTitle("Developer Console"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        pack();
    }
}
