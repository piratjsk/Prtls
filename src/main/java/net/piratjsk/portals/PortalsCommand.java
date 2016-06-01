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
        if(!cmd.getName().equalsIgnoreCase("portals")) return false;
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Portals.activePortalsMsg.replaceAll("%amount%", String.valueOf(manager.getPortals().size()))));
        return true;
    }

}
