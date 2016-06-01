package net.piratjsk.portals;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class PortalsListener implements Listener {

    private final PortalsManager manager;

    PortalsListener(Portals plugin) {
        this.manager = plugin.getPortalsManager();
    }

    @EventHandler
    public void onPortalModify(final InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        if (event.getInventory().getType().equals(InventoryType.DISPENSER)) {
            final Location loc = event.getInventory().getLocation();
            if(Portals.isValidPortal(loc)) {
                final String name = Portals.getPortalName(event.getInventory());
                if (manager.hasPortal(name)) {
                    if (manager.hasPortal(loc))
                        if (manager.getPortal(loc).getName().equals(name))
                            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE ,1,1);
                        else
                            for (int i=0;i<9;++i)
                                ((Dispenser)loc.getBlock().getState()).dispense();
                    else
                        for (int i=0;i<9;++i)
                            ((Dispenser)loc.getBlock().getState()).dispense();
                } else if (manager.hasPortal(loc)) {
                    manager.getPortal(event.getInventory().getLocation()).setName(name);
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE ,1,1);
                } else {
                    player.getWorld().playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE ,1,1);
                    manager.addPortal(new Portal(name,loc));
                }
            } else if (manager.hasPortal(loc)) {
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY,1,1);
                manager.removePortal(loc);
            }

        }
    }

    @EventHandler
    public void onPortalBreak(final BlockBreakEvent event) {
        if (manager.hasPortal(event.getBlock().getLocation())) {
            event.getBlock().getLocation().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_ANVIL_DESTROY,1,1);
            manager.removePortal(event.getBlock().getLocation());
        }
    }

    @EventHandler
    public void onPortalUse(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack ticket = player.getItemInHand();
        if (ticket.getType().equals(Material.PAPER) && ticket.getItemMeta().hasDisplayName()) {
            if (player.getInventory().contains(Material.GOLD_INGOT)) {
                final Location loc = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation();
                if (manager.hasPortal(loc)) {
                    final String name = ticket.getItemMeta().getDisplayName();
                    if (manager.hasPortal(name)) {
                        Portal portal = manager.getPortal(name);
                        if (loc.equals(portal.getLocation())) return;
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 1));
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT,1,1);
                        portal.teleport(player);
                        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT,1,1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPortalDispense(final BlockDispenseEvent event) {
        final Location loc = event.getBlock().getLocation();
        if (manager.hasPortal(loc))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPortalPush(final BlockPistonExtendEvent event) {
        for (Block block : event.getBlocks())
            if (manager.hasPortal(block.getLocation()))
                event.setCancelled(true);
    }

    @EventHandler
    public void onPortalPull(final BlockPistonRetractEvent event) {
        for (Block block : event.getBlocks())
            if (manager.hasPortal(block.getLocation()))
                event.setCancelled(true);
    }

}
