package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.*;

import javax.swing.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * AddRecipeIngredient class
 * Class for the frame that is used to add ingredients to a recipe
 */
public class AddRecipeIngredient extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * ComboBox which is used to select the ingredient
     */
    private JComboBox<Ingredient> ingredientComboBox;

    /**
     * Spinner which is used to define the quantity of the ingredient
     */
    private JSpinner quantitySpinner;

    /**
     * Button to add the ingredient
     */
    private JButton addIngredientButton;

    /**
     * Button to close this frame
     */
    private JButton closeButton;

    /**
     * ComboBox to select the unit for the ingredient
     */
    private JComboBox<IngredientUnit> ingredientUnitComboBox;


    /**
     * AddRecipeIngredient constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
    public AddRecipeIngredient(GUIController controller) {
        super(controller, buildFrameTitle("Zutat zum Rezept hinzufügen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init()
    {
        add(root);
        pack();
        setResizable(false);
        initializeComponents();
    }

    /**
     * Initializes the components in this frame
     * (e.g. setting default values & ranges)
     */
    private void initializeComponents()
    {
        closeButton.addActionListener(e -> dispose());
        quantitySpinner.setValue(1);
        quantitySpinner.addChangeListener(e -> {
            if ((Integer) quantitySpinner.getValue() < 1) quantitySpinner.setValue(1);
        });

        addIngredientButton.addActionListener(e -> {
            try {
                if (getController().addIngredientToRecipe(new Ingredient(((Ingredient)ingredientComboBox.getSelectedItem()).getIngredientID(), (int)quantitySpinner.getValue(), (IngredientUnit) ingredientUnitComboBox.getSelectedItem()))) {
                    dispose();
                }
            } catch (IngredientNotFoundException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {

            }
        });
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents()
    {
        ingredientComboBox = new JComboBox<>(IngredientController.getIngredients(new IngredientFilter("ORDER BY name")));
        ingredientUnitComboBox = new JComboBox<>(IngredientUnitController.getAllUnits());
    }
}
