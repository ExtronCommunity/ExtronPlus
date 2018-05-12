package com.supercity.main.event;

import com.supercity.main.recording.RecordingManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveServer implements Listener {

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        RecordingManager.setAFK(e.getPlayer(),false);
    }
}
