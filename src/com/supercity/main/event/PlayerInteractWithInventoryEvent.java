package com.supercity.main.event;

import com.supercity.main.item.ItemBackPack;
import com.supercity.main.utils.Reference;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractWithInventoryEvent implements Listener {

    @EventHandler
    public void onInventoryInteraction(InventoryClickEvent e) {
        Inventory i = e.getInventory();
        ItemStack checkItem = e.getCurrentItem().getType() == Material.AIR?e.getCursor():e.getCurrentItem();
        if(i.getTitle().equals(Reference.BACKPACK_TITLE)) {
            if(ItemBackPack.isBackpack(checkItem)){
                e.setCancelled(true);
            }
        }
    }

}
