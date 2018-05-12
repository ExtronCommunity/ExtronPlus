package com.supercity.main.event;

import com.supercity.main.SuperCity;
import com.supercity.main.recording.RecordingManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinGameEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        SuperCity.INSTANCE.getJetpackHandler().initiatePlayer(e.getPlayer());
        //RecordingManager.playerJoin(e.getPlayer());
    }
}
