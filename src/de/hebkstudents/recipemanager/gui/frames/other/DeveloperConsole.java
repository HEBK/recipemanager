package de.hebkstudents.recipemanager.gui.frames.other;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;

public class DeveloperConsole extends AppFrame {
    private JPanel root;
    private JTextPane consolePane;
    private JButton closeConsoleButton;
    private JScrollPane consoleScrollPane;
    private JTextField commandTextfield;
    private JButton executeButton;
    private JButton openLogfileDirButton;
    private JButton consoleLogUpdateButton;
    private JButton openLogfileButton;

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

        commandTextfield.requestFocus();

        openLogfileButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(Logger.getLogger().getLogfile());
                Logger.log(LogType.INFORMATION, "Opened logfile for current runtime '" + Logger.getLogger().getLogfileName() + "'.");
            } catch (IOException ex) {
                Logger.log(LogType.ERROR, "Can not open logfile!");
                Logger.logException(ex);
            }
        });

        executeButton.addActionListener(e -> runCommand());
        commandTextfield.addActionListener(e -> runCommand());
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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occoured!\n\n" + e);
            dispose();
        }
    }

    private void runCommand()
    {
        String commandInput = commandTextfield.getText();
        String[] parsedCommand = commandInput.split(" ");

        if (commandInput.isEmpty() || commandInput.charAt(0) == ' ') { commandTextfield.requestFocus(); return; }

        executeButton.setEnabled(false);
        commandTextfield.setEnabled(false);

        Thread commandThread = new Thread(() -> {
            Logger.log(new LogType("DEVELOPER", Color.GRAY), "User has executed command '" + commandInput + "'");
            consoleLogUpdate();
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}

            switch (parsedCommand[0].toLowerCase()) {
                case "help":
                    break;
                case "version":
                    Logger.log(LogType.INFORMATION, "You're running " + APPNAME + " v" + VERSION);
                    break;
                case "exit":
                    if (parsedCommand.length > 1 && parsedCommand[1].equalsIgnoreCase("confirm")) RecipeManager.shutdownApp(0);
                    Logger.log(LogType.INFORMATION, "Please type 'exit confirm' to exit this application!");
                    break;
                case "ingredient":
                    if (parsedCommand.length == 3) {
                        if (parsedCommand[1].equalsIgnoreCase("delete")) {
                            try {
                                int ingredientID = Integer.parseInt(parsedCommand[2]);
                                IngredientController.deleteIngredient(ingredientID);
                            } catch (NumberFormatException e) {
                                Logger.log(LogType.ERROR, "IngredientID must be type of integer!");
                            } catch (IngredientNotFoundException e) {
                                Logger.log(LogType.ERROR, "Ingredient not found!");
                            }
                            break;
                        }
                    }
                    Logger.log(LogType.INFORMATION, "Command syntax: ingredient <add|delete|modify> <ID>");
                    break;
                case "close":
                    Logger.log(LogType.INFORMATION, "Closing DevConsole frame...");
                    dispose();
                    break;
                default:
                    Logger.log(LogType.ERROR, "The command '" + parsedCommand[0] + "' does not exist!");
            }

            // Update console output
            consoleLogUpdate();

            // Reset inputs
            commandTextfield.setText(null);
            executeButton.setEnabled(true);
            commandTextfield.setEnabled(true);
            commandTextfield.requestFocus();
        });

        // Start command execution
        commandThread.start();
    }

}
