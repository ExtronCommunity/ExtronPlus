package com.redsponge.extron.plus.sleep;

import com.redsponge.extron.plus.ExtronPlus;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;
import java.util.List;

public class SleepSkippingHandler implements Listener {

    private int skipCooldown;
    private List<Player> inBed;
    private boolean doSkipNight;

    public SleepSkippingHandler() {
        skipCooldown = 0;
        inBed = new ArrayList<>();
        doSkipNight = true;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ExtronPlus.INSTANCE, () -> tick(), 0, 1);
    }

    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent e) {
        inBed.add(e.getPlayer());
        for(Player p : Bukkit.getOnlinePlayers()) {
            TextComponent component = new TextComponent("Hullo");
            component.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/dontskipnight"));
            p.sendMessage(component.toString());
        }
    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent e) {
        inBed.remove(e.getPlayer());
    }

    private void tick() {
        if(inBed.size() > 0) {
            if(skipCooldown == 0) {
                skipCooldown = 20;
            } else {
                skipCooldown--;
                if(skipCooldown <= 0) {
                    skipNight();
                    inBed.clear();
                }
            }
        }
    }

    private void skipNight() {
        World overworld = inBed.get(0).getWorld();
        System.out.println(overworld.getTime());
        System.out.println(overworld.getFullTime());
        overworld.setFullTime(overworld.getFullTime() + 24000 - overworld.getTime());
        overworld.setStorm(false);
    }

    public void setDoSkipNight(boolean doSkipNight) {
        this.doSkipNight = doSkipNight;
    }

    public boolean isDoSkipNight() {
        return doSkipNight;
    }
}
