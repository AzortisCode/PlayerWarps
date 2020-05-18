package com.azortis.playerwarps.impl.inventory.object;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryItem extends ItemStack {
    private Action<InventoryClickEvent> action;

    public InventoryItem(Material type, Action<InventoryClickEvent> action) {
        super(type);
        this.action = action;
    }

    public InventoryItem(Material type, int amount, Action<InventoryClickEvent> action) {
        super(type, amount);
        this.action = action;
    }

    public InventoryItem(ItemStack stack, Action<InventoryClickEvent> action) throws IllegalArgumentException {
        super(stack);
        this.action = action;
    }


    public void useAction(InventoryClickEvent event) {
        action.action(event);
    }


    public void setAction(Action<InventoryClickEvent> action) {
        this.action = action;
    }

    public void setItemStack(ItemStack stack) {
        setType(stack.getType());
        setAmount(stack.getAmount());
        if (stack.hasItemMeta()) {
            setItemMeta(stack.getItemMeta());
        }
    }
}
