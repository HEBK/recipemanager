package de.hebkstudents.recipemanager.design;

public class LaF {

    private final String className;
    private final String name;

    public LaF(String className, String name)
    {
        this.className = className;
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
