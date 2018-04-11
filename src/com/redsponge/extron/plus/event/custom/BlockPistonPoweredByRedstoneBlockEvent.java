package com.redsponge.extron.plus.event.custom;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class BlockPistonPoweredEvent extends BlockEvent {

    private static final HandlerList handles = new HandlerList();

    public BlockPistonPoweredEvent(Block theBlock) {
        super(theBlock);
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
