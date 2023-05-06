package uk.jamieisgeek.sootlib.Registers;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import uk.jamieisgeek.sootlib.SootLib;

import java.lang.reflect.InvocationTargetException;

public class EventRegisterer {
    private final JavaPlugin plugin;
    private final String packageName;
    private final String folderName;

    public EventRegisterer(String folderName) {
        this.plugin = SootLib.getSootLib().provider;
        this.packageName = plugin.getClass().getPackage().getName();
        this.folderName = folderName;
    }

    public void registerEvents() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(Class<?> clazz : new Reflections(packageName + "." + folderName)
                .getSubTypesOf(Listener.class)) {
            Listener listener = (Listener) clazz.getDeclaredConstructor().newInstance();
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
