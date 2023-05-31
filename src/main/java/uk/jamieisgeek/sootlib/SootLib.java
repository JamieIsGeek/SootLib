package uk.jamieisgeek.sootlib;

import org.bukkit.plugin.java.JavaPlugin;
import uk.jamieisgeek.sootlib.Misc.TextManager;

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
