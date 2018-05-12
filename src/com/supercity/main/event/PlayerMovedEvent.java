package com.supercity.main.event;

import com.supercity.main.recording.RecordingManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovedEvent implements Listener {

    @EventHandler
    public void move(PlayerMoveEvent e) {
        RecordingManager.getSettings(e.getPlayer()).afkTimer = 0;
        RecordingManager.setAFK(e.getPlayer(),false);
    }

}
