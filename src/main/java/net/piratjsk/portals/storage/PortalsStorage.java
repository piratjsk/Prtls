package net.piratjsk.portals.storage;

import net.piratjsk.portals.Portal;

import java.util.List;

public interface PortalsStorage {

    List<Portal> loadPortals();

    void savePortals(final List<Portal> portals);

}
