package net.piratjsk.portals;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class PortalsCommand implements CommandExecutor {

    private final PortalsManager manager;

    PortalsCommand(final Portals plugin) {
        this.manager = plugin.getPortalsManager();
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (sender.hasPermission("portals.load") && args.length > 0 && args[0].equalsIgnoreCase("load")) {
            manager.load();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[Portals] &rLoaded!"));
            return true;
        }
        if (sender.hasPermission("portals.save") && args.length > 0 && args[0].equalsIgnoreCase("save")) {
            manager.save();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9[Portals] &rSaved!"));
            return true;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Portals.activePortalsMsg.replaceAll("%amount%", String.valueOf(manager.getPortals().size()))));
        return true;
    }

}
