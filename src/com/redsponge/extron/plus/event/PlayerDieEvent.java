package com.redsponge.extron.plus.event;

import com.redsponge.extron.plus.ExtronPlus;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDieEvent implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        ExtronPlus.INSTANCE.getJetpackHandler().playerDeath(e.getEntity());
    }
}
