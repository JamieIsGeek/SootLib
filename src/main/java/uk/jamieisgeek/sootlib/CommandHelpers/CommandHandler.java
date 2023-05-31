package uk.jamieisgeek.sootlib.CommandHelpers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public abstract class CommandHandler implements CommandExecutor {
    private final CommandInfo commandInfo;

    public CommandHandler() {
        this.commandInfo = getClass().getDeclaredAnnotation(CommandInfo.class);
        Objects.requireNonNull(commandInfo, "CommandInfo annotation is required for CommandHandler");
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!commandInfo.permission().isEmpty() && !sender.hasPermission(commandInfo.permission())) {
            sender.sendMessage("§cYou do not have permission to use this command.");
            return true;
        }

        if (commandInfo.requiresPlayer()) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cYou must be a player to use this command.");
                return true;
            }
            execute((Player) sender, args);
            return true;
        }

        execute(sender, args);
        return true;
    }

    /**
     * @param player The player that executed the command
     * @param args The arguments that were passed to the command
     */
    public void execute(Player player, String[] args) {
    }

    /**
     * @param sender The sender that executed the command (Possibly console)
     * @param args The arguments that were passed to the command
     */
    public void execute(CommandSender sender, String[] args) {
    }
}
