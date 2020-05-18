package com.azortis.playerwarps.impl.inventory;

import com.azortis.playerwarps.impl.inventory.object.Action;
import com.azortis.playerwarps.impl.inventory.object.Condition;
import com.azortis.playerwarps.impl.inventory.object.InventoryItem;
import com.azortis.playerwarps.impl.inventory.object.InventoryLayout;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

public interface IMenu extends InventoryHolder {
    /**
     * The actions that are performed when the menu is constructed.
     *
     * @param menuAction The action that is called when a menu is constructed
     */
    void construct(Action<IMenu> menuAction);

    /**
     * Get the InventoryItem at the specified slot.
     *
     * @param slot the slot at which the item resides at
     * @return item at the specified slot in the inventory
     */
    InventoryItem getItem(int slot);

    /**
     * Set an item at the slot specified.
     *
     * @param slot slot that the item will reside in
     * @param item the item to set the slot to
     */
    void setItem(int slot, InventoryItem item);

    /**
     * Adds an item to the first available slot in the inventory.
     *
     * @param item the item to add
     */
    void addItem(InventoryItem item);

    /**
     * Set the current inventory layout to the specified one.
     *
     * @param layout the layout to set the inventory to
     */
    void setLayout(InventoryLayout layout);

    /**
     * Add a condition which is checked when the inventory is opened.
     *
     * @param condition the condition
     */
    void addOpenCondition(Condition<InventoryOpenEvent> condition);

    /**
     * Add a condition which is checked when the inventory is closed.
     *
     * @param condition the condition
     */
    void addCloseCondition(Condition<InventoryCloseEvent> condition);

    /**
     * Get all conditions which are checked when the inventory is opened.
     *
     * @return a set of conditions which do commands
     */
    List<Condition<InventoryOpenEvent>> getOpenCondition();

    /**
     * Get all conditions which are checked when the inventory is closed.
     *
     * @return a set of conditions which do commands
     */
    List<Condition<InventoryCloseEvent>> getCloseCondition();

    /**
     * Sets the title of the inventory.
     *
     * @param title The title of the inventory - color code supported.
     */
    void setTitle(String title);

    /**
     * Returns the size of the inventory.
     *
     * @return the size of the inventory
     */
    int getInventorySize();

    /**
     * Returns the name of the inventory.
     *
     * @return title of inventory
     */
    String getTitle();

    /**
     * Test conditions for conditional items when the inventory is opened.
     * @param event the event
     */
    void doConditions(InventoryOpenEvent event);
}
