package com.supercity.main.event;

import com.supercity.main.blocks.CustomBlockStorage;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class PlaceBlockEvent implements Listener {

    @EventHandler
    public void placedBlock(BlockPlaceEvent e) {
        e.setCancelled(onPlayerPlaceBlock(e.getPlayer(),e.getBlockPlaced(),e.getItemInHand()));
    }

    private boolean onPlayerPlaceBlock(Player player, Block blockPlaced, ItemStack itemInHand) {
        return false;
        player.setResourcePack();
    }
}
