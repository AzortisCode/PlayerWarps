package com.azortis.playerwarps.inventory.layouts;

import com.azortis.playerwarps.impl.inventory.menu.BasicMenu;
import com.azortis.playerwarps.impl.inventory.object.*;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class MainMenuLayout extends InventoryLayout {
    public MainMenuLayout(BasicMenu menu) {
        item = new InventoryItem[menu.getInventorySize()];
        InventoryItem filler = new ItemBuilder<>(Material.BLACK_STAINED_GLASS_PANE)
                .name("&l").setAction(event -> event.setCancelled(true)).build();
        Arrays.fill(item, filler);
        final InventoryItem manage, admin, warps;
        manage = new ItemBuilder<>(Material.PLAYER_HEAD)
                .name("&b&lManage Warps").setAction(event -> {
                    if (!event.getWhoClicked().hasPermission("warp.settings") ||
                            !event.getWhoClicked().hasPermission("warp.set")) {
                        event.setCancelled(true);
                        return;
                    }
                    // todo: Make this open new inventory

                }).build();
        admin = new ItemBuilder<>(Material.BEDROCK).name("&b&lModerate Content").setAction(event -> {
            if (!event.getWhoClicked().hasPermission("warp.admin")) {
                event.setCancelled(true);
                return;
            }
            // todo: Make this open new inventory

        }).build();
        warps = new ItemBuilder<>(Material.ENDER_PEARL).name("&b&lPlayer Warps").setAction(event -> {
            if (!event.getWhoClicked().hasPermission("warp.default")) {
                event.setCancelled(true);
                return;
            }
            // todo: Make this open new inventory

        }).build();
        menu.addOpenCondition(new Condition<InventoryOpenEvent>()
                .addRequirements(event -> event.getPlayer().hasPermission("warp.set") ||
                        event.getPlayer().hasPermission("warp.settings") ||
                        event.getPlayer().hasPermission("warp.admin") ||
                        event.getPlayer().hasPermission("warp.default"))
                .setAction(event -> {
                            SkullMeta skullMeta = (SkullMeta) manage.getItemMeta();
                            skullMeta.setOwningPlayer((OfflinePlayer) event.getPlayer());
                            manage.setItemMeta(skullMeta);
                            int size = event.getInventory().getSize() / 9;
                            int row = size % 3 == 0 ? size / 3 : size / 2;
                            boolean set, admins, defaults;
                            set = (event.getPlayer().hasPermission("warp.set") || event.getPlayer().hasPermission("warp.settings"));
                            admins = event.getPlayer().hasPermission("warp.admin");
                            defaults = event.getPlayer().hasPermission("warp.default");
                            int sum = (set ? 1 : 0) + (admins ? 1 : 0) + (defaults ? 1 : 0);
                            if (size % 2 == 0) {
                                // We take up two rows
                                switch (sum) {
                                    case 1:
                                        event.getInventory().setItem((row * 8) + 5, defaults ? warps : set ? manage : admins ? admin : filler);
                                        break;
                                    case 2:
                                        event.getInventory().setItem((row * 8) + 4, set ? manage : defaults ? warps : admins ? admin : filler);
                                        event.getInventory().setItem((row * 8) + 6, defaults ? warps : admins ? admin : set ? manage : filler);
                                        break;
                                    case 3:
                                        event.getInventory().setItem((row * 8) + 4, manage);
                                        event.getInventory().setItem((row * 8) + 6, admin);
                                        event.getInventory().setItem(((row + 1) * 8) + 5, warps);
                                        break;
                                }
                            } else if (size % 3 == 0) {
                                // We take up one row
                                switch (sum) {
                                    case 1:
                                        event.getInventory().setItem((row * 8) + 5, defaults ? warps : set ? manage : admins ? admin : filler);
                                        break;
                                    case 2:
                                        event.getInventory().setItem((row * 8) + 4, set ? manage : defaults ? warps : admins ? admin : filler);
                                        event.getInventory().setItem((row * 8) + 6, defaults ? warps : admins ? admin : set ? manage : filler);
                                        break;
                                    case 3:
                                        event.getInventory().setItem((row * 8) + 3, manage);
                                        event.getInventory().setItem((row * 8) + 5, admin);
                                        event.getInventory().setItem((row * 8) + 7, warps);
                                        break;
                                }
                            }
                        }
                ));

    }

}
