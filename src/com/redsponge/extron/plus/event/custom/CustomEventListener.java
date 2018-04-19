package com.redsponge.extron.plus.event.custom;

import com.redsponge.extron.plus.ExtronPlus;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class CustomEventListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if(e.getBlockPlaced().getType() == Material.REDSTONE_BLOCK) {
            for(BlockFace f : BlockFace.values()) {
                Material type = e.getBlockPlaced().getRelative(f).getType();
                if(type == Material.PISTON_BASE) {
                    ExtronPlus.INSTANCE.getServer().getPluginManager().callEvent(new BlockPistonPoweredByRedstoneBlockEvent(e.getBlockPlaced().getRelative(f), f, e.getBlockPlaced(), false));
                    return;
                }
                else if(type == Material.PISTON_STICKY_BASE) {
                    ExtronPlus.INSTANCE.getServer().getPluginManager().callEvent(new BlockPistonPoweredByRedstoneBlockEvent(e.getBlockPlaced().getRelative(f), f, e.getBlockPlaced(), true));
                    return;
                }
            }
        }
    }
}
