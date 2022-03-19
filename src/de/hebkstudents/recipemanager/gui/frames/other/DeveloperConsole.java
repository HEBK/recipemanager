package de.hebkstudents.recipemanager.gui.frames.other;

import de.hebkstudents.recipemanager.RecipeManager;
import de.hebkstudents.recipemanager.exception.IngredientNotFoundException;
import de.hebkstudents.recipemanager.gui.GUIController;
import de.hebkstudents.recipemanager.gui.frametype.AppFrame;
import de.hebkstudents.recipemanager.ingredient.IngredientController;
import de.hebkstudents.recipemanager.utils.UpdateChecker;
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

/**
 * DeveloperConsole class
 * Class for the frame that is used to take a look into the app log or to execute commands
 */
public class DeveloperConsole extends AppFrame {

    /**
     * Root panel that is being added to the frame
     */
    private JPanel root;

    /**
     * TextPane that shows the console output
     */
    private JTextPane consolePane;

    /**
     * Button that closes the console
     */
    private JButton closeConsoleButton;

    /**
     * Textfield to type in commands
     */
    private JTextField commandTextfield;

    /**
     * Button to execute commands
     */
    private JButton executeButton;

    /**
     * Button to open the directory for logfiles
     */
    private JButton openLogfileDirButton;

    /**
     * Button to update the console output
     */
    private JButton consoleLogUpdateButton;

    /**
     * Button to open the current logfile in editor
     */
    private JButton openLogfileButton;


    /**
     * DeveloperConsole constructor. Initializes the frame from its superclass.
     * @param controller GUIController which is used to manage the frame.
     */
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

    /**
     * Initializes a new Interval that runs a thread to update the console output
     */
    private void consoleLogUpdater()
    {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable consoleLogUpdate = this::consoleLogUpdate;
        executorService.scheduleAtFixedRate(consoleLogUpdate, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * Updates the console output
     */
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

    /**
     * Runs a command from the textfield
     */
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
                    Logger.log(LogType.INFORMATION, "The following commands exist:\n" +
                            "- version      -> Shows the current installed app version\n" +
                            "- exit         -> Shuts down the app after confirmation\n" +
                            "- ingredient   -> Manages ingredients\n" +
                            "- config       -> Maneges the app configuration\n" +
                            "- close        -> Closes this window\n" +
                            "- restart      -> Restarts the apps GUI\n" +
                            "- update       -> Executes an app update");
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
                case "config":
                    if (parsedCommand.length == 2) {
                        if (parsedCommand[1].equalsIgnoreCase("list")){
                            Logger.log(LogType.INFORMATION, "Current config keys: " + Arrays.toString(DEFAULT_CONFIG.getExistingProperties()));
                            break;
                        }
                    }
                    if (parsedCommand.length == 3) {
                        if (parsedCommand[1].equalsIgnoreCase("get")) {
                            Logger.log(LogType.INFORMATION, "The current config value for '" + parsedCommand[2] + "' is: " + DEFAULT_CONFIG.read(parsedCommand[2]));
                            break;
                        }
                    }
                    if (parsedCommand.length == 4) {
                        if (parsedCommand[1].equalsIgnoreCase("set")) {
                            if (DEFAULT_CONFIG.write(parsedCommand[2], parsedCommand[3])) {
                                Logger.log(LogType.INFORMATION, "The config value for '" + parsedCommand[2] + "' has been set to: " + parsedCommand[3]);
                            } else {
                                Logger.log(LogType.ERROR, "The config value for '" + parsedCommand[2] + "' can not be set!");
                            }
                            break;
                        }
                    }
                    Logger.log(LogType.INFORMATION, "Command syntax: config <list|get|set> (property) (value)");
                    break;
                case "close":
                    Logger.log(LogType.INFORMATION, "Closing DevConsole frame...");
                    dispose();
                    break;
                case "restart":
                    getController().stop(true);
                    break;
                case "update":
                    if (parsedCommand.length == 2) {
                        if (parsedCommand[1].equalsIgnoreCase("check")) {
                            UpdateChecker.logUpdateCheck();
                            break;
                        }
                        if (parsedCommand[1].equalsIgnoreCase("run")) {
                            Logger.log(LogType.INFORMATION, "Checking for new update ...");
                            if (UpdateChecker.updateAvailable()) {
                                try {
                                    Logger.log(LogType.INFORMATION, "Downloading update version " + UpdateChecker.getLatestVersionString() + "...");
                                    UpdateChecker.downloadLatestInstaller(true);
                                } catch (IOException e) {
                                    Logger.log(LogType.INFORMATION, "Cannot download update! (VERSION_STRING_NOT_AVAILABLE)");
                                    Logger.logException(e);
                                }
                            } else {
                                Logger.log(LogType.INFORMATION, "No new update available!");
                            }
                            break;
                        }
                    }
                    Logger.log(LogType.INFORMATION, "Command syntax: update <check|run>");
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
