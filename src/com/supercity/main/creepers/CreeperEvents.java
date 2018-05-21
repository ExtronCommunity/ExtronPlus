package com.supercity.main.creepers;

import com.supercity.main.creepers.CreeperChecker;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

public class CreeperEvents implements Listener {

    @EventHandler
    public void explode(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper) {
            CreeperChecker.explosion(e);
        }
    }

    @EventHandler
    public void target(EntityTargetLivingEntityEvent e) {
        if (e.getEntity() instanceof Creeper) {
            CreeperChecker.target(e);
        }
    }

    @EventHandler
    public void removed(EntityDeathEvent e) {
        if (e.getEntity() instanceof Creeper) {
            CreeperChecker.death(e);
        }
    }
}
