package com.supercity.main.event.result;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveResult extends CancellableResult {

    private Location from;
    private Location to;

    public static final MoveResult DEFAULT = new MoveResult(false,null,null);

    public MoveResult(boolean cancel,Location from, Location to) {
        super(cancel);
        this.from = from;
        this.to = to;
    }

    @Override
    public void modifyCancellable(Cancellable event) {
        if (event instanceof PlayerMoveEvent) {
            ((PlayerMoveEvent)event).setTo(to);
            ((PlayerMoveEvent)event).setFrom(from);
        }
    }

    @Override
    public EventResult<Cancellable> getDefault() {
        return DEFAULT;
    }
}
