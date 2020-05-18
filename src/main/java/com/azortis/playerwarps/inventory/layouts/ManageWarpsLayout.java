package com.azortis.playerwarps.inventory.layouts;

import com.azortis.playerwarps.impl.inventory.IPaginatedMenu;
import com.azortis.playerwarps.impl.inventory.layout.DefaultPaginatedLayout;
import com.azortis.playerwarps.impl.inventory.object.InventoryItem;

public class ManageWarpsLayout extends DefaultPaginatedLayout {
    public ManageWarpsLayout(IPaginatedMenu menu) {
        super(menu);
        int size = menu.getInventorySize();
        InventoryItem search;

    }
}
