package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.*;

import javax.swing.*;

import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * Add ingredient class
 * Class for the frame that is used to add new ingredients
 */
public class AddIngredient extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * JTextField that is used to type in the name of the ingredient
     */
    private JTextField nameTextField;

    /**
     * JComboBox which is used to select the category of the ingredient
     */
    private JComboBox<IngredientCategory> categoryComboBox;

    /**
     * JCheckBox which is used to define if the ingredient is vegetarian
     */
    private JCheckBox vegetarianCheckBox;

    /**
     * JCheckBox which is used to define if the ingredient is vegan
     */
    private JCheckBox veganCheckBox;

    /**
     * JButton which is clicked to add the ingredient with its values to the DB
     */
    private JButton addIngredientButton;

    /**
     * JButton to go back to the ingredients list
     */
    private JButton backButton;

    /**
     * AddIngredient constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
    public AddIngredient(GUIController controller){
        super(controller, buildFrameTitle("Zutat hinzufügen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        pack();
        setResizable(false);
        initComponents();
    }

    /**
     * Sets the items for the ingredient category combobox (From DB)
     */
    private void initCategoryComboBox()
    {
        categoryComboBox = new JComboBox<>(IngredientCategoryController.getIngredientCategories());
    }

    /**
     * Checks if all inputs are filled correctly and requests the addition to the database using the IngredientController
     */
    private void addIngredient()
    {
        Component g = this;
        if (nameTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Bitte geben Sie den Namen der Zutat ein!", buildFrameTitle("Error"), JOptionPane.ERROR_MESSAGE);
            return;
        }
        Ingredient ingredient = new Ingredient(nameTextField.getText(), veganCheckBox.isSelected(), vegetarianCheckBox.isSelected(), (IngredientCategory) categoryComboBox.getSelectedItem());
        if (IngredientController.addIngredient(ingredient)) {

            new Thread(() -> JOptionPane.showMessageDialog(g, "Die Zutat wurde erfolgreich hinzugefügt!", buildFrameTitle("Zutat hinzugefügt"), JOptionPane.INFORMATION_MESSAGE)).start();
            nameTextField.setText(null);
            veganCheckBox.setSelected(false);
            vegetarianCheckBox.setSelected(false);
            categoryComboBox.setSelectedIndex(0);
            nameTextField.requestFocus();
        } else {
            new Thread(() -> JOptionPane.showMessageDialog(g, "Die Zutat konnte nicht hinzugefügt werden oder existiert bereits!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
        }
    }


    /**
     * Initializes the components in this frame
     * (e.g. setting default values & ranges)
     */
    private void initComponents()
    {
        vegetarianCheckBox.setSelected(true);
        vegetarianCheckBox.addItemListener(e -> {
            if (!vegetarianCheckBox.isSelected()) veganCheckBox.setSelected(false);
        });

        veganCheckBox.addItemListener(e -> {
            if (veganCheckBox.isSelected()) vegetarianCheckBox.setSelected(true);
        });

        addIngredientButton.addActionListener(e -> addIngredient());
        backButton.addActionListener(e -> getController().openFrameShowIngredients(null));
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        initCategoryComboBox();
    }
}
