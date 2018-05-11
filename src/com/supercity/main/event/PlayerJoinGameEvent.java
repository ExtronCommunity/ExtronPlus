package com.supercity.main.event;

import com.supercity.main.SuperCity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinGameEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        SuperCity.INSTANCE.getJetpackHandler().initiatePlayer(e.getPlayer());
    }
}
