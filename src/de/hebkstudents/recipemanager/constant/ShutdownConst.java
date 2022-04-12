package de.hebkstudents.recipemanager.constant;

/**
 * Integer constant values for app shutdown codes
 */
public class ShutdownConst {

    /**
     * Critical force shutdown status code
     * (-> Does not shutdown other services! (e.g. Database, Logger, etc.))
     */
    public static final int CRITICAL = -1;

    /**
     * App update status code
     * (-> Applied when app shuts down to execute update package)
     */
    public static final int UPDATE = -2;
}
