package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import de.hebkstudents.recipemanager.storage.DatabaseController;

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

        Object[][] rows = {};

        recipesTableModel = new DefaultTableModel(rows, headrow) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 5:
                    case 2:
                    case 1:
                        return String.class;
                    case 3:
                    case 4:
                        return Boolean.class;
                    default: return null;
                }
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
