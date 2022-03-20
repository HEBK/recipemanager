package de.hebkstudents.recipemanager.gui.frames.other;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.design.LaF;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;

/**
 * Options class
 * Class for the frame that is used to configure app options
 */
public class Options extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * ComboBox which contains all available LaF's
     */
    private JComboBox<LaF> designComboBox;

    /**
     * Checkbox that defines if the app should check for updates during startup
     */
    private JCheckBox checkUpdatesCheckbox;

    /**
     * Button to save options
     */
    private JButton saveButton;

    /**
     * Button to close the options frame
     */
    private JButton closeButton;

    /**
     * Checkbox that defines if the logo in Menu should be animated
     */
    private JCheckBox animatedMenuLogoCheckBox;

    /**
     * Options constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
    public Options(GUIController controller) {
        super(controller, buildFrameTitle("Optionen"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init()
    {
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
        checkUpdatesCheckbox.setSelected(Boolean.parseBoolean(DEFAULT_CONFIG.read("checkForUpdates")));
        animatedMenuLogoCheckBox.setSelected(Boolean.parseBoolean(DEFAULT_CONFIG.read("animatedMenuLogo")));

        closeButton.addActionListener(e -> dispose());
        saveButton.addActionListener(e -> {
            boolean lafChanged = !((LaF) Objects.requireNonNull(designComboBox.getSelectedItem())).getClassName().equals(UIManager.getLookAndFeel().getClass().getName());
            boolean logoDesignChanged = animatedMenuLogoCheckBox.isSelected() != Boolean.parseBoolean(DEFAULT_CONFIG.read("animatedMenuLogo"));
            boolean restartNeeded = lafChanged || logoDesignChanged;
            
            ArrayList<Boolean> status = new ArrayList<>();
            status.add(DEFAULT_CONFIG.write("checkForUpdates", String.valueOf(checkUpdatesCheckbox.isSelected())));
            status.add(DEFAULT_CONFIG.write("designClass", ((LaF) Objects.requireNonNull(designComboBox.getSelectedItem())).getClassName()));
            status.add(DEFAULT_CONFIG.write("animatedMenuLogo", String.valueOf(animatedMenuLogoCheckBox.isSelected())));

            if (status.stream().anyMatch(j -> !j)) {
                JOptionPane.showMessageDialog(this, "Ein oder mehrere Einstellungen konnten nicht gespeichert werden!", buildFrameTitle("Warnung"), JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Optionen gespeichert!" + (restartNeeded ? "\n\nDie App wird wegen Änderungen am Erscheinungsbild neugestartet!" : ""), buildFrameTitle("Optionen gespeichert"), JOptionPane.INFORMATION_MESSAGE);
            }

            if (restartNeeded) {
                RecipeManager.loadLookAndFeel(((LaF)designComboBox.getSelectedItem()).getClassName());
                getController().stop(true);
            } else {
                dispose();
            }
        });
    }

    /**
     * IntelliJ Idea custom component creation method
     */
    private void createUIComponents() {
        designComboBox = new JComboBox<>(LOOK_AND_FEEL_TPS.toArray(new LaF[0]));

        for (int i = 0; i < LOOK_AND_FEEL_TPS.toArray(new LaF[0]).length; i++) {
            if (LOOK_AND_FEEL_TPS.toArray(new LaF[0])[i].getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
                designComboBox.setSelectedIndex(i);
            }
        }
    }
}
