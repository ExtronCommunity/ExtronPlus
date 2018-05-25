package com.supercity.main.brettyprotection;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class BrettyProtection {

    @EventHandler
    public void brettyHit(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            if (e.getDamager().getName().equalsIgnoreCase("PrettyNice")) {
                e.getDamager().remove();
                ((Player) e.getEntity()).setHealth(20);
            }
        }
    }

}
