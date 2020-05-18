package com.azortis.playerwarps.impl.inventory.object;

import java.util.Arrays;

public abstract class InventoryLayout {
    protected InventoryItem[] item;

    public InventoryLayout setSize(int size) {
        if (item != null)
            item = Arrays.copyOf(item, size);
        else item = new InventoryItem[size];
        return this;
    }

    public InventoryItem[] getLayout() {
        return item;
    }

}
