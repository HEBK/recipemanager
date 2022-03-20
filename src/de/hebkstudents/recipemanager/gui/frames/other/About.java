package de.hebkstudents.recipemanager.gui.frames.other;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.list.AboutPanels;
import de.hebkstudents.recipemanager.utils.UpdateChecker;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;

/**
 * About class
 * Class for the frame that is used to show all information about the software, its license and the third-party libraries
 */
public class About extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * Button which opens the github repo page
     */
    private JButton githubButton;

    /**
     * Button which closes this frame
     */
    private JButton closeButton;

    /**
     * Panel that contains all components that describe the software
     */
    private JPanel aboutPanel;

    /**
     * Button which switches the frames shown panel to the third-party-panel
     */
    private JButton thirdPartyButton;

    /**
     * Button which switches the frames shown panel to the license-panel
     */
    private JButton licenseButton;

    /**
     * Panel that contains all components with information about the used third-party contents
     */
    private JPanel thirdPartyPanel;

    /**
     * Button on third-party-panel that is used to go back to about-panel
     */
    private JButton tpBackButton;

    /**
     * TextPane on third-party-panel which contains the third-party licenses as text
     */
    private JTextPane tpLicensesPane;

    /**
     * Button on third-party-panel which opens the third-party-license file
     */
    private JButton tpFileButton;

    /**
     * Panel that contains all components with information about the apps license
     */
    private JPanel licensePanel;

    /**
     * Button on license-panel that is used to go back to about-panel
     */
    private JButton licenseBackButton;

    /**
     * Button on license-panel which opens the software-license file
     */
    private JButton licenseFileButton;

    /**
     * TextPane on third-party-panel which contains the licenses as text
     */
    private JTextPane licensePane;

    /**
     * Button on about-panel that executes an update check on click
     */
    private JButton updateCheckButton;

    /**
     * Label that contains the appname and version
     * (e.g. %APPNAME% - v%VERSION%)
     */
    private JLabel appnameLabel;

    /**
     * About constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
    public About(GUIController controller) {
        super(controller, buildFrameTitle("Über"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        changePanel(AboutPanels.ABOUT);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initializeComponents();
        loadThirdPartyLicense();
        loadLicense();
    }

    /**
     * Initializes the components in this frame
     * (e.g. setting default values & ranges)
     */
    private void initializeComponents()
    {
        appnameLabel.setText(APPNAME + " - v" + VERSION);

        githubButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI(GITHUB_REPO_URL));
            } catch (IOException | URISyntaxException ex) {
                Logger.log(LogType.ERROR, "Cannot open GitHub repository webpage!");
                Logger.logException(ex);
                JOptionPane.showMessageDialog(null, "Fehler beim Aufrufen der Downloadseite!\n\nFür mehr Informationen bitte den Log einsehen!", buildFrameTitle("Fehler"), JOptionPane.ERROR_MESSAGE);
            }
        });

        thirdPartyButton.addActionListener(e -> changePanel(AboutPanels.THIRD_PARTY));
        tpBackButton.addActionListener(e -> changePanel(AboutPanels.ABOUT));
        tpFileButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("resources/licenses/THIRD-PARTY"));
                Logger.log(LogType.INFORMATION, "Opened third-party license file.");
            } catch (IOException ex) {
                Logger.log(LogType.ERROR, "Can not open third-party license file!");
                Logger.logException(ex);
            }
        });

        licenseButton.addActionListener(e -> changePanel(AboutPanels.LICENSE));
        licenseBackButton.addActionListener(e -> changePanel(AboutPanels.ABOUT));
        licenseFileButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File("resources/licenses/LICENSE.rtf"));
                Logger.log(LogType.INFORMATION, "Opened license file.");
            } catch (IOException ex) {
                Logger.log(LogType.ERROR, "Can not open license file!");
                Logger.logException(ex);
            }
        });

        updateCheckButton.addActionListener(e -> new Thread(() -> UpdateChecker.showInformationPane(true)).start());
        closeButton.addActionListener(e -> dispose());
    }

    /**
     * Method to change the current visible panel
     * @param e Element of AboutPanel Enum
     */
    private void changePanel(AboutPanels e)
    {
        aboutPanel.setVisible(false);
        thirdPartyPanel.setVisible(false);
        licensePanel.setVisible(false);

        if (e == AboutPanels.ABOUT) {
            aboutPanel.setVisible(true);
            setTitle(buildFrameTitle("Über"));
            setSize(new Dimension(500, 375));
        }

        if (e == AboutPanels.THIRD_PARTY) {
            thirdPartyPanel.setVisible(true);
            setTitle(buildFrameTitle("Drittanbieterlizenzen"));
            setSize(new Dimension(650, 575));
        }

        if (e == AboutPanels.LICENSE) {
            licensePanel.setVisible(true);
            setTitle(buildFrameTitle("Lizenz"));
            setSize(new Dimension(650, 575));
        }
    }

    /**
     * Load the third-party-license from file into textpane
     */
    private void loadThirdPartyLicense()
    {
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("resources/licenses/THIRD-PARTY"));
                String line = reader.readLine();
                while (line !=null) {
                    tpLicensesPane.setText(tpLicensesPane.getText() + line + "\n");
                    line = reader.readLine();
                }
                tpLicensesPane.setCaretPosition(0);
            } catch (IOException e) {
                tpLicensesPane.setText("Licenses could not be loaded!");
                Logger.log(LogType.ERROR, "Third-Party licenses cannot be loaded from file!");
                Logger.logException(e);
            }
        }).start();
    }

    /**
     * Load the license from file into textpane
     */
    private void loadLicense()
    {
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("resources/licenses/LICENSE"));
                String line = reader.readLine();
                while (line !=null) {
                    licensePane.setText(licensePane.getText() + line + "\n");
                    line = reader.readLine();
                }
                licensePane.setCaretPosition(0);
            } catch (IOException e) {
                licensePane.setText("License could not be loaded!");
                Logger.log(LogType.ERROR, "License cannot be loaded from file!");
                Logger.logException(e);
            }
        }).start();
    }
}
