package de.hebkstudents.recipemanager;

import com.bulenkov.darcula.DarculaLaf;
import de.hebkstudents.recipemanager.storage.StaticProperties;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import javax.swing.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class RecipeManager {

    public static void main(String[] args) {

        loadLookAndFeel(new DarculaLaf());

        initLogger();



    }

    private static void initLogger() {
        Logger logger = new Logger("RecipeManager", new Date(), StaticProperties.STORAGE_PATH + "/logs");
        logger.load();
        Logger.log(LogType.SYSTEM, "Logger initialization completed!");
        Logger.log(LogType.INFORMATION, StaticProperties.APPNAME + " - Version: " + StaticProperties.VERSION);


    }



    private static void loadLookAndFeel(LookAndFeel l) {
        try {
            UIManager.setLookAndFeel(l);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null, "We're unable to load the LookAndFeel for this Application.\nPlease contact the developer using the email address below.\n\ninfo@sarpex.eu", "Critical application error!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private static void registerDrivers(Driver[] drivers)
    {
        if (Logger.isLoaded()) {
            Logger.log(LogType.SYSTEM, "Loading " + drivers.length + " drivers ...");
        }
        try {
            for (Driver d: drivers) {
                DriverManager.registerDriver(d);
            }
            Logger.log(LogType.SYSTEM, "All drivers were loaded successfully!");
        } catch (SQLException e) {
            if (Logger.isLoaded()) {
                Logger.log(LogType.ERROR, "One or more drivers could not be loaded!\n" + e.getMessage());
            }
        }
    }
}
