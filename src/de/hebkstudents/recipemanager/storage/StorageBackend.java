package de.hebkstudents.recipemanager.storage;

import java.io.File;
import java.util.ArrayList;

public class StorageBackend {

    /**
     * Storage backend root path
     */
    private String rootPath;

    /**
     * Storage backend sub directories in root path
     */
    private String[] subDirs;

    public StorageBackend(String path, String[] subDirs)
    {
        this.subDirs = subDirs;
        this.rootPath = path;

    }

    public boolean createRootPath()
    {
        File root = new File(rootPath);
        if (!root.exists()) return root.mkdirs();
        return true;
    }

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

    public boolean rootDirExists()
    {
        return new File(rootPath).exists();
    }

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

    public File getRootDirectory()
    {
        return new File(rootPath);
    }

    public File[] getSubDirectories()
    {
        File[] subDirectories = new File[subDirs.length];
        for (int i = 0; i < subDirectories.length; i++) {
            subDirectories[i] = new File(rootPath + "/" + subDirs[i]);
        }
        return subDirectories;

    }
}
