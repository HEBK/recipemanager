package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.*;

import javax.swing.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class AddRecipeIngredient extends AppFrame {

    private JPanel root;
    private JComboBox<Ingredient> ingredientComboBox;
    private JSpinner quantitySpinner;
    private JButton addIngredientButton;
    private JButton closeButton;
    private JComboBox<IngredientUnit> ingredientUnitComboBox;

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


    private void createUIComponents()
    {
        ingredientComboBox = new JComboBox<>(IngredientController.getIngredients(new IngredientFilter("ORDER BY name")));
        ingredientUnitComboBox = new JComboBox<>(IngredientUnitController.getAllUnits());
    }
}
