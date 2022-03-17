package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.recipe.RecipeCategory;
import de.hebkstudents.recipemanager.recipe.RecipeCategoryController;

import javax.swing.*;
import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * RecipeFilterFrame
 * Not yet in use
 */
public class RecipeFilterFrame extends AppFrame {
    private JPanel root;
    private JTextField queryTextField;
    private JComboBox<RecipeCategory> categoryCombobox;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;
    private JCheckBox activateFoodTypeCheckbox;
    private JComboBox<String> difficultyComboBox;
    private JSpinner maxTimeSpinner;
    private JTable ingredientsTable;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private JButton applyFilterButton;
    private JLabel ingredientCountLabel;

    public RecipeFilterFrame(GUIController controller) {
        super(controller, buildFrameTitle("Rezepte filtern"), DEFAULT_DIMENSION, true);
        init();
    }


    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750,550));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void setIngredientCountLabel(int ingredientCount)
    {
        ingredientCountLabel.setText(ingredientCount + " Zutat" + ((ingredientCount != 1) ? "en" : ""));
    }

    private void createUIComponents() {
        difficultyComboBox = new JComboBox<>(new String[]{"Alle Schwierigkeiten", "Anfänger", "Fortgeschritten", "Experte"});
        categoryCombobox = new JComboBox<>();
        categoryCombobox.addItem(new RecipeCategory(-1, "Alle Kategorien"));
        for (RecipeCategory c : RecipeCategoryController.getAllCategories()) {
            categoryCombobox.addItem(c);
        }

    }
}
