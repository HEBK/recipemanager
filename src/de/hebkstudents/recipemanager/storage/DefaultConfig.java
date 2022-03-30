package de.hebkstudents.recipemanager.storage;

import de.hebkstudents.recipemanager.exception.InvalidMethodParameterException;
import eu.cr4zyfl1x.logger.LogType;
import eu.cr4zyfl1x.logger.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * Class DefaultConfig
 * Class that manages the access to the defined properties file
 */
public class DefaultConfig {

    /**
     * Property Object that (has) loaded the configuration file;
     */
    private final Properties properties = new Properties();

    /**
     * File object of the configuration file
     */
    private final File configFile;

    /**
     * Description of the config
     */
    private final String description;


    /**
     * Constructs a new Configuration object
     * @param filepath Path to the configuration file
     * @param description Description of the config
     * @throws InvalidMethodParameterException If param file is null
     */
    public DefaultConfig(String filepath, String description) throws InvalidMethodParameterException {
        if (filepath == null) throw new InvalidMethodParameterException("null is an invalid parameter for filepath::String!");
        configFile = new File(filepath);
        this.description = description;
    }

    /**
     * Constructs a new Configuration object
     * @param file Configuration File Object
     * @param description Description of the config
     * @throws InvalidMethodParameterException If param file is null
     */
    public DefaultConfig(File file, String description) throws InvalidMethodParameterException {
        if (file == null) throw new InvalidMethodParameterException("null is an invalid parameter for file::File!");
        configFile = file;
        this.description = description;
    }

    /**
     * Checks if the config file exists
     * @return true, if file exists
     */
    public boolean exists()
    {
        return configFile.exists();
    }

    /**
     * Creates the config file
     * @return true if the config file has been created
     */
    public boolean createConfigFile()
    {
        if (exists()) {
            Logger.log(LogType.WARNING, "The config file already exists! Aborted.");
            return false;
        }
        try {
            return configFile.createNewFile();
        } catch (IOException e) {
            Logger.log(LogType.ERROR, "Cannot create config file because of IOException! (Maybe the directory does not exist!)");
            Logger.logException(e);
            return false;
        }
    }

    /**
     * Writes/Changes a property to/of the config
     * @param property Property
     * @param value Value
     * @return true if property has been created/changed
     */
    public boolean write(String property, String value)
    {
        if (!exists()) {
            Logger.log(LogType.ERROR, "Cannot write to config file! Config file does not exist!");
            return false;
        }

        try {
            properties.load(new FileReader(configFile));
            properties.setProperty(property, value);
            properties.store(new FileWriter(configFile), description);
            return true;
        } catch (IOException e) {
            Logger.log(LogType.ERROR, "Cannot write to config file because of IOException!");
            Logger.logException(e);
        }
        return false;
    }

    /**
     * Reads a property from the config
     * @param property Property to read
     * @return Value of property. If an error occurred or the property does not exist the value will be null
     */
    public String read(String property)
    {
        if (!exists()) {
            Logger.log(LogType.ERROR, "Cannot read config file! Config file does not exist!");
            return null;
        }

        try {
            properties.load(new FileReader(configFile));
            return properties.getProperty(property);
        } catch (IOException exception) {
            Logger.log(LogType.ERROR, "Cannot read from config file because of IOException!");
            Logger.logException(exception);
        }
        return null;
    }

    /**
     * Gets the description of the config
     * @return Description as String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets all existing property keys in an Array
     * @return Array with property keys
     */
    public String[] getExistingProperties()
    {
        try {
            properties.load(new FileReader(configFile));
            String[] propertyArray = new String[properties.keySet().size()];
            Object[] pps = properties.keySet().toArray(new Object[0]);

            for (int i = 0; i < propertyArray.length; i++) {
                propertyArray[i] = pps[i].toString();
            }
            return propertyArray;
        } catch (IOException e) {
            Logger.logException(e);
        }
        return new String[]{};
    }
}
