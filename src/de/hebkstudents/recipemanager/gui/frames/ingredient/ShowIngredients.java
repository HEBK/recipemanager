package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import de.hebkstudents.recipemanager.ingredient.IngredientFilter;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

/**
 * ShowIngredients class
 * Class for the frame that is used to show all available or filtered ingredients
 */
public class ShowIngredients extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * Button to open frame to filter ingredients
     */
    private JButton filterButton;

    /**
     * Button to open frame to add a new ingredient
     */
    private JButton addIngredientButton;

    /**
     * Button to delete selected ingredient
     */
    private JButton deleteIngredientButton;

    /**
     * Button to open frame to edit the selected ingredient
     */
    private JButton editIngredientButton;

    /**
     * Button to close this frame
     */
    private JButton closeButton;

    /**
     * Table object of the table that contains the ingredients
     */
    private JTable ingredientsTable;

    /**
     * Label that shows the current count of elements in table
     */
    private JLabel countLabel;

    /**
     * TableModel for the table that contains the ingredients
     */
    private DefaultTableModel ingredientsTableModel;

    /**
     * Current filter for ingredients
     * If null, no filters are applied
     */
    private final IngredientFilter filter;

    /**
     * ShowIngredients constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     * @param filter Filter that should be applied to the table
     */
    public ShowIngredients(GUIController controller, IngredientFilter filter) {
        super(controller, buildFrameTitle("Zutatenliste"), DEFAULT_DIMENSION, true);
        this.filter = filter;
        init();
    }

    @Override
    protected void init() {
        add(root);
        initButtons();
        setMinimumSize(new Dimension(750,550));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Set Count label
        setCountLabel(ingredientsTable.getRowCount());
    }

    /**
     * Initializes the function of all buttons
     */
    private void initButtons()
    {
        filterButton.setName("buttonIngredientFilter");
        filterButton.addActionListener(getController());

        closeButton.addActionListener(e -> dispose());

        editIngredientButton.addActionListener(e -> {
            Component a = this;
            if (!ingredientsTable.getSelectionModel().isSelectionEmpty()) {
                int selectedRow = ingredientsTable.getSelectedRow();
                int ingredientID = Integer.parseInt(ingredientsTable.getValueAt(selectedRow, 0).toString());

                try {
                    Ingredient ingredient = IngredientController.getIngredient(ingredientID);
                    getController().openFrameEditIngredient(ingredient);
                    dispose();
                } catch (IngredientNotFoundException ex) {

                    new Thread(() -> JOptionPane.showMessageDialog(a, "Die Zutat kann nicht bearbeitet werden!\n\nDie Zutat existiert nicht", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                    Logger.log(LogType.ERROR, "Cannot edit ingredient! Ingredient was not found!");
                    Logger.logException(ex);
                }
            } else new Thread(() -> JOptionPane.showMessageDialog(a, "Es wurde keine Zutat ausgewählt!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
        });

        addIngredientButton.setName("buttonIngredientsAddIngredient");
        addIngredientButton.addActionListener(controller);

        deleteIngredientButton.addActionListener(e -> {
            AppFrame a = this;
            // Check if a row/ingredient is selected
            if (!ingredientsTable.getSelectionModel().isSelectionEmpty()) {

                // Row & IngredientID
                int selectedRow = ingredientsTable.getSelectedRow();
                int ingredientID = Integer.parseInt(ingredientsTable.getValueAt(selectedRow, 0).toString());

                // Ask if ingredient should really be deleted
                if (JOptionPane.showConfirmDialog(this, "Möchten Sie die ausgewählte Zutat wirklich löschen?", buildFrameTitle("Zutat löschen"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    try {
                        if (!IngredientController.isInUse(ingredientID)) {
                            if (IngredientController.deleteIngredient(ingredientID)) {

                                // Show success msg
                                new Thread(() -> JOptionPane.showMessageDialog(a, "Die Zutat wurde erfolgreich gelöscht!", buildFrameTitle("Zutat gelöscht"), JOptionPane.INFORMATION_MESSAGE)).start();

                                // Delete Row from Table
                                ingredientsTableModel.removeRow(selectedRow);

                                // Set Count label
                                setCountLabel(ingredientsTable.getRowCount());
                            } else {
                                new Thread(() -> JOptionPane.showMessageDialog(a, "Beim Löschen der Zutat ist ein Fehler aufgetreten!\n\nWeitere Informationen können Sie dem Log entnehmen.", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                            }
                        } else {
                            new Thread(() -> JOptionPane.showMessageDialog(a, "Die Zutat wird von einem Rezept verwendet und kann daher nicht gelöscht werden!", buildFrameTitle("Fehler"), JOptionPane.WARNING_MESSAGE)).start();
                        }
                    } catch (IngredientNotFoundException | SQLException ex) {
                        new Thread(() -> JOptionPane.showMessageDialog(a, "Beim Löschen der Zutat ist ein Fehler aufgetreten!\n\nWeitere Informationen können Sie dem Log entnehmen.", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                        Logger.logException(ex);
                    }
                }
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(a, "Es wurde keine Zutat ausgewählt!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
            }
        });
    }

    /**
     * Gets all/filtered ingredients as table ready two-dimensional-array
     * @param filter Filter-Object to be applied (null -> no filter)
     * @return All/filtered ingredients as two-dim-array
     */
    private Object[][] getTableData(IngredientFilter filter)
    {
        Ingredient[] ingredients = IngredientController.getIngredients(filter);
        Object[][] rows = new Object[ingredients.length][5];

        for (int i = 0; i < rows.length; i++) {
            rows[i][0] = String.valueOf(ingredients[i].getIngredientID());
            rows[i][1] = ingredients[i].getName();
            rows[i][2] = ingredients[i].isVegetarian();
            rows[i][3] = ingredients[i].isVegan();
            rows[i][4] = ingredients[i].getIngredientCategory().getName();
        }

        return rows;
    }

    /**
     * Generates the table from table model
     */
    private void buildTable()
    {
        String[] headRow = {"ID", "Name", "Vegetarisch", "Vegan", "Kategorie"};

        Object[][] contentRows = getTableData(filter);

        // TableModel
        ingredientsTableModel = new DefaultTableModel(contentRows, headRow) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 0 -> Integer.class;
                    case 1, 4 -> String.class;
                    case 2, 3 -> Boolean.class;
                    default -> null;
                };
            }
        };

        // Create Table from Model
        ingredientsTable = new JTable(ingredientsTableModel);

        // Set columns sortable
        ingredientsTable.setAutoCreateRowSorter(true);

        // Increase row height
        ingredientsTable.setRowHeight(20);

        // Only one line
        ingredientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set column widths
        int width = ingredientsTable.getPreferredSize().width;
        ingredientsTable.getColumnModel().getColumn(0).setPreferredWidth((int) Math.floor(width*0.025));
        ingredientsTable.getColumnModel().getColumn(1).setPreferredWidth((int) Math.floor(width*0.45));
        ingredientsTable.getColumnModel().getColumn(2).setPreferredWidth((int) Math.floor(width*0.05));
        ingredientsTable.getColumnModel().getColumn(3).setPreferredWidth((int) Math.floor(width*0.05));
        ingredientsTable.getColumnModel().getColumn(4).setPreferredWidth((int) Math.floor(width*0.435));
    }

    /**
     * Sets the label which shows the current row count
     * @param count Row count
     */
    private void setCountLabel(int count)
    {
        countLabel.setText("Es wurden " + count + " Zutaten gefunden.");
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        Runnable buildTable = this::buildTable;
        buildTable.run();
    }
}
