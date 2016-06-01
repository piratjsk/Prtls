package net.piratjsk.portals;

import java.util.List;

import net.piratjsk.portals.storage.PortalsStorage;
import net.piratjsk.portals.storage.YAMLPortalsStorage;
import org.bukkit.Location;

public class PortalsManager {

    private List<Portal> portals;
    private final PortalsStorage storage;

    public PortalsManager(final Portals plugin) {
        final String storageType = plugin.getConfig().getString("storage");
        // TODO: more storage types
        if (storageType.equalsIgnoreCase("yaml")) {
            this.storage = new YAMLPortalsStorage();
            this.portals = storage.loadPortals();
        } else
            this.storage = null;
    }

    public boolean hasPortal(final String name) {
        for (final Portal portal : portals)
            if (portal.getName().equals(name))
                return true;
        return false;
    }

    public boolean hasPortal(final Location location) {
        for (final Portal portal : portals)
            if (portal.getLocation().equals(location))
                return true;
        return false;
    }

    public boolean hasPortal(final Portal portal) {
        for (final Portal p : portals)
            if (p.getName().equals(portal.getName()) && p.getLocation().equals(portal.getLocation()))
                return true;
        return false;
    }

    public Portal getPortal(final String name) {
        for (final Portal portal : portals)
            if (portal.getName().equals(name))
                return portal;
        return null;
    }

    public Portal getPortal(final Location location) {
        for (final Portal portal : portals)
            if (portal.getLocation().equals(location))
                return portal;
        return null;
    }

    public void addPortal(final Portal portal) {
        if (!hasPortal(portal))
            this.portals.add(portal);
    }

    public void removePortal(final String name) {
        for (final Portal portal : portals)
            if (portal.getName().equals(name))
                this.portals.remove(portal);
    }

    public void removePortal(final Location location) {
        Portal toRemove=null;
        for (final Portal portal : portals)
            if (portal.getLocation().equals(location))
                toRemove=portal;
        if (toRemove!=null)
            portals.remove(toRemove);
    }

    public List<Portal> getPortals() {
        return portals;
    }

    public void save() {
        if (this.storage==null) return;
        storage.savePortals(this.portals);
    }

}
