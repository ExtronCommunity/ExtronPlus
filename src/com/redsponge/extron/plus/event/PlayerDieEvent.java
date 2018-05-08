package com.redsponge.extron.plus.event;

import com.redsponge.extron.plus.ExtronPlus;
import com.redsponge.extron.plus.utils.Reference;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDieEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(ExtronPlus.INSTANCE.getJetpackHandler().wearingJetpack(e.getEntity()) && e.getEntity().getLastDamageCause().getCause() == DamageCause.FALL || e.getEntity().getLastDamageCause().getCause() == DamageCause.FLY_INTO_WALL) {
            e.setDeathMessage(Reference.DEATH_MESSAGE_JETPACK.replaceAll("%name", e.getEntity().getDisplayName()));
        }
        ExtronPlus.INSTANCE.getJetpackHandler().playerDeath(e.getEntity());
    }
}
