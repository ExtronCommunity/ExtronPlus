package com.redsponge.extron.plus.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Redstone;

import java.util.ArrayList;
import java.util.List;

public class TrappedSigns implements Listener {

    private List<Location> signs = new ArrayList<>();

    @EventHandler
    public void interact(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
            if (signs.contains(e.getClickedBlock().getLocation())) {
                this.provideRedstonePower(e.getClickedBlock().getLocation());
            }
        }
    }

    @EventHandler
    public void placeBlock(BlockPlaceEvent e) {
        if (e.getItemInHand().getType() == Material.SIGN) {
            if (e.getItemInHand().getItemMeta().getLocalizedName().equalsIgnoreCase(Reference.TRAPPED_SIGN_NAME)) {
                signs.add(e.getBlockPlaced().getLocation());
            }
        }
    }

    private void provideRedstonePower(Location loc) {

    }
}