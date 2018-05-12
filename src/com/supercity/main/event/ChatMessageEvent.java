package com.supercity.main.event;

import com.supercity.main.recording.RecordingManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatMessageEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RecordingManager.getSettings(p).showChat() || p == e.getPlayer()) {
                p.sendMessage(String.format(e.getFormat(),e.getPlayer().getDisplayName(),e.getMessage()));
            }
        }
    }

}
