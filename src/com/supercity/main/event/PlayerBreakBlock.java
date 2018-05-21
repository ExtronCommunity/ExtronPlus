package com.supercity.main.event;

import com.supercity.main.utils.Reference;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerBreakBlock implements Listener {

    @EventHandler
    public void breakBlock(BlockBreakEvent e) {
        if (Reference.TRAPPED_SIGNS_LOCATIONS.contains(e.getBlock().getLocation())) {
            Reference.TRAPPED_SIGNS_LOCATIONS.remove(e.getBlock().getLocation());
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(),new ItemStack(Material.TRIPWIRE_HOOK));
        }
    }

}
