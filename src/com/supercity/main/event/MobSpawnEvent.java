package com.supercity.main.event;

import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftShulker;
import org.bukkit.entity.Shulker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class MobSpawnEvent implements Listener {

    @EventHandler
    public void spawn(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof Shulker) {
            Shulker s = (Shulker) e.getEntity();
            s.setColor(DyeColor.values()[new Random().nextInt(DyeColor.values().length)]);
        }
    }
}
