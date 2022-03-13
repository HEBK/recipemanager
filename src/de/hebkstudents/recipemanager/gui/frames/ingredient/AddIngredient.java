package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.*;

import javax.swing.*;

import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class AddIngredient extends AppFrame {
    private JPanel root;
    private JLabel appnameLabel;
    private JTextField nameTextField;
    private JComboBox<IngredientCategory> categoryComboBox;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox veganCheckBox;
    private JButton addIngredientButton;
    private JButton backButton;

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

    private void initCategoryComboBox()
    {
        categoryComboBox = new JComboBox<>(IngredientCategoryController.getIngredientCategories());
    }

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

    private void createUIComponents() {
        initCategoryComboBox();
    }
}
