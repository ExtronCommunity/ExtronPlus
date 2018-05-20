package com.supercity.main.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomBlock implements Listener {

    /**
     * The idCount of the block. used for registry and differentiation.
     */
    public abstract String getId();

    /**
     * The display name of the item.
     */
    public abstract String getDisplayName();

    /**
     * The real block type to place when placing this block.
     */
    public abstract Material getRealBlockType();

    /**
     * The data of the real block to place.
     */
    public abstract byte getRealBlockData();

    /**
     * When a player places this block.
     * @return Cancel the event or not.
     */
    public boolean onPlacedBy(Player p, Location loc) {
        return false;
    }

    /**
     * When a player breaks this block.
     * @return Cancel the event or not.
     */
    public boolean onBrokenBy(Player p, ItemStack item) {
        return true;
    }

    /**
     * When a player right clicks this placed block.
     */
    public void onRightClick(Player p, Block block, BlockFace clickedFace) {

    }

    /**
     * When a player interacts using this block's item.
     */
    public void onInteractWith(Player p) {

    }

    public void onRedstoneActivated() {

    }

    public boolean isTickable() {
        return false;
    }

    public void onTick(Block b) {

    }

    public List<ItemStack> getDrops() {
        return new ArrayList<>();
    }
}
