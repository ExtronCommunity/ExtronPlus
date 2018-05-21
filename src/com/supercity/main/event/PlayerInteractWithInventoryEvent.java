package com.supercity.main.event;

import com.supercity.main.item.ItemBackPack;
import com.supercity.main.utils.Reference;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerInteractWithInventoryEvent implements Listener {

    @EventHandler
    public void onInventoryInteraction(InventoryClickEvent e) {
        Inventory i = e.getInventory();
        if (e.getCurrentItem() != null) {
            ItemStack checkItem = e.getCurrentItem().getType() == Material.AIR ? e.getCursor() : e.getCurrentItem();
            if (e.getClickedInventory() instanceof PlayerInventory && this.isTaking(e.getAction())) {
                if (i.getTitle().equals(Reference.BACKPACK_TITLE)) {
                    if (ItemBackPack.isBackpack(checkItem)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isTaking(InventoryAction action) {
        return action == InventoryAction.PICKUP_ALL || action == InventoryAction.PICKUP_HALF || action == InventoryAction.PICKUP_ONE || action == InventoryAction.PICKUP_SOME || action == InventoryAction.MOVE_TO_OTHER_INVENTORY || action == InventoryAction.SWAP_WITH_CURSOR;
    }

}
