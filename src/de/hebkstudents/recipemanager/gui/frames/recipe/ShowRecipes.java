package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.exception.RecipeNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import de.hebkstudents.recipemanager.ingredient.IngredientFilter;
import de.hebkstudents.recipemanager.recipe.Recipe;
import de.hebkstudents.recipemanager.recipe.RecipeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class ShowRecipes extends AppFrame {
    private JPanel root;
    private JButton filterButton;
    private JButton addRecipeButton;
    private JButton deleteRecipeButton;
    private JButton modifyRecipeButton;
    private JButton showRecipeButton;
    private JButton closeButton;
    private JLabel countLabel;

    private JTable recipesTable;
    private DefaultTableModel recipesTableModel;

    public ShowRecipes(GUIController controller) {
        super(controller, buildFrameTitle("Rezepte anzeigen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750, 550));
        initializeComponents();
    }

    private void initializeComponents()
    {
        closeButton.addActionListener(e -> dispose());
        addRecipeButton.addActionListener(e -> {
            dispose();
            getController().openFrameAddRecipe();
        });

        setCountLabel(recipesTable.getRowCount());
    }

    private Object[][] getTableData() {
        Recipe[] recipes = RecipeController.getRecipes();
        Object[][] rows = new Object[recipes.length][7];

        for (int i = 0; i < rows.length; i++) {
            rows[i][0] = String.valueOf(recipes[i].getRecipeID());
            rows[i][1] = recipes[i].getName();
            rows[i][2] = recipes[i].getCategory().getName();
            rows[i][3] = RecipeController.getDifficultyByID(recipes[i].getDifficulty());
            rows[i][4] = new ArrayList<>(Arrays.asList(recipes[i].getIngredients())).stream().allMatch(Ingredient::isVegetarian);
            rows[i][5] = new ArrayList<>(Arrays.asList(recipes[i].getIngredients())).stream().allMatch(Ingredient::isVegan);
            rows[i][6] = recipes[i].getTime() + " Minuten";
        }

        return rows;
    }


    private void setTable (){
        String[] headrow = {"ID", "Name", "Kategorie", "Schwierigkeit", "Vegetarisch", "Vegan", "Zeit"};

        Object[][] rows = getTableData();

        recipesTableModel = new DefaultTableModel(rows, headrow) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Integer.class;
                    case 1:
                    case 2:
                    case 3:
                    case 6:
                        return String.class;
                    case 4:
                    case 5:
                        return Boolean.class;
                    default: return null;
                }
            }
        };

        // Create table from model
        recipesTable = new JTable(recipesTableModel);

        // Make rows sortable
        recipesTable.setAutoCreateRowSorter(true);

        // Set row height
        recipesTable.setRowHeight(20);

        // set column width
        int width = recipesTable.getSize().width;
        recipesTable.getColumnModel().getColumn(0).setMaxWidth(50);          // ID
        recipesTable.getColumnModel().getColumn(1).setPreferredWidth(120);     // Name
        recipesTable.getColumnModel().getColumn(2).setPreferredWidth(70);     // Kategorie
        recipesTable.getColumnModel().getColumn(3).setPreferredWidth(70);     // Schwierigkeit
        recipesTable.getColumnModel().getColumn(4).setMaxWidth(75);     // Vegetarisch
        recipesTable.getColumnModel().getColumn(5).setMaxWidth(55);     // Vegan
        recipesTable.getColumnModel().getColumn(6).setPreferredWidth(80);    // Zeit
    }


    private void setCountLabel(int count)
    {
        countLabel.setText("Es wurde" + ((count != 1) ? "n" : "") + " " + count +" Rezept" + ((count != 1) ? "e" : "") + " gefunden!");
    }

    private void createUIComponents() {
        setTable();
    }
}
