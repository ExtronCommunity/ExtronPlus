package com.supercity.main.event;

import com.supercity.main.SuperCity;
import com.supercity.main.utils.Reference;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDieEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(SuperCity.INSTANCE.getJetpackHandler().wearingJetpack(e.getEntity()) && e.getEntity().getLastDamageCause().getCause() == DamageCause.FALL || e.getEntity().getLastDamageCause().getCause() == DamageCause.FLY_INTO_WALL) {
            e.setDeathMessage(Reference.DEATH_MESSAGE_JETPACK.replaceAll("%name", e.getEntity().getDisplayName()));
        }
        SuperCity.INSTANCE.getJetpackHandler().playerDeath(e.getEntity());
    }
}
