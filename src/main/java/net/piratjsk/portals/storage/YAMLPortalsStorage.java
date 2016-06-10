package net.piratjsk.portals.storage;

import net.piratjsk.portals.Portal;
import net.piratjsk.portals.Portals;
import org.bukkit.*;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class YAMLPortalsStorage implements PortalsStorage {

    public List<Portal> loadPortals() {
        final List<Portal> portals = new ArrayList<Portal>();
        final FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Portals.getInstance().getDataFolder(), "portals.yml"));
        if (config.getKeys(false).size()==0) return portals;
        final Set<String> ids = config.getConfigurationSection("portals").getKeys(false);
        for (final String id : ids) {
            final World world = Bukkit.getWorld(config.getString("portals." + id + ".world"));
            final double x = config.getDouble("portals." + id + ".x");
            final double y = config.getDouble("portals." + id + ".y");
            final double z = config.getDouble("portals." + id + ".z");
            final Location location = new Location(world, x, y, z);
            final String name = config.getString("portals." + id + ".name");
            portals.add(new Portal(name, location));
        }
        return portals;
    }

    public void savePortals(final List<Portal> portals) {
        final File configFile = new File(Portals.getInstance().getDataFolder(), "portals.yml");
        final FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (portals.size()==0)
            config.set("portals",null);
        int id = 0;
        for (final Portal portal : portals) {
            config.set("portals."+id+".name",portal.getName());
            config.set("portals."+id+".world",portal.getLocation().getWorld().getName());
            config.set("portals."+id+".x",portal.getLocation().getX());
            config.set("portals."+id+".y",portal.getLocation().getY());
            config.set("portals."+id+".z",portal.getLocation().getZ());
            ++id;
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
