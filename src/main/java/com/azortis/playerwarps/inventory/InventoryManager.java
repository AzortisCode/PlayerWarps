package com.azortis.playerwarps.inventory;

import com.azortis.playerwarps.PlayerWarps;
import com.azortis.playerwarps.command.Injected;
import com.azortis.playerwarps.impl.inventory.IMenu;
import com.azortis.playerwarps.impl.inventory.menu.BasicMenu;
import com.azortis.playerwarps.impl.inventory.menu.PaginatedMenu;
import com.azortis.playerwarps.inventory.layouts.MainMenuLayout;
import com.azortis.playerwarps.inventory.layouts.ManageWarpsLayout;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InventoryManager extends Injected {
    private BasicMenu mainMenu;
    private PaginatedMenu warpsManagerMenu;

    public BasicMenu getMainMenu() {
        if (mainMenu == null) {
            mainMenu = new BasicMenu(27, getPlayerWarps().getSettingsManager().getSettings().getMainMenuTitle());
            mainMenu.setLayout(new MainMenuLayout(mainMenu));
        }
        return mainMenu;
    }

    public PaginatedMenu getWarpsManager(Player p) {
        if (warpsManagerMenu == null) {
            PaginatedMenu menu = new PaginatedMenu(54);
            menu.setTitle("&b&lYour Warps");
            menu.setPages(1);
            menu.setLayout(new ManageWarpsLayout(menu));

        }

        return warpsManagerMenu;
    }

    public InventoryManager(PlayerWarps playerWarps) {
        super(playerWarps);
    }

    public void closeInventories() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.getOpenInventory().getTopInventory().getHolder() != null &&
                    player.getOpenInventory().getTopInventory().getHolder() instanceof IMenu) {
                player.closeInventory();
            }
        });
    }

    public void reload() {
        mainMenu = null;
    }
}
