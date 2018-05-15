package com.supercity.main.event.result;

import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class DamageResult extends CancellableResult {

    private double damage;
    public static final DamageResult DEFAULT = new DamageResult(false,0);

    public DamageResult(boolean cancel, double damage) {
        super(cancel);
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public void modifyCancellable(Cancellable e) {
        if (e instanceof EntityDamageEvent) {
            ((EntityDamageEvent) e).setDamage(damage);
        } else if (e instanceof PlayerItemDamageEvent) {
            ((PlayerItemDamageEvent) e).setDamage((int) damage);
        }
    }

    @Override
    public EventResult<Cancellable> getDefault() {
        return DEFAULT;
    }
}
