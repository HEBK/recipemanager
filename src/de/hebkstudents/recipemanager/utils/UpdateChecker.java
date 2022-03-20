package de.hebkstudents.recipemanager.utils;

import de.hebkstudents.recipemanager.RecipeManager;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Scanner;

import static de.hebkstudents.recipemanager.storage.AppProperties.*;
import static de.hebkstudents.recipemanager.storage.AppProperties.APPNAME;

/**
 * Class UpdateChecker
 * Manages App-Updates & Update-Searches
 */
public class UpdateChecker {

    /**
     * Temporarily cached latest version string
     */
    private static String cacheVerString;

    /**
     * Gets the latest version string from Update-Server
     * @return Latest version String or 'n/A' if error
     * @throws IOException If its was not possible to establish a connection to the update server
     */
    public static String getLatestVersionString(boolean ignoreCache) throws IOException {
        if (cacheVerString != null && !ignoreCache) return cacheVerString;

        try {
            URL url = new URL(VERSION_STRING_SERVER_URL);
            Scanner scanner = new Scanner(url.openStream());
            String version = scanner.nextLine();
            if (version.isEmpty() || version.equals(" ") || version.equals("\n")) version = "n/A";

            cacheVerString = version;
            return version;
        } catch (IOException e) {
            Logger.log(LogType.ERROR, "Cannot get last version string from server! (IOException)");
            throw e;
        }
    }

    /**
     * Checks whether an update is available or not
     * @return true if an update is available
     */
    public static boolean updateAvailable()
    {
        try {
            return !getLatestVersionString(true).equals(VERSION);
        } catch (IOException e) {
            Logger.logException(e);
        }
        return false;
    }

    /**
     * Shows Update information panes
     * @param showUpToDateMessage If true -> Shows a pane if software is up-to-date
     */
    public static void showInformationPane(boolean showUpToDateMessage)
    {
        if (updateAvailable()) {
            try {
                if (JOptionPane.showConfirmDialog(null, "Version "+ getLatestVersionString(false) + " von " + APPNAME + " ist nun verfügbar!\n\nMöchten Sie diese jetzt herunterladen?\n\nIhre Version: " + VERSION, APPNAME + " | Update verfügbar!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Der Download wird gestartet, sobald Sie auf OK klicken oder dieses Fenster schließen.\nDies kann je nach Internetverbindung eine längere Zeit in Anspruch nehmen.\n\nSie werden benachrichtigt, wenn das Update bereit ist.", APPNAME + " | Update wird heruntergeladen ...", JOptionPane.INFORMATION_MESSAGE);
                    downloadLatestInstaller(true);
                }
            } catch (IOException e) {
                Logger.log(LogType.ERROR, "Cannot check for updates! (IOException) -> Maybe no connection to update server");
                Logger.logException(e);
            }
        } else if (showUpToDateMessage) new Thread(() -> JOptionPane.showMessageDialog(null, "Ihre " + APPNAME + " Version ist aktuell!\n\nIhre Version: " + VERSION, APPNAME + " | Update Checker", JOptionPane.INFORMATION_MESSAGE)).start();
    }

    /**
     * Logs the update check procedure in the logfile
     */
    public static void logUpdateCheck()
    {
        if (updateAvailable()) {
            try {
                Logger.log(LogType.INFORMATION, "Update checker: Version " + getLatestVersionString(false) + " is now available!");
            } catch (IOException e) {
                Logger.log(LogType.ERROR, "Unable to check for Updates! (IOException) -> Maybe no connection to update server");
                Logger.logException(e);
            }
            return;
        }
        Logger.log(LogType.INFORMATION, "You're running the latest version of " + APPNAME + "!");
    }

    /**
     * Downloads the latest installer from Update-Server
     * @param showExecutePane Asks if the update should be executed
     */
    public static void downloadLatestInstaller(boolean showExecutePane)
    {
        new Thread(() -> {
            try {
                URL latestInstallerURL = new URL("https://cdn.sarpex.eu/software/hebk/recipemanager/dl/download.php?type=exe");
                URLConnection connection = latestInstallerURL.openConnection();

                String filename;
                String fieldVals = connection.getHeaderField("Content-Disposition");
                if (fieldVals == null || !fieldVals.contains("filename=\"")) {
                    filename = "recipemanager-latest-windows-x64.exe";
                } else {
                    filename = fieldVals.substring(fieldVals.indexOf("filename=\"") + 10, fieldVals.length() - 1);
                }

                ReadableByteChannel rbc = Channels.newChannel(latestInstallerURL.openStream());
                FileOutputStream stream = new FileOutputStream(STORAGE_PATH + "/cache/" + filename);
                stream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                stream.close();

                if (showExecutePane) {
                    if (JOptionPane.showConfirmDialog(null, "Das Update wurde erfolgreich heruntergeladen!\n\nJetzt aktualisieren?", APPNAME + " | Update heruntergeladen", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        File installer = new File(STORAGE_PATH + "/cache/" + filename);

                        if (installer.exists()) {
                            RecipeManager.getManager().getController().stop(false);
                            Desktop.getDesktop().open(new File(STORAGE_PATH + "/cache/" + filename));
                            RecipeManager.shutdownApp(1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Der Installer konnte nicht ausgeführt werden!", APPNAME + " | Fehler", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (IOException e) {
                Logger.log(LogType.ERROR, "An error occurred while downloading update or the user has aborted the setup!");
                Logger.logException(e);
                new Thread(() -> JOptionPane.showMessageDialog(null, "Es trat ein fehler während der Aktualisierung auf!\n\n" + e.getMessage(), APPNAME + " | Fehler", JOptionPane.ERROR_MESSAGE)).start();
                RecipeManager.getManager().getController().run();
            }
        }).start();
    }
}
