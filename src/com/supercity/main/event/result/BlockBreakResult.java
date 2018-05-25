package com.supercity.main.event.result;

import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakResult extends CancellableResult {

    private boolean dropItems;
    private int xp;

    public static final BlockBreakResult DEFAULT = new BlockBreakResult(false,true,0);

    public BlockBreakResult(boolean cancel, boolean dropItems, int xp) {
        super(cancel);
        this.dropItems = dropItems;
        this.xp = xp;
    }

    @Override
    public void modifyCancellable(Cancellable event) {
        if (event instanceof BlockBreakEvent) {
            ((BlockBreakEvent) event).setDropItems(dropItems);
            ((BlockBreakEvent) event).setExpToDrop(xp);
        }
    }

    @Override
    public EventResult<Cancellable> getDefault() {
        return DEFAULT;
    }
}
