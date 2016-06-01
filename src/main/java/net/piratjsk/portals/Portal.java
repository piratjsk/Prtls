package net.piratjsk.portals;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Portal {

    private String name;
    private Location location;

    public Portal(final String name, final Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(final Location location) {
        this.location = location;
    }

    public Location getDestination() {
        return location.clone().add(0.5, 1, 0.5);
    }

    public void teleport(final Player player) {
        final Location dest = this.getDestination();
        dest.setPitch(player.getLocation().getPitch());
        dest.setYaw(player.getLocation().getYaw());
        player.teleport(dest);
    }

}
