package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.IngredientCategory;
import de.hebkstudents.recipemanager.ingredient.IngredientCategoryController;
import de.hebkstudents.recipemanager.ingredient.IngredientFilter;

import javax.swing.*;
import java.util.Objects;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class IngredientFilterFrame extends AppFrame {
    private JPanel root;
    private JTextField queryTextfield;
    private JComboBox<IngredientCategory> categoryCombobox;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;
    private JButton applyFilterButton;
    private JCheckBox activateFoodTypeCheckbox;


    public IngredientFilterFrame(GUIController controller) {
        super(controller, buildFrameTitle("Zutatenfilter"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        pack();
        initComponents();
        setResizable(false);
    }

    private void initComponents()
    {
        applyFilterButton.addActionListener(e -> getController().openFrameShowIngredients(new IngredientFilter(queryTextfield.getText(), getFoodTypeStates()[1], getFoodTypeStates()[0], ((IngredientCategory) Objects.requireNonNull(categoryCombobox.getSelectedItem())).getCategoryID() == -1 ? null : (IngredientCategory) categoryCombobox.getSelectedItem(), null)));

        vegetarianCheckBox.setSelected(true);
        vegetarianCheckBox.addItemListener(e -> {
            if (!vegetarianCheckBox.isSelected()) veganCheckBox.setSelected(false);
        });

        veganCheckBox.addItemListener(e -> {
            if (veganCheckBox.isSelected()) vegetarianCheckBox.setSelected(true);
        });

        changeFoodTypeCheckboxesState();
        activateFoodTypeCheckbox.addItemListener(e -> changeFoodTypeCheckboxesState());
    }

    private void changeFoodTypeCheckboxesState()
    {
        vegetarianCheckBox.setEnabled(activateFoodTypeCheckbox.isSelected());
        veganCheckBox.setEnabled(activateFoodTypeCheckbox.isSelected());
    }

    private Boolean[] getFoodTypeStates()
    {
        boolean vegetarian = vegetarianCheckBox.isSelected();
        boolean vegan = veganCheckBox.isSelected();

        if (activateFoodTypeCheckbox.isSelected()) {
            if (vegan) return new Boolean[]{true,true};
            if (vegetarian) return new Boolean[]{true, null};
        }
        return new Boolean[]{null, null};
    }



    private void initCategoryComboBox()
    {
        categoryCombobox = new JComboBox<>();
        categoryCombobox.addItem(new IngredientCategory(-1, "Alle Kategorien"));
        for (IngredientCategory category: IngredientCategoryController.getIngredientCategories()) {
            categoryCombobox.addItem(category);
        }
    }

    private void createUIComponents() {
        initCategoryComboBox();
    }
}
