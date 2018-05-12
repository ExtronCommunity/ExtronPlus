package com.supercity.main.recording;

import com.supercity.main.utils.Reference;
import com.supercity.main.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_12_R1.scoreboard.CraftScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RecordingManager {

    private static Map<UUID,RecordingSettings> settings = new HashMap<>();

    public static void setRecording(Player p, boolean b) {
        getSettings(p).setRecording(b);
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (pl != p) {
                if (!getSettings(pl).isRecording() && !getSettings(pl).sendRecordAlerts()) {
                    if (b) {
                        pl.sendMessage(Reference.PLAYER_STARTED_RECORDING.replaceAll("%player", p.getDisplayName()));
                    } else {
                        pl.sendMessage(Reference.PLAYER_STOPPED_RECORDING.replaceAll("%player", p.getDisplayName()));
                    }
                }
            } else {
                if (b) {
                    Reflection.sendActionBar(p, Reference.MYSELF_STARTED_RECORDING);
                } else {
                    Reflection.sendActionBar(p, Reference.MYSELF_STOPPED_RECORDING);
                }
            }
        }
        Scoreboard s;
        if (getSettings(p).showScoreboard()) {
            s = Bukkit.getScoreboardManager().getMainScoreboard();
        } else {
            s = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective health = s.getObjective("health");
            if (health == null) {
                health = s.registerNewObjective("health","health");
                health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
            }
        }
        if (b) {
            Team rec = s.getTeam("recording");
            if (rec == null) {
                rec = s.registerNewTeam("recording");
                rec.setPrefix("§c[REC] ");
                rec.setColor(ChatColor.RED);
                rec.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.NEVER);
            }
            rec.addEntry(p.getName());
        } else {
            Team t = s.getTeam("team");
            if (t == null) {
                t = s.registerNewTeam("team");
                t.setColor(ChatColor.DARK_PURPLE);
                t.setPrefix(ChatColor.DARK_PURPLE +"");
                t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            }
            t.addEntry(p.getName());
        }
        p.setScoreboard(s);
    }

    public static RecordingSettings createSettings(Player p) {
        RecordingSettings s = new RecordingSettings();
        settings.put(p.getUniqueId(),s);
        return s;
    }

    public static RecordingSettings getSettings(Player p) {
        if (settings.containsKey(p.getUniqueId())) {
            return settings.get(p.getUniqueId());
        }
        return createSettings(p);
    }

    public static void toggleScoreboard(Player p) {
        boolean b = getSettings(p).showScoreboard();
        getSettings(p).setShowScoreboard(!b);
        Scoreboard s;
        if (b) {
            s = Bukkit.getScoreboardManager().getNewScoreboard();
            Objective health = s.registerNewObjective("health","health");
            health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        } else {
            s = Bukkit.getScoreboardManager().getMainScoreboard();
        }
        if (getSettings(p).isRecording()) {
            Team rec = s.getTeam("recording");
            if (rec == null){
                rec = s.registerNewTeam("recording");
                rec.setPrefix("§c[REC] ");
                rec.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.NEVER);
            }
            rec.addEntry(p.getName());
        } else {
            Team t = s.getTeam("team");
            if (t == null) {
                t = s.registerNewTeam("team");
                t.setColor(ChatColor.DARK_PURPLE);
                t.setPrefix(ChatColor.DARK_PURPLE +"");
                t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            }
            t.addEntry(p.getName());
        }
        p.setScoreboard(s);
    }

    public static void toggleChat(Player p) {
        getSettings(p).setShowChat(!getSettings(p).showChat());
        if (getSettings(p).showChat()) {
            Reflection.sendActionBar(p,Reference.CHAT_SHOWN);
        } else {
            Reflection.sendActionBar(p,Reference.CHAT_HIDDEN);
        }
    }
}
