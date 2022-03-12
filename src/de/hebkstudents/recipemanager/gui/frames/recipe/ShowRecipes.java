package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class ShowRecipes extends AppFrame {
    private JPanel root;
    private JTable table1;
    private JButton filterButton;
    private JButton addRecipeButton;
    private JButton deleteRecipeButton;
    private JButton modifyRecipeButton;
    private JButton showRecipeButton;

    private DefaultTableModel recipesTableModel;

    public ShowRecipes(GUIController controller) {
        super(controller, buildFrameTitle("Rezepte anzeigen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750, 550));
    }

    private void setTable (){
        String[] headrow = {"Name", "Kategorie", "Schwierigkeit", "Vegetarisch", "Vegan", "Zeit"};

        String[][] rows = {{"Spaghetti", "Pasta", "einfach", "ja", "ja", "15 Minuten"}, {"Tiefkühlpizza", "Pizza", "einfach", "nein", "nein", "10 Minuten"}};

        recipesTableModel = new DefaultTableModel(rows, headrow) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1 = new JTable(recipesTableModel);

        table1.setAutoCreateRowSorter(true);
        table1.setRowHeight(25);
    }

    private void createUIComponents() {
        setTable();
    }
}
