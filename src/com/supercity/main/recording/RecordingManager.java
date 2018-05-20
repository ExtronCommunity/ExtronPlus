package com.supercity.main.recording;

import com.supercity.main.utils.Reference;
import com.supercity.main.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RecordingManager {

    private static Map<UUID,RecordingSettings> settings = new HashMap<>();
    private static Scoreboard main;
    private static Scoreboard hiddenPlayed;

    static {
        main = Bukkit.getScoreboardManager().getMainScoreboard();
        TeamType.initAll(main);
        hiddenPlayed = Bukkit.getScoreboardManager().getNewScoreboard();
        TeamType.initAll(hiddenPlayed);
    }

    public static void copyHealthFromMain() {
        Objective h = hiddenPlayed.registerNewObjective("health","health");
        h.setDisplayName("Health");
        h.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        for (Player p : Bukkit.getOnlinePlayers()) {
            copyHealth(p);
            toggleScoreboard(p);
            toggleScoreboard(p);
        }
    }

    private static void copyHealth(Player p) {
        Objective health = main.getObjective("health");
        if (health == null) {
            return;
        }
        Objective health2 = hiddenPlayed.getObjective("health");
        if (health2 == null) {
            return;
        }
        Score s = health.getScore(p.getName());
        health2.getScore(p.getName()).setScore(s.getScore());
    }

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
        if (b) {
            TeamType.RECORDING.join(p);
        } else {
            TeamType.DEFAULT.join(p);
        }
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
        if (getSettings(p).showScoreboard()) {
            p.setScoreboard(main);
        } else {
            p.setScoreboard(hiddenPlayed);
        }
    }

    public static void toggleChat(Player p) {
        getSettings(p).setShowChat(!getSettings(p).showChat());
        if (getSettings(p).showChat()) {
            Reflection.sendActionBar(p,Reference.CHAT_SHOWN);
        } else {
            Reflection.sendActionBar(p,Reference.CHAT_HIDDEN);
        }
    }

    public static void tick() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            getSettings(p).afkTimer++;
            if (getSettings(p).afkTimer > Reference.AFK_TIMER_LIMIT && !getSettings(p).isAFK()) {
                setAFK(p, true);
            }
            if (getSettings(p).showScoreboard()) {
                if (getSettings(p).isAFK()) {
                    changePlayedScore(p, -1);
                }
            } else if (!getSettings(p).isAFK()) {
                changePlayedScore(p,1);
            }
        }
    }

    private static void changePlayedScore(Player p, int i) {
        Objective played = main.getObjective("played");
        if (played == null) {
            played = main.registerNewObjective("played","stat.playOneMinute");
            played.setDisplaySlot(DisplaySlot.SIDEBAR);
            played.setDisplayName("Played");
        }
        Score s = played.getScore(p.getName());
        s.setScore(s.getScore() + i);
    }

    public static void setAFK(Player p, boolean b) {
        getSettings(p).setAFK(b);
        if (b) {
            TeamType.AFK.join(p);
        } else if (getSettings(p).isRecording()) {
            TeamType.RECORDING.join(p);
        } else {
            TeamType.DEFAULT.join(p);
        }
    }

    public static void setAllNotAFK() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            setAFK(p,false);
        }
    }

    public static void playerJoin(Player p) {
        copyHealth(p);
        TeamType.DEFAULT.join(p);
    }

    public enum TeamType {
        DEFAULT("team",ChatColor.DARK_PURPLE), AFK("afk",ChatColor.LIGHT_PURPLE), RECORDING("recording",ChatColor.RED);


        private final ChatColor color;
        private final String name;

        TeamType(String name, ChatColor color) {
            this.name = name;
            this.color = color;
        }

        public static void initAll(Scoreboard sb) {
            for (TeamType t : values()) {
                t.create(sb);
            }
        }

        public void create(Scoreboard sb) {
            Team t = sb.getTeam(this.name);
            if (t == null) {
                t = sb.registerNewTeam(this.name);
            }

            t.setColor(color);
            t.setPrefix(color +"");
            t.setSuffix(ChatColor.RESET +"");
            t.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.NEVER);
        }

        public String getName() {
            return name;
        }

        public ChatColor getColor() {
            return color;
        }

        public Team getTeam(Scoreboard sb) {
            this.create(sb);
            return sb.getTeam(this.name);
        }

        private void join(Scoreboard sb, Player p) {
            Team t = getTeam(sb);
            t.addEntry(p.getName());
        }

        public void join(Player p) {
            this.join(main,p);
            this.join(hiddenPlayed,p);
        }
    }

}
