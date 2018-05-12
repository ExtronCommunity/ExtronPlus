package com.supercity.main.blocks;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class CustomBlock {

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
    public boolean onPlacedBy(Player p) {
        return true;
    }

    /**
     * When a player breaks this block.
     * @return Cancel the event or not.
     */
    public boolean onBrokenBy(Player p) {
        return true;
    }

    /**
     * When a player right clicks this placed block.
     */
    public void onRightClick(Player p) {

    }

    /**
     * When a player interacts using this block's item.
     */
    public void onInteractWith(Player p) {

    }

    public void onRedstoneActivated() {

    }

}
