package com.azortis.playerwarps.impl.inventory.object;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * This item can do actions based off of conditions.
 * Also does the default action as well.
 */
public class ConditionalItem extends InventoryItem {
    private Set<Condition<InventoryOpenEvent>> actions;


    public ConditionalItem(Material type, Action<InventoryClickEvent> action) {
        super(type, action);
    }

    public ConditionalItem(Material type, int amount, Action<InventoryClickEvent> action) {
        super(type, amount, action);
    }

    public ConditionalItem(ItemStack stack, Action<InventoryClickEvent> action) throws IllegalArgumentException {
        super(stack, action);
    }

    public Set<Condition<InventoryOpenEvent>> getConditions() {
        return actions;
    }

    public ConditionalItem setConditions(Set<Condition<InventoryOpenEvent>> actions) {
        this.actions = actions;
        return this;
    }

    public ConditionalItem addConditions(Condition<InventoryOpenEvent> condition) {
        actions.add(condition);
        return this;
    }

    public void testConditions(InventoryOpenEvent event) {
        actions.forEach(condition -> condition.test(event));
    }

    @Override
    public void useAction(InventoryClickEvent event) {
        super.useAction(event);
    }
}
