package com.supercity.main.event;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (PlayerInteractionEvent.trappedSigns.contains(e.getBlock().getLocation())) {
            PlayerInteractionEvent.trappedSigns.remove(e.getBlock().getLocation());
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),new ItemStack(Material.TRIPWIRE_HOOK));
        }
    }

}
