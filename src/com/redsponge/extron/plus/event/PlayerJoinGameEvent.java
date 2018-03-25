package com.redsponge.extron.plus.event;

import com.redsponge.extron.plus.ExtronPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinGameEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ExtronPlus.INSTANCE.getJetpackHandler().initiatePlayer(e.getPlayer());
    }
}
