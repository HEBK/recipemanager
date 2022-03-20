package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.exception.InvalidIngredientException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.ingredient.IngredientCategory;
import de.hebkstudents.recipemanager.ingredient.IngredientCategoryController;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * Edit ingredient class
 * Class for the frame that is used to edit ingredients
 */
public class EditIngredient extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * Spinner element that contains the ingredient ID as value
     */
    private JSpinner idSpinner;

    /**
     * TextField that contains the ingredient name
     */
    private JTextField nameTextField;

    /**
     * ComboBox to chang the category of ingredient
     */
    private JComboBox<IngredientCategory> categoryComboBox;

    /**
     * CheckBox to define whether the ingredient is vegetarian or not
     */
    private JCheckBox vegetarianCheckBox;

    /**
     * CheckBox to define whether the ingredient is vegan or not
     */
    private JCheckBox veganCheckBox;

    /**
     * Button to save changes
     */
    private JButton saveButton;

    /**
     * Button to go back to ingredient list
     */
    private JButton backButton;

    /**
     * Ingredeint object that should be edited
     */
    private final Ingredient ingredient;

    /**
     * EditIngredient constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     * @param ingredient Ingredient object to edit
     */
    public EditIngredient(GUIController controller, Ingredient ingredient) {
        super(controller, buildFrameTitle("Zutat bearbeiten"), DEFAULT_DIMENSION, true);
        this.ingredient = ingredient;
        init();
    }

    @Override
    protected void init() {
        add(root);
        setResizable(false);
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
        idSpinner.setValue(ingredient.getIngredientID());
        idSpinner.addChangeListener(e -> idSpinner.setValue(ingredient.getIngredientID()));
        nameTextField.setText(ingredient.getName());
        categoryComboBox.setSelectedIndex(ingredient.getIngredientCategory().getCategoryID()-1);
        veganCheckBox.setSelected(ingredient.isVegan());
        vegetarianCheckBox.setSelected(ingredient.isVegetarian());
        backButton.addActionListener(e -> getController().openFrameShowIngredients(null));
        saveButton.addActionListener(e -> updateIngredient());
    }

    /**
     * Updates the ingredient in the database
     * (with current values in form fields)
     */
    private void updateIngredient()
    {
        ingredient.setVegetarian(vegetarianCheckBox.isSelected());
        ingredient.setVegan(veganCheckBox.isSelected());
        ingredient.setIngredientCategory((IngredientCategory) categoryComboBox.getSelectedItem());
        Component a = this;

        try {
            if (IngredientController.updateIngredient(ingredient)) {
                JOptionPane.showMessageDialog(a, "Die Zutat wurde erfolgreich aktualisiert!", buildFrameTitle("Zutat aktualisiert!"), JOptionPane.INFORMATION_MESSAGE);
                getController().openFrameShowIngredients(null);
                return;
            }
        } catch (IngredientNotFoundException | InvalidIngredientException e) {
            Logger.log(LogType.ERROR, "Cannot update ingredient because it's invalid or does not exist!");
            Logger.logException(e);
        }
        new Thread(() -> JOptionPane.showMessageDialog(a, "Beim Speichern der Zutat ist ein Fehler augetreten!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        categoryComboBox = new JComboBox<>(IngredientCategoryController.getIngredientCategories());
    }
}
