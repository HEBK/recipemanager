package de.hebkstudents.recipemanager.gui.frames.ingredient;

import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.Ingredient;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import de.hebkstudents.recipemanager.ingredient.IngredientFilter;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

import static de.hebkstudents.recipemanager.storage.AppProperties.DEFAULT_DIMENSION;

public class ShowIngredients extends AppFrame {
    private JPanel root;
    private JButton filterButton;
    private JButton addIngredientButton;
    private JButton deleteIngredientButton;
    private JButton updateIngredientButton;
    private JButton closeButton;

    private JTable ingredientsTable;
    private JLabel countLabel;
    private DefaultTableModel ingredientsTableModel;

    private final IngredientFilter filter;

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

    private void initButtons()
    {
        filterButton.setName("buttonIngredientFilter");
        filterButton.addActionListener(getController());

        closeButton.addActionListener(e -> dispose());

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
                    } catch (IngredientNotFoundException ex) {
                        new Thread(() -> JOptionPane.showMessageDialog(a, "Beim Löschen der Zutat ist ein Fehler aufgetreten!\n\nWeitere Informationen können Sie dem Log entnehmen.", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
                        Logger.logException(ex);
                    }
                }
            } else {
                new Thread(() -> JOptionPane.showMessageDialog(a, "Es wurde keine Zutat ausgewählt!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE)).start();
            }
        });
    }

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
                switch (columnIndex) {
                    case 0:
                        return Integer.class;
                    case 1:
                    case 4:
                        return String.class;
                    case 2:
                    case 3:
                        return Boolean.class;
                    default:
                        return null;
                }
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

    private void setCountLabel(int count)
    {
        countLabel.setText("Es wurden " + count + " Zutaten gefunden.");
    }

    private void createUIComponents() {
        Runnable buildTable = this::buildTable;
        buildTable.run();
    }
}
