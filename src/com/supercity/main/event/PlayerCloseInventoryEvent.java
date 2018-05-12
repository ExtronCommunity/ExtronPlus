package com.supercity.main.event;

import com.supercity.main.backpack.ItemBackPack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class PlayerCloseInventoryEvent implements Listener {

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        if(ItemBackPack.backpackInventories.keySet().contains(e.getInventory())) {
            ItemBackPack.close(e.getInventory());
        }
    }

}
