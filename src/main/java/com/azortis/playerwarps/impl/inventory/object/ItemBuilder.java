package com.azortis.playerwarps.impl.inventory.object;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder<T extends InventoryItem> {

    private ItemStack stack;
    private Action<InventoryClickEvent> action;

    public ItemBuilder(ItemStack stack) {
        this.stack = stack;
    }

    public ItemBuilder(Material m) {
        this.stack = new ItemStack(m);
    }

    public ItemBuilder<T> name(String name) {
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        stack.setItemMeta(stackMeta);
        return this;
    }

    public ItemBuilder<T> amount(int amount) {
        stack.setAmount(amount);
        return this;
    }


    public ItemBuilder<T> lore(String... lore) {
        for (int i = 0; i < lore.length; i++) {
            lore[i] = ChatColor.translateAlternateColorCodes('&', lore[i]);
        }

        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.setLore(Arrays.asList(lore));
        stack.setItemMeta(stackMeta);
        return this;
    }


    public ItemBuilder<T> lore(List<String> lore) {
        for (int i = 0; i < lore.size(); i++) {
            lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
        }

        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.setLore(lore);
        stack.setItemMeta(stackMeta);
        return this;
    }


    public ItemBuilder<T> data(short data) {
        stack.setDurability(data);
        return this;
    }


    public ItemBuilder<T> durability(short durability) {
        stack.setDurability(durability);
        return this;
    }


    public ItemBuilder<T> setAction(Action<InventoryClickEvent> action) {
        this.action = action;
        return this;
    }

    public T build() {
        return (T) new InventoryItem(stack, action);
    }
}
