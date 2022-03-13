package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class AddRecipe extends AppFrame {
    private JPanel root;
    private JTextField recipeNameTextfield;
    private JTextArea descriptionTextArea;
    private JComboBox<String> difficultyComboBox;
    private JSpinner timeSpinner;
    private JButton addRecipeButton;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JButton closeButton;

    private JTable ingredientsTable;
    private DefaultTableModel ingredientsTableModel;

    public AddRecipe(GUIController controller) {
        super(controller, buildFrameTitle("Rezept hinzufügen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750,550));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initializeComponents();
    }

    private void initializeComponents()
    {
        closeButton.addActionListener(e -> dispose());

        timeSpinner.setValue(5);
        timeSpinner.addChangeListener(e -> {
            if ((Integer) timeSpinner.getValue() < 0) {
                timeSpinner.setValue(0);
            }
        });
    }

    private void buildIngredientsTable()
    {
        String[]    headRow = {"Zutat", "Menge"};
        Object[][]  contentRows = {};

        ingredientsTableModel = new DefaultTableModel(contentRows, headRow){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ingredientsTable = new JTable(ingredientsTableModel);

        // Height
        ingredientsTable.setRowHeight(20);

        // Sort
        ingredientsTable.setAutoCreateRowSorter(false);

        // Selection
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Sizes
        int width = ingredientsTable.getPreferredSize().width;
        ingredientsTable.getColumnModel().getColumn(0).setPreferredWidth((int) (width*0.95));
        ingredientsTable.getColumnModel().getColumn(1).setPreferredWidth((int) (width*0.05));
    }

    private void createUIComponents() {
        buildIngredientsTable();
    }
}
