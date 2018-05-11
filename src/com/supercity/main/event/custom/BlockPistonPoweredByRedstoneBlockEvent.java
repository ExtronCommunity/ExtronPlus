package com.supercity.main.event.custom;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class BlockPistonPoweredByRedstoneBlockEvent extends BlockEvent {

    private static final HandlerList handlers = new HandlerList();
    private boolean sticky;
    private Block poweredBy;
    private BlockFace direction;
    public BlockPistonPoweredByRedstoneBlockEvent(Block piston, BlockFace direction, Block poweredBy, boolean sticky) {
        super(piston);
        this.direction = direction;
        this.poweredBy = poweredBy;
        this.sticky = sticky;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isSticky() {
        return sticky;
    }

    public Block getPiston() {
        return block;
    }

    public BlockFace getDirection() {
        return direction;
    }

    public Block getPoweredBy() {
        return poweredBy;
    }
}
