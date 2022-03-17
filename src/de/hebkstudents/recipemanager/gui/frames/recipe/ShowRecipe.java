package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.recipe.Recipe;
import de.hebkstudents.recipemanager.recipe.RecipeController;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class ShowRecipe extends AppFrame {
    private JPanel root;
    private JSpinner portionSpinner;
    private JButton recalculateButton;
    private JButton closeButton;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;
    private JTextPane descriptionTextPane;
    private JButton editRecipeButton;

    private Recipe recipe;


    private JTable ingredientsTable;
    private JLabel recipeDifficultyLabel;
    private JLabel recipeTimeLabel;
    private JLabel recipeCategoryLabel;
    private JLabel recipeNameLabel;
    private DefaultTableModel ingredientsTableModel;

    public ShowRecipe(GUIController controller, Recipe recipe) {
        super(controller, buildFrameTitle("Rezept: " + recipe.getName()), DEFAULT_DIMENSION, true);
        this.recipe = recipe;
        init();
    }

    @Override
    protected void init()
    {
        add(root);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        initializeComponents();
    }

    private void initializeComponents()
    {
        vegetarianCheckBox.setSelected(new ArrayList<>(Arrays.asList(recipe.getIngredients())).stream().allMatch(Ingredient::isVegetarian));
        veganCheckBox.setSelected(new ArrayList<>(Arrays.asList(recipe.getIngredients())).stream().allMatch(Ingredient::isVegan));
        vegetarianCheckBox.addChangeListener(e -> vegetarianCheckBox.setSelected(new ArrayList<>(Arrays.asList(recipe.getIngredients())).stream().allMatch(Ingredient::isVegetarian)));
        veganCheckBox.addChangeListener(e -> veganCheckBox.setSelected(new ArrayList<>(Arrays.asList(recipe.getIngredients())).stream().allMatch(Ingredient::isVegan)));

        descriptionTextPane.setText(recipe.getDescription());

        recipeDifficultyLabel.setText(RecipeController.getDifficultyByID(recipe.getDifficulty()));
        recipeTimeLabel.setText(recipe.getTime() + " Minute" + ((recipe.getTime() == 1) ? "" : "n"));
        recipeCategoryLabel.setText(recipe.getCategory().getName());
        recipeNameLabel.setText(recipe.getName());

        portionSpinner.setValue(recipe.getDefaultQuantity());

        portionSpinner.addChangeListener(e -> {
            if ((int)portionSpinner.getValue() < 1) {
                portionSpinner.setValue(1);
            }
        });

        closeButton.addActionListener(e -> dispose());

        recalculateButton.addActionListener(e -> recalculateIngredients((int)portionSpinner.getValue()));
    }


    private void recalculateIngredients(int portions)
    {
        DecimalFormat df = new DecimalFormat("0.0");
        for (int i = 0; i < ingredientsTable.getRowCount(); i++) {
            try {
                double quantity = Float.parseFloat(ingredientsTable.getValueAt(i, 1).toString().split(" ")[0]);
                assert recipe.getDefaultQuantity() != 0;
                double onePortion = quantity /(double)recipe.getDefaultQuantity();
                double newQuantity = onePortion * portions;
                ingredientsTableModel.setValueAt(df.format(newQuantity).replace(',', '.') + " " + ingredientsTable.getValueAt(i, 1).toString().split(" ")[1], i, 1);
            } catch (NumberFormatException e) {
                new Thread(() -> JOptionPane.showMessageDialog(null, "Beim Umrechnen der Mengen trat ein Fehler auf!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                Logger.logException(e);
                return;
            }
        }
        recipe.setDefaultQuantity(portions);
    }



    private String getQuantityAndUnit(Ingredient i)
    {
        return (i != null && i.getName() != null && i.getUnit() != null && i.getUnit().getUnit() != null) ? i.getQuantity() + " " + i.getUnit().getUnit() : "n/A";
    }

    private Object[][] getTableData()
    {
        Object[][] rows = new Object[recipe.getIngredients().length][2];

        for (int i = 0; i < rows.length; i++) {
            rows[i][0]  = recipe.getIngredients()[i].getName();
            rows[i][1]  = getQuantityAndUnit(recipe.getIngredients()[i]);
        }
        return rows;
    }

    private void buildTable()
    {
        String[] headRow            = {"Zutat", "Menge"};
        Object[][] contentRows      = getTableData();

        ingredientsTableModel = new DefaultTableModel(contentRows, headRow){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ingredientsTable = new JTable(ingredientsTableModel);

        ingredientsTable.setRowHeight(20);

        ingredientsTable.setAutoCreateRowSorter(true);

        ingredientsTable.setEnabled(false);

        // Set Column width
        int width = ingredientsTable.getWidth();
        ingredientsTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        ingredientsTable.getColumnModel().getColumn(1).setPreferredWidth(20);

    }

    private void createUIComponents() {
        buildTable();
    }
}
