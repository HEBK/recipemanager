package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.exception.RecipeNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.recipe.Recipe;
import de.hebkstudents.recipemanager.recipe.RecipeController;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * ShowRecipes class
 * Class for the frame that is used to list all or filtered recipes
 */
public class ShowRecipes extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * Button that opens the filter frame
     */
    private JButton filterButton;

    /**
     * Button open the frame to add a new recipe to the DB
     */
    private JButton addRecipeButton;

    /**
     * Button to delete the selected recipe
     */
    private JButton deleteRecipeButton;

    /**
     * Button to modify the selected recipe
     */
    private JButton modifyRecipeButton;

    /**
     * Button to show the selected recipe in a new frame
     */
    private JButton showRecipeButton;

    /**
     * Button to close this window
     */
    private JButton closeButton;

    /**
     * Label to show the count of the current recipes in the table
     */
    private JLabel countLabel;

    /**
     * Table to list all or the filtered recipes
     */
    private JTable recipesTable;

    /**
     * TableModel for the table that contains the recipes
     */
    private DefaultTableModel recipesTableModel;

    /**
     * ShowRecipes constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
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

    /**
     * Initializes the components in this frame
     * (e.g. setting default values & ranges)
     */
    private void initializeComponents()
    {
        closeButton.addActionListener(e -> dispose());
        addRecipeButton.addActionListener(e -> {
            dispose();
            getController().openFrameAddRecipe();
        });

        showRecipeButton.addActionListener(e -> {
            if (!recipesTable.getSelectionModel().isSelectionEmpty()) {
                int selectedRow = recipesTable.getSelectedRow();

                try {
                    int recipeID = Integer.parseInt(recipesTable.getValueAt(selectedRow, 0).toString());
                    Recipe recipe = RecipeController.getRecipe(recipeID);

                    dispose();
                    getController().openFrameShowRecipe(recipe);

                } catch (NumberFormatException | RecipeNotFoundException ex) {
                    new Thread(() -> JOptionPane.showMessageDialog(this, "Es ist ein Fehler aufgetreten!\n\nWeitere Informationen im Log", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                    Logger.logException(ex);
                }
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(this, "Bitte wählen Sie ein Rezept aus, um es anzuzeigen!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
            }
        });

        setCountLabel(recipesTable.getRowCount());
    }

    /**
     * Gets the recipes as two-dimensional-array to make them compatible for the table
     * @return two-dimensional-array with the recipe objects
     */
    private Object[][] getTableData() {
        Recipe[] recipes = RecipeController.getRecipes();
        Object[][] rows = new Object[recipes.length][7];

        for (int i = 0; i < rows.length; i++) {
            rows[i][0] = String.valueOf(recipes[i].getRecipeID());
            rows[i][1] = recipes[i].getName();
            rows[i][2] = recipes[i].getCategory().getName();
            rows[i][3] = RecipeController.getDifficultyByID(recipes[i].getDifficulty());
            rows[i][4] = recipes[i].isVegetarian();
            rows[i][5] = recipes[i].isVegan();
            rows[i][6] = recipes[i].getTime() + " Minuten";
        }

        return rows;
    }


    /**
     * Builds/Initializes the table for the recipes
     */
    private void buildTable(){
        String[] headrow = {"ID", "Name", "Kategorie", "Schwierigkeit", "Vegetarisch", "Vegan", "Zeit"};

        Object[][] rows = getTableData();

        recipesTableModel = new DefaultTableModel(rows, headrow) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> Integer.class;
                    case 1, 2, 3, 6 -> String.class;
                    case 4, 5 -> Boolean.class;
                    default -> null;
                };
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


    /**
     * Sets the defined count in the label
     * @param count Recipe count as integer
     */
    private void setCountLabel(int count)
    {
        countLabel.setText("Es wurde" + ((count != 1) ? "n" : "") + " " + count +" Rezept" + ((count != 1) ? "e" : "") + " gefunden!");
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        buildTable();
    }
}
