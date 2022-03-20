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

/**
 * ShowRecipe class
 * Class for the frame that is used to show a recipe
 */
public class ShowRecipe extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * Spinner which shows the default portion count and can also be used to recalculate the ingredient quantities for the desired portion count
     */
    private JSpinner portionSpinner;

    /**
     * Recalculates portions on click
     */
    private JButton recalculateButton;

    /**
     * Closes this frame
     */
    private JButton closeButton;

    /**
     * Shows if the recipe is vegetarian
     */
    private JCheckBox vegetarianCheckBox;

    /**
     * Shows if the recipe is vegan
     */
    private JCheckBox veganCheckBox;

    /**
     * Shows the description of the recipe in a scrollable textpane
     */
    private JTextPane descriptionTextPane;

    /**
     * Button to edit recipe in a new frame
     */
    private JButton editRecipeButton;

    /**
     * Current recipe object
     */
    private final Recipe recipe;

    /**
     * Table which contains all ingredients for this recipe
     */
    private JTable ingredientsTable;

    /**
     * Label which shows the difficulty for this recipe
     */
    private JLabel recipeDifficultyLabel;

    /**
     * Label which shows the needed time for this recipe
     */
    private JLabel recipeTimeLabel;

    /**
     * Label which shows the category for this recipe
     */
    private JLabel recipeCategoryLabel;

    /**
     * Label which shows the recipes name
     */
    private JLabel recipeNameLabel;

    /**
     * Table model for the ingredients table
     */
    private DefaultTableModel ingredientsTableModel;


    /**
     * ShowRecipe constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     * @param recipe Object of recipe to show in frame
     */
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

    /**
     * Initializes the components in this frame
     * (e.g. setting default values & ranges)
     */
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

    /**
     * Recalculates the ingredients in table
     * @param portions Number of desired portions
     */
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

    /**
     * Gets the Quantity and Unit of an ingredient as String
     * (e.g. Butter 50 g)
     * @param i Object of ingredient
     * @return String with quantity and unit
     */
    private String getQuantityAndUnit(Ingredient i)
    {
        return (i != null && i.getName() != null && i.getUnit() != null && i.getUnit().getUnit() != null) ? i.getQuantity() + " " + i.getUnit().getUnit() : "n/A";
    }

    /**
     * Gets ingredients as table ready two-dimensional-array
     * @return ingredients as two-dim-array
     */
    private Object[][] getTableData()
    {
        Object[][] rows = new Object[recipe.getIngredients().length][2];

        for (int i = 0; i < rows.length; i++) {
            rows[i][0]  = recipe.getIngredients()[i].getName();
            rows[i][1]  = getQuantityAndUnit(recipe.getIngredients()[i]);
        }
        return rows;
    }

    /**
     * Builds the ingredient table for the current recipe
     */
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

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        buildTable();
    }
}
