package com.supercity.main.event;

import com.supercity.main.item.ItemBackPack;
import com.supercity.main.utils.Reference.ItemData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class PlayerCraftItemEvent implements Listener {

    @EventHandler
    public void onPlayerCraft(CraftItemEvent e) {
        if(e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLocalizedName()) {
            if(e.getCurrentItem().getItemMeta().getLocalizedName().equals(ItemData.BACKPACK.getLocName())) {
                ItemBackPack.assignID(e.getCurrentItem());
            }
        }
    }

}
