package de.hebkstudents.recipemanager;

import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.util.Date;

public class RecipeManager {

    public static void main(String[] args) {
	    System.out.println("Hello World!");
        initLogger();
        Logger.log(LogType.INFORMATION, "Hruren");
    }

    private static void initLogger() {
        Logger logger = new Logger("RecipeManager", new Date(), "resources/logs");
        logger.load();
    }
}
