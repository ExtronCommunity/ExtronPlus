package com.supercity.main.inventory;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftInventoryCustom;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class SplitterInventory extends CraftInventoryCustom {
    public SplitterInventory(InventoryHolder owner) {
        super(owner, InventoryType.FURNACE, "Enmc");
    }
}
