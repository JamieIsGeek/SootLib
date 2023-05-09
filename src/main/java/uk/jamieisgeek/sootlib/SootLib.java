package uk.jamieisgeek.sootlib;

import org.bukkit.plugin.java.JavaPlugin;
import uk.jamieisgeek.sootlib.Misc.TextManager;
import uk.jamieisgeek.sootlib.Storage.Database;

public final class SootLib {
    public final JavaPlugin provider;
    public final TextManager textManager;
    private static SootLib instance = null;

    /**
     * @param provider The plugin that is using SootLib
     */
    public SootLib(JavaPlugin provider) {
        this.provider = provider;
        this.textManager = new TextManager();
        instance = this;
    }

    /**
     * @param address The address of the database
     * @param database The name of the database
     * @param username The username for the database
     * @param password The password for the database
     * @param port The port for the database
     * @return The database
     * @throws ClassNotFoundException If the database driver is not found
     */
    public Database createDatabase(String address, String database, String username, String password, String port) throws ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        return new Database(address, database, username, password, port);
    }

    /**
     * @return The SootLib instance
     */
    public static SootLib getSootLib() {
        return instance;
    }

    /**
     * @return The TextManager
     */
    public TextManager getTextManager() {
        return textManager;
    }
}
