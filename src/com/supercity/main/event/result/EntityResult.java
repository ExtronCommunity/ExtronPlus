package com.supercity.main.event.result;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityResult extends CancellableResult {

    private Entity entity;
    public static final EntityResult DEFAULT = new EntityResult(false,null);

    public EntityResult(boolean cancel, Entity entity) {
        super(cancel);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void modifyCancellable(Cancellable event) {
        if (event instanceof EntityShootBowEvent) {
            ((EntityShootBowEvent) event).setProjectile(entity);
        }
    }

    @Override
    public EventResult<Cancellable> getDefault() {
        return DEFAULT;
    }
}
