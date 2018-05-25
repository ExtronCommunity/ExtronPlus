package com.supercity.main.event.result;


import org.bukkit.event.Cancellable;

public abstract class CancellableResult extends EventResult<Cancellable> {

    private boolean cancel;

    public CancellableResult(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public void modify(Cancellable event) {
        event.setCancelled(cancel);
        if (!cancel && getDefault() != this) {
            modifyCancellable(event);
        }
    }

    public abstract void modifyCancellable(Cancellable event);
}
