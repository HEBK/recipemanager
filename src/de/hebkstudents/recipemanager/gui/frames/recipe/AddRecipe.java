package de.hebkstudents.recipemanager.gui.frames.recipe;

import de.hebkstudents.recipemanager.exception.InvalidRecipeException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.recipe.Recipe;
import de.hebkstudents.recipemanager.recipe.RecipeCategory;
import de.hebkstudents.recipemanager.recipe.RecipeCategoryController;
import de.hebkstudents.recipemanager.recipe.RecipeController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * AddRecipe class
 * Class for the frame that is used to add a new recipe
 */
public class AddRecipe extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * TextField for the name of the recipe
     */
    private JTextField recipeNameTextfield;

    /**
     * TextArea to type in the recipe description
     */
    private JTextArea descriptionTextArea;

    /**
     * ComboBox to select the recipe difficulty
     */
    private JComboBox<String> difficultyComboBox;

    /**
     * Spinner to define the needed time for this recipe
     */
    private JSpinner timeSpinner;

    /**
     * Button to add the recipe to the DB
     */
    private JButton addRecipeButton;

    /**
     * Button to add a new ingredient to the recipe
     */
    private JButton addIngredientButton;

    /**
     * Button to remove an ingredient from the recipe
     */
    private JButton removeIngredientButton;

    /**
     * Button to close this window
     */
    private JButton closeButton;

    /**
     * ComboBox to select the recipe category
     */
    private JComboBox<RecipeCategory> recipeCategoryComboBox;

    /**
     * Table to display the current added ingredients
     */
    private JTable ingredientsTable;

    /**
     * Spinner to define the default portion count
     */
    private JSpinner defaultQuantities;

    /**
     * Label which shows the current count of ingredients
     */
    private JLabel ingredientCountLabel;

    /**
     * Model for the ingredient table that defines the columns etc.
     */
    private DefaultTableModel ingredientsTableModel;

    /**
     * ArrayList that is used to temporary save the added ingredients
     */
    private final ArrayList<Ingredient> temporaryIngredients = new ArrayList<>();

    /**
     * AddRecipe constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
    public AddRecipe(GUIController controller) {
        super(controller, buildFrameTitle("Rezept hinzuf??gen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        setMinimumSize(new Dimension(750,550));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initializeComponents();
    }

    /**
     * Initializes the components in this frame
     * (e.g. setting default values & ranges)
     */
    private void initializeComponents()
    {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                getController().closeFrameAddRecipeIngredient();
                dispose();
            }
        });
        closeButton.addActionListener(e -> {
            getController().closeFrameAddRecipeIngredient();
            dispose();
        });

        addIngredientButton.setName("buttonAddRecipeAddRecipeIngredient");
        addIngredientButton.addActionListener(getController());

        timeSpinner.setValue(5);
        timeSpinner.addChangeListener(e -> {
            if ((Integer) timeSpinner.getValue() < 0) {
                timeSpinner.setValue(0);
            }
        });

        defaultQuantities.setValue(4);
        defaultQuantities.addChangeListener(e -> {
            if ((Integer) defaultQuantities.getValue() < 1) defaultQuantities.setValue(1);
        });

        removeIngredientButton.addActionListener(e -> {

            if (ingredientsTable.getSelectionModel().isSelectionEmpty()) {
                AppFrame a = this;
                new Thread(() -> JOptionPane.showMessageDialog(a, "Es wurde keine Zutat ausgew??hlt, die gel??scht werden soll!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                return;
            }

            int selectedRow = ingredientsTable.getSelectedRow();
            String ingredientName = (String)ingredientsTable.getValueAt(selectedRow, 0);

            if (temporaryIngredients.removeIf(ingredient -> ingredient.getName().equals(ingredientName))) {
                ingredientsTableModel.removeRow(selectedRow);
                setIngredientCountLabel(ingredientsTable.getRowCount());
                JOptionPane.showMessageDialog(this, "Die Zutat wurde vom Rezept entfernt!", buildFrameTitle("Zutat entfernt!"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(this, "Die Zutat konnte nicht entfernt werden!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                Logger.log(LogType.ERROR, "Cannot remove ingredient '"+ingredientName+"' from recipe! Most likely it is shown in table but does not exist in the list of ingredients.");
            }
        });

        addRecipeButton.addActionListener(e -> {
            if (recipeNameTextfield.getText().isEmpty() || recipeNameTextfield.getText().charAt(0) == ' ') {
                new Thread(() -> JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Namen f??r das Rezept ein!\n\nAchtung: Der Name darf nicht mit einem Leerzeichen beginnen!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                return;
            }
            if (descriptionTextArea.getText().isEmpty()) {
                new Thread(() -> JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Beschreibung f??r das Rezept ein!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                return;
            }
            if (temporaryIngredients.size() == 0) {
                new Thread(() -> JOptionPane.showMessageDialog(null, "Bitte f??gen Sie mindestens eine Zutat dem Rezept hinzu.", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                return;
            }

            Ingredient[] ingredients = new Ingredient[temporaryIngredients.size()];
            temporaryIngredients.toArray(ingredients);
            Recipe r = new Recipe(recipeNameTextfield.getText(), descriptionTextArea.getText(), ingredients, (int)defaultQuantities.getValue(), (int) timeSpinner.getValue(), difficultyComboBox.getSelectedIndex(), (RecipeCategory) recipeCategoryComboBox.getSelectedItem());

            System.out.println(r.getCategory());

            try {
                if (RecipeController.addRecipe(r)) {
                    new Thread(() -> JOptionPane.showMessageDialog(null, "Das Rezept wurde hinzugef??gt!", buildFrameTitle("Rezept hinzugef??gt"), JOptionPane.INFORMATION_MESSAGE)).start();
                    dispose();
                }
            } catch (InvalidRecipeException ex) {
                new Thread(() -> JOptionPane.showMessageDialog(null, "Das generierte Rezept-Objekt war fehlerhaft und konnte nicht hinzugef??gt werden!\n\nWeitere Informationen k??nnen dem Log entnommen werden.", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                Logger.logException(ex);
            }
        });
    }

    /**
     * Builds/Initializes the table for the ingredients
     */
    private void buildIngredientsTable()
    {
        String[]    headRow = {"Zutat", "Menge"};
        Object[][]  contentRows = {};

        ingredientsTableModel = new DefaultTableModel(contentRows, headRow){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ingredientsTable = new JTable(ingredientsTableModel);

        // Height
        ingredientsTable.setRowHeight(20);

        // Sort
        ingredientsTable.setAutoCreateRowSorter(false);

        // Selection
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Sizes
        int width = ingredientsTable.getPreferredSize().width;
        ingredientsTable.getColumnModel().getColumn(0).setPreferredWidth((int) (width*0.95));
        ingredientsTable.getColumnModel().getColumn(1).setPreferredWidth((int) (width*0.05));
    }

    /**
     * Adds a new ingredient to the recipe and the ingredient table
     * @param i Ingredient object
     * @return true if successfully added
     */
    public boolean addIngredient(Ingredient i)
    {
        if (i == null || i.getUnit() == null || i.getQuantity() == null) {
            Logger.log(LogType.ERROR, "Cannot add incomplete ingredient to recipe! Either your ingredient is broken or the unit/quantity is missing!");
            new Thread(() -> JOptionPane.showMessageDialog(this, "There was an error adding the ingredient!\n\nMore information can be shown in the log.")).start();
            return false;
        }

        for (Ingredient ti: temporaryIngredients) {
            if (ti.getName().equals(i.getName())) {
                JOptionPane.showMessageDialog(null, "Diese Zutat wurde dem Rezept bereits hinzugef??gt!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        temporaryIngredients.add(i);
        ingredientsTableModel.addRow(new Object[]{i.getName(), i.getQuantity() + " " + i.getUnit().getUnit()});
        setIngredientCountLabel(ingredientsTable.getRowCount());
        return true;
    }

    /**
     * Sets the current count of ingredients
     */
    private void setIngredientCountLabel(int ingredientCount)
    {
        ingredientCountLabel.setText(ingredientCount + " Zutat" + ((ingredientCount != 1) ? "en" : ""));
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        buildIngredientsTable();
        recipeCategoryComboBox = new JComboBox<>(RecipeCategoryController.getAllCategories());
    }
}
