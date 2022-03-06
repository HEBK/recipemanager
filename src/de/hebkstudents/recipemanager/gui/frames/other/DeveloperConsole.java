package de.hebkstudents.recipemanager.gui.frames.other;

import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static de.hebkstudents.recipemanager.storage.StaticProperties.DEFAULT_DIMENSION;
import static de.hebkstudents.recipemanager.storage.StaticProperties.STORAGE_PATH;

public class DeveloperConsole extends AppFrame {
    private JPanel root;
    private JTextPane consolePane;
    private JButton closeConsoleButton;
    private JScrollPane consoleScrollPane;
    private JTextField textField1;
    private JButton executeButton;
    private JButton openLogfileDirButton;
    private JButton consoleLogUpdateButton;

    public DeveloperConsole (GUIController controller)
    {
        super(controller, buildFrameTitle("Developer Console"), DEFAULT_DIMENSION, true);
        init();
    }

    @Override
    protected void init() {
        add(root);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        consoleLogUpdater();


        closeConsoleButton.addActionListener(e -> {
            dispose();
        });

        openLogfileDirButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new File(STORAGE_PATH + "/logs"));
                Logger.log(LogType.INFORMATION, "Opened logfile directory '" +STORAGE_PATH + "/logs' in explorer.");
            } catch (IOException exception) {
                Logger.log(LogType.ERROR, "Can not open logfile directory!");
                Logger.logException(exception);
            }
        });

        consoleLogUpdateButton.addActionListener(e -> consoleLogUpdate());

        textField1.requestFocus();
    }


    private void consoleLogUpdater()
    {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable consoleLogUpdate = this::consoleLogUpdate;
        executorService.scheduleAtFixedRate(consoleLogUpdate, 0, 10, TimeUnit.SECONDS);
    }

    private void consoleLogUpdate()
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(Logger.getLogger().getLogfile()));
            String line = reader.readLine();
            consolePane.setText("");
            while (line != null) {
                consolePane.setText(consolePane.getText() + line + "\n");
                line = reader.readLine();
            }
            consolePane.setCaretPosition(consolePane.getDocument().getLength());
        } catch (IOException e) {}
    }










}
