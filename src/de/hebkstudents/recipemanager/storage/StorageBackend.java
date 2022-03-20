package de.hebkstudents.recipemanager.storage;

import java.io.File;
import java.util.ArrayList;

/**
 * Class StorageBackend
 * Manages the directory structure in a defined path
 */
public class StorageBackend {

    /**
     * Storage backend root path
     */
    private String rootPath;

    /**
     * Storage backend subdirectories in root path
     */
    private String[] subDirs;


    /**
     * Constructs a new StorageBackend object
     * @param path Storage backend root path
     * @param subDirs Storage backend subdirectories in root path
     */
    public StorageBackend(String path, String[] subDirs)
    {
        this.subDirs = subDirs;
        this.rootPath = path;
    }

    /**
     * Creates the defined root path if not exists
     * @return true if created or existing
     */
    public boolean createRootPath()
    {
        File root = new File(rootPath);
        if (!root.exists()) return root.mkdirs();
        return true;
    }

    /**
     * Creates the defined subdirectories if not existing
     * @return true if created or existing
     */
    public boolean createDirectories()
    {
        if (subDirs != null) {
            File dir;
            ArrayList<Boolean> status = new ArrayList<>();
            status.add(createRootPath());
            for (String directory: subDirs) {
                directory = rootPath + "/" + directory;
                dir = new File(directory);
                if (!dir.exists()) {
                    status.add(dir.mkdirs());
                }
            }
            for (Boolean aBoolean : status) {
                if (!aBoolean) return false;
            }
        }
        return true;
    }

    /**
     * Checks if the root directory exists
     * @return true if exists
     */
    public boolean rootDirExists()
    {
        return new File(rootPath).exists();
    }

    /**
     * Checks if the subdirectories exist
     * @return true if existing
     */
    public boolean directoriesExist()
    {
        ArrayList<Boolean> status = new ArrayList<>();
        status.add(rootDirExists());
        File dir;
        for (String directory: subDirs) {
            directory = rootPath + "/" + directory;
            dir = new File(directory);
            status.add(dir.exists());
        }
        return status.stream().allMatch(j -> j);
    }

    /**
     * Gets the root directory as File object
     * @return File object of root directory
     */
    public File getRootDirectory()
    {
        return new File(rootPath);
    }

    /**
     * Gets the subdirectories as File object Array
     * @return File object Array of all subdirectories
     */
    public File[] getSubDirectories()
    {
        File[] subDirectories = new File[subDirs.length];
        for (int i = 0; i < subDirectories.length; i++) {
            subDirectories[i] = new File(rootPath + "/" + subDirs[i]);
        }
        return subDirectories;

    }
}
