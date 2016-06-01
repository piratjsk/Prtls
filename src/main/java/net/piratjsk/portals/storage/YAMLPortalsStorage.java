package net.piratjsk.portals.storage;

import net.piratjsk.portals.Portal;
import net.piratjsk.portals.Portals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class YAMLPortalsStorage implements PortalsStorage {

    public List<Portal> loadPortals() {
        final List<Portal> portals = new ArrayList<Portal>();
        final FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Portals.getInstance().getDataFolder(), "portals.yml"));
        if (config.getKeys(false).size()==0) return portals;
        final Set<String> names = config.getConfigurationSection("portals").getKeys(false);
        for (final String name : names) {
            final World world = Bukkit.getWorld(config.getString("portals." + name + ".world"));
            final double x = config.getDouble("portals." + name + ".x");
            final double y = config.getDouble("portals." + name + ".y");
            final double z = config.getDouble("portals." + name + ".z");
            final Location location = new Location(world, x, y, z);
            portals.add(new Portal(name, location));
        }
        return portals;
    }

    public void savePortals(final List<Portal> portals) {
        final File configFile = new File(Portals.getInstance().getDataFolder(), "portals.yml");
        final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (portals.size()==0)
            config.set("portals",null);
        for (final Portal portal : portals) {
            String name = portal.getName();
            config.set("portals."+name+".world",portal.getLocation().getWorld().getName());
            config.set("portals."+name+".x",portal.getLocation().getX());
            config.set("portals."+name+".y",portal.getLocation().getY());
            config.set("portals."+name+".z",portal.getLocation().getZ());
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
