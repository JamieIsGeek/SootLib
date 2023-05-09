package uk.jamieisgeek.sootlib.Registers;

import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import uk.jamieisgeek.sootlib.CommandHelpers.CommandHandler;
import uk.jamieisgeek.sootlib.SootLib;

import java.lang.reflect.InvocationTargetException;

public class CommandRegisterer {
    private final JavaPlugin plugin;
    private final String packageName;
    private final String folderName;

    /**
     * @param folderName The name of the folder that the Command classes are in.
     */
    public CommandRegisterer(String folderName) {
        this.plugin = SootLib.getSootLib().provider;
        this.packageName = plugin.getClass().getPackage().getName();
        this.folderName = folderName;
    }

    public void registerCommands() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        for(Class<? extends CommandHandler> clazz: new Reflections(packageName + "." + folderName)
                .getSubTypesOf(CommandHandler.class)) {
            CommandHandler commandHandler = clazz.getDeclaredConstructor().newInstance();
            plugin.getCommand(commandHandler.getCommandInfo().name()).setExecutor(commandHandler);

            plugin.getLogger().info("Registered command: " + commandHandler.getCommandInfo().name());
        }
    }
}
