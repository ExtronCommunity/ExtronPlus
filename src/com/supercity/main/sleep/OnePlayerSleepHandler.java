package com.supercity.main.sleep;

import com.supercity.main.SuperCity;
import com.supercity.main.recording.RecordingManager;
import com.supercity.main.utils.Reference;
import com.supercity.main.utils.WorldUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OnePlayerSleepHandler implements Listener {

    private int skipCooldown;
    private List<Player> inBed;
    private boolean doSkipNight;

    private final TextComponent cancelSleep;
    private final TextComponent playerSleeping;
    private final Random random;
    public OnePlayerSleepHandler() {
        skipCooldown = 0;
        inBed = new ArrayList<>();
        doSkipNight = true;
        this.random = new Random();
        playerSleeping = new TextComponent();
        cancelSleep = new TextComponent(Reference.CANCEL_SLEEP);
        cancelSleep.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dontskipnight"));
        cancelSleep.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[] {new TextComponent(Reference.CANCEL_SLEEP_HOVER_MESSAGE)}));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(SuperCity.INSTANCE, this::tick, 0, 1);
    }

    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent e) {
        if(doSkipNight) {
            if(inBed.isEmpty()) {
                playerSleeping.setText(Reference.PLAYER_ENTERED_BED.replaceAll("%player", e.getPlayer().getDisplayName()) + " ");
                cancelSleep.setText(Reference.CANCEL_SLEEP);
            } else if(inBed.size() == 1) {
                playerSleeping.setText(Reference.ANOTHER_PLAYER_ENTERED_BED.replaceAll("%player", e.getPlayer().getDisplayName()) + " ");
                cancelSleep.setText(Reference.CANCEL_SLEEP_2_PEOPLE);
            } else if(inBed.size() == 2) {
                playerSleeping.setText(Reference.PLAYER_3_ENTERED_BED.replaceAll("%player1", inBed.get(0).getDisplayName()).replaceAll("%player2", inBed.get(1).getDisplayName()).replaceAll("%player", e.getPlayer().getDisplayName()) + " ");
                cancelSleep.setText(Reference.CANCEL_SLEEP_3_PEOPLE);
            } else {
                playerSleeping.setText(Reference.PLAYER_4_ENTERED_BED[random.nextInt(Reference.PLAYER_4_ENTERED_BED.length)].replaceAll("%player", e.getPlayer().getDisplayName()) + " ");
                cancelSleep.setText(Reference.CANCEL_SLEEP_4_PEOPLE[random.nextInt(Reference.CANCEL_SLEEP_4_PEOPLE.length)]);
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (RecordingManager.getSettings(p).sendSleepAlerts()) {
                    p.spigot().sendMessage(playerSleeping, cancelSleep);
                }
            }
        }
        inBed.add(e.getPlayer());
    }

    @EventHandler
    public void onPlayerLeaveBed(PlayerBedLeaveEvent e) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RecordingManager.getSettings(p).sendSleepAlerts()) {
                p.sendMessage(ChatColor.GREEN + e.getPlayer().getDisplayName() + " left the bed");
            }
        }
        inBed.remove(e.getPlayer());
    }

    private void tick() {
        if(inBed.size() > 0) {
            if(skipCooldown == 0) {
                skipCooldown = Reference.ONE_PLAYER_SLEEP_COOLDOWN;
            } else if(doSkipNight){
                skipCooldown--;
                if(skipCooldown <= 0) {
                    skipNight();
                    inBed.clear();
                }
            }
        }
        if(WorldUtils.getOverworld().getTime() == 0){
            System.out.println("Resetting one player sleep");
            doSkipNight = true;
            skipCooldown = 0;
        }
    }

    private void skipNight() {
        World overworld = inBed.get(0).getWorld();
        overworld.setFullTime(overworld.getFullTime() + 24000 - overworld.getTime());
        overworld.setStorm(false);
        doSkipNight = true;
        skipCooldown = 0;
    }

    public void setDoSkipNight(boolean doSkipNight) {
        this.doSkipNight = doSkipNight;
    }

    public boolean isDoSkipNight() {
        return doSkipNight;
    }

    public List<Player> getInBed() {
        return inBed;
    }
}
