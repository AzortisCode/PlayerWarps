package com.azortis.playerwarps.impl.inventory;

import com.azortis.playerwarps.impl.inventory.menu.PaginatedMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.Plugin;

public class InventoryListener implements Listener {
    private static InventoryListener listener;

    private InventoryListener(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static InventoryListener initialize(Plugin plugin) {
        if (listener == null) listener = new InventoryListener(plugin);
        return listener;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getClickedInventory().getHolder() != null
                && event.getClickedInventory().getHolder() instanceof IMenu && event.getCurrentItem() != null
                && event.getCurrentItem().getType() != Material.AIR) {
            IMenu menu = (IMenu) event.getClickedInventory().getHolder();
            menu.getItem(event.getSlot()).useAction(event);

        }

    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof IMenu) {
            IMenu menu = (IMenu) event.getInventory().getHolder();
            menu.getOpenCondition().forEach(condition -> condition.test(event));
            menu.doConditions(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof IMenu) {
            IMenu menu = (IMenu) event.getInventory().getHolder();
            menu.getCloseCondition().forEach(condition -> condition.test(event));
            if (menu instanceof IPaginatedMenu) {
                ((IPaginatedMenu) menu).onInventoryClose((Player) event.getPlayer());
            }
        }
    }

}
