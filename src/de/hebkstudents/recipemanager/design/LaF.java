package de.hebkstudents.recipemanager.design;

/**
 * Small object to manage the link between Name and Class of LookAndFeels
 */
public class LaF {

    /**
     * Class name of lookandfeel
     */
    private final String className;

    /**
     * Name of lookandfeel
     */
    private final String name;

    /**
     * LaF Constructor
     * @param className Class name of lookandfeel
     * @param name Name of lookandfeel
     */
    public LaF(String className, String name)
    {
        this.className = className;
        this.name = name;
    }

    /**
     * Gets the classname of the lookandfeel
     * @return classname of the lookandfeel
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets the name of lookandfeel
     * @return name of lookandfeel
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
