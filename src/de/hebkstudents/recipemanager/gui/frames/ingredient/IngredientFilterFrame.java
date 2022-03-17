package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.IngredientCategory;
import de.hebkstudents.recipemanager.ingredient.IngredientCategoryController;
import de.hebkstudents.recipemanager.ingredient.IngredientFilter;

import javax.swing.*;
import java.util.Objects;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * IngredientFilterFrame class
 * Class for the frame that is used to filter ingredients
 */
public class IngredientFilterFrame extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * Query string for ingredients to search for
     */
    private JTextField queryTextfield;

    /**
     * JComboBox that is used to select the ingredient category
     */
    private JComboBox<IngredientCategory> categoryCombobox;

    /**
     * CheckBox to filter for vegetarian ingredients
     */
    private JCheckBox vegetarianCheckBox;

    /**
     * CheckBox to filter for vegan ingredients
     */
    private JCheckBox veganCheckBox;

    /**
     * Button to apply filter to ingredients list
     */
    private JButton applyFilterButton;

    /**
     * CheckBox to activate filtering for vegan & vegetarian ingredients
     */
    private JCheckBox activateFoodTypeCheckbox;


    /**
     * IngredientFilterFrame constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
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

    /**
     * Initializes the components in this frame
     * (e.g. setting default values & ranges)
     */
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

    /**
     * Enables or disables the vegan & vegetarian checkbox whether filter for them is activated or not
     */
    private void changeFoodTypeCheckboxesState()
    {
        vegetarianCheckBox.setEnabled(activateFoodTypeCheckbox.isSelected());
        veganCheckBox.setEnabled(activateFoodTypeCheckbox.isSelected());
    }

    /**
     * Gets the current status (Enabled) for the vegan & vegetarian checkbox as an array
     * @return Array with two booleans (vegan(0) & vegetarian(1))
     */
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

    /**
     * Initializes the category combobox with values from DB
     */
    private void initCategoryComboBox()
    {
        categoryCombobox = new JComboBox<>();
        categoryCombobox.addItem(new IngredientCategory(-1, "Alle Kategorien"));
        for (IngredientCategory category: IngredientCategoryController.getIngredientCategories()) {
            categoryCombobox.addItem(category);
        }
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        initCategoryComboBox();
    }
}
