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

public class About extends AppFrame {
    private JPanel root;
    private JButton githubButton;
    private JButton closeButton;
    private JPanel aboutPanel;
    private JButton thirdPartyButton;
    private JButton licenseButton;
    private JPanel thirdPartyPanel;
    private JButton tpBackButton;
    private JTextPane tpLicensesPane;
    private JButton tpFileButton;
    private JPanel licensePanel;
    private JButton licenseBackButton;
    private JButton licenseFileButton;
    private JTextPane licensePane;
    private JButton updateCheckButton;
    private JLabel appnameLabel;

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
