package com.azortis.playerwarps.impl.inventory.menu;

import com.azortis.playerwarps.impl.inventory.IMenu;
import com.azortis.playerwarps.impl.inventory.IPaginatedMenu;
import com.azortis.playerwarps.impl.inventory.object.*;
import com.azortis.playerwarps.impl.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class PaginatedMenu implements IPaginatedMenu {
    private InventoryItem[] items;
    private InventoryLayout[] layouts;
    private int pages, size;
    private Map<UUID, Integer> playerPages;
    private String title;
    private List<Condition<InventoryOpenEvent>> openConditions;
    private List<Condition<InventoryCloseEvent>> closeConditions;

    public PaginatedMenu(int size) {
        this.size = size;
        playerPages = new HashMap<>();
        openConditions = new ArrayList<>();
        closeConditions = new ArrayList<>();
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
    public InventoryItem getItem(int slot, int page) {
        return items[slot + ((page - 1) * size)];
    }

    @Override
    public Inventory getInventory(int page) {
        Inventory inventory = Bukkit.createInventory(this, size, Color.color(title + ": " + page));
        inventory.setStorageContents(getItems(page));
        return inventory;
    }

    private InventoryItem[] getItems(int page) {
        return Arrays.copyOfRange(items, (page - 1) * size, page * size);
    }

    @Override
    public void setItem(int slot, int page, InventoryItem item) {
        items[slot + ((page - 1) * size)] = item;
    }

    @Override
    public void setLayout(InventoryLayout layout, int page) {
        if (layout.getLayout().length < size || layout.getLayout().length > size) {
            System.out.println("ERROR LAYOUT NOT CORRECT SIZE " + layout.getClass());
            return;
        }
        layouts[page - 1] = layout;
        for (int i = 0; i < layout.getLayout().length; i++) {
            if (layout.getLayout()[i] != null)
                items[((page - 1) * size) + i] = layout.getLayout()[i];
        }
    }

    @Override
    public void construct(Action<IMenu> menuAction) {
        menuAction.action(this);
    }

    @Override
    public void addItem(InventoryItem item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) continue;
            items[i] = item;
            return;
        }
    }

    @Override
    public void openInventory(Player player, int page) {
        player.openInventory(getInventory(page));
        playerPages.put(player.getUniqueId(), page);
    }

    @Override
    public void setPages(int sum) {
        this.pages = sum;
        if (items != null)
            items = Arrays.copyOf(items, pages * size);
        else items = new InventoryItem[pages * size];
        if (layouts != null)
            layouts = Arrays.copyOf(layouts, pages);
        else layouts = new InventoryLayout[pages];
    }

    @Override
    public int getPages() {
        return pages;
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
    public void doConditions(InventoryOpenEvent event) {
        Arrays.stream(getItems(getPage(event.getPlayer().getUniqueId()))).forEach(item -> {
            if(item instanceof ConditionalItem) {
                ((ConditionalItem) item).testConditions(event);
            }
        });
    }

    @Override
    public void resetItem() {
        items = new InventoryItem[items.length];
        for(int i = 0; i < layouts.length; i++) {
            setLayout(layouts[i], i+1);
        }
    }

    @Override
    public int addItemExpand(InventoryItem item, InventoryLayout defaultLayout) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                continue;
            }
            items[i] = item;
            return -1;
        }
        setPages(pages + 1);
        if (defaultLayout != null)
            setLayout(defaultLayout, pages);
        for (int i = (pages - 1) * size; i < items.length; i++) {
            if (items[i] != null) continue;
            items[i] = item;
            break;
        }
        return pages;
    }

    @Override
    public int getPage(UUID uuid) {
        return playerPages.get(uuid);
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
    public void onInventoryClose(Player player) {
        playerPages.remove(player.getUniqueId());
    }
}
