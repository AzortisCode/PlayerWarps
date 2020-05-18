package com.azortis.playerwarps.impl.inventory.menu;

import com.azortis.playerwarps.impl.inventory.IMenu;
import com.azortis.playerwarps.impl.inventory.object.*;
import com.azortis.playerwarps.impl.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class BasicMenu implements IMenu {
    private InventoryItem[] items;
    private InventoryLayout layout;
    private int size;
    private Set<UUID> viewers;
    private String title;
    private List<Condition<InventoryOpenEvent>> openConditions;
    private List<Condition<InventoryCloseEvent>> closeConditions;

    public BasicMenu(int size, String title) {
        this.size = size;
        this.viewers = new HashSet<>();
        this.title = title;
        items = new InventoryItem[size];
        openConditions = new ArrayList<>();
        closeConditions = new ArrayList<>();
    }

    @Override
    public void construct(Action<IMenu> menuAction) {
        menuAction.action(this);
    }

    @Override
    public InventoryItem getItem(int slot) {
        return items[slot];
    }

    @Override
    public void setItem(int slot, InventoryItem item) {
        items[slot] = item;
    }

    @Override
    public void addItem(InventoryItem item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null || items[i].isSimilar(new InventoryItem(Material.AIR, event -> event.setCancelled(true)))) {
                items[i] = item;
                return;
            }
        }
    }

    @Override
    public void doConditions(InventoryOpenEvent event) {
        Arrays.stream(items).forEach(item -> {
            if (item instanceof ConditionalItem)
                ((ConditionalItem) item).testConditions(event);
        });
    }

    @Override
    public void setLayout(InventoryLayout layout) {
        int size = Math.min(layout.getLayout().length, items.length);
        InventoryItem[] layoutItems = layout.getLayout();
        System.arraycopy(layoutItems, 0, items, 0, size);
        this.layout = layout;
    }

    @Override
    public void addOpenCondition(Condition<InventoryOpenEvent> condition) {
        openConditions.add(condition);
    }

    @Override
    public void addCloseCondition(Condition<InventoryCloseEvent> condition) {
        closeConditions.add(condition);
    }

    @Override
    public List<Condition<InventoryOpenEvent>> getOpenCondition() {
        return openConditions;
    }

    @Override
    public List<Condition<InventoryCloseEvent>> getCloseCondition() {
        return closeConditions;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getInventorySize() {
        return size;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Inventory getInventory() {
        Inventory inventory = Bukkit.createInventory(this, size, Color.color(title));
        inventory.setStorageContents(items);
        return inventory;
    }

    public Set<UUID> getViewers() {
        return viewers;
    }

    public InventoryLayout getLayout() {
        return layout;
    }
}
