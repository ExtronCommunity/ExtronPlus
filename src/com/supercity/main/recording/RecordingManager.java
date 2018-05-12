package com.supercity.main.recording;

import com.supercity.main.utils.Reference;
import com.supercity.main.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.scoreboard.CraftScoreboard;
import org.bukkit.craftbukkit.v1_12_R1.scoreboard.CraftScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

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
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!getSettings(pl).showScoreboard()) {
                    Team r = pl.getScoreboard().getTeam("recording");
                    if (r != null) r.addEntry(p.getName());
                }
            }
        } else {
            Team t = s.getTeam("team");
            if (t == null) {
                t = s.registerNewTeam("team");
                t.setColor(ChatColor.DARK_PURPLE);
                t.setPrefix(ChatColor.DARK_PURPLE +"");
                t.setSuffix(ChatColor.RESET +"");
                t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            }
            t.addEntry(p.getName());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!getSettings(pl).showScoreboard()) {
                    Team r = pl.getScoreboard().getTeam("team");
                    if (r != null) r.addEntry(p.getName());
                }
            }
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
            copyHealthFromMain(health);
            Team r = s.registerNewTeam("recording");
            Team t = s.registerNewTeam("team");
            Team a = s.registerNewTeam("afk");
            for (Player pl : Bukkit.getOnlinePlayers()) {
                r.setPrefix("§c[REC] ");
                r.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.NEVER);
                if (!getSettings(pl).isAFK() && getSettings(pl).isRecording()) r.addEntry(pl.getName());

                t.setColor(ChatColor.DARK_PURPLE);
                t.setPrefix(ChatColor.DARK_PURPLE +"");
                t.setSuffix(ChatColor.RESET +"");
                t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
                if (!getSettings(pl).isAFK() && !getSettings(p).isRecording()) t.addEntry(pl.getName());

                a.setColor(ChatColor.GRAY);
                a.setPrefix(ChatColor.GRAY + "");
                a.setSuffix(ChatColor.RESET + "");
                a.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);

                if (getSettings(pl).isAFK()) a.addEntry(pl.getName());
            }
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
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!getSettings(pl).showScoreboard()) {
                    Team r = pl.getScoreboard().getTeam("recording");
                    if (r != null) r.addEntry(p.getName());
                }
            }
        } else {
            Team t = s.getTeam("team");
            if (t == null) {
                t = s.registerNewTeam("team");
                t.setColor(ChatColor.DARK_PURPLE);
                t.setPrefix(ChatColor.DARK_PURPLE +"");
                t.setSuffix(ChatColor.RESET +"");
                t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            }
            t.addEntry(p.getName());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!getSettings(pl).showScoreboard()) {
                    Team r = pl.getScoreboard().getTeam("team");
                    if (r != null) r.addEntry(p.getName());
                }
            }
        }
        p.setScoreboard(s);
    }

    private static void copyHealthFromMain(Objective health) {
        Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective h = main.getObjective("health");
        if (h == null) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            Score s = h.getScore(p.getName());
            health.getScore(p.getName()).setScore(s.getScore());
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
            if (getSettings(p).isAFK()) {
                Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
                Objective o = s.getObjective("played");
                if (o != null) {
                    Score score = o.getScore(p.getName());
                    score.setScore(score.getScore()-1);
                }
            }
        }
    }

    public static void setAFK(Player p, boolean b) {
        getSettings(p).setAFK(b);
        Scoreboard s = p.getScoreboard();
        if (b) {
            Team afk = s.getTeam("afk");
            if (afk == null) {
                afk = s.registerNewTeam("afk");
                afk.setColor(ChatColor.GRAY);
                afk.setPrefix(ChatColor.GRAY + "");
                afk.setSuffix(ChatColor.RESET + "");
                afk.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            }
            afk.addEntry(p.getName());
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (!getSettings(pl).showScoreboard()) {
                    Team r = pl.getScoreboard().getTeam("afk");
                    if (r != null) r.addEntry(p.getName());
                }
            }
        } else {
            if (getSettings(p).isRecording()) {
                Team rec = s.getTeam("recording");
                if (rec == null) {
                    rec = s.registerNewTeam("recording");
                    rec.setPrefix("§c[REC] ");
                    rec.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
                }
                rec.addEntry(p.getName());
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (!getSettings(pl).showScoreboard()) {
                        Team r = pl.getScoreboard().getTeam("recording");
                        if (r != null) r.addEntry(p.getName());
                    }
                }
            } else {
                Team t = s.getTeam("team");
                if (t == null) {
                    t = s.registerNewTeam("team");
                    t.setColor(ChatColor.DARK_PURPLE);
                    t.setPrefix(ChatColor.DARK_PURPLE +"");
                    t.setSuffix(ChatColor.RESET +"");
                    t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
                }
                t.addEntry(p.getName());
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    if (!getSettings(pl).showScoreboard()) {
                        Team r = pl.getScoreboard().getTeam("team");
                        if (r != null) r.addEntry(p.getName());
                    }
                }
            }
        }
    }

    public static void setAllNotAFK() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            setAFK(p,false);
        }
    }

    public static void playerJoin(Player p) {
        for (Player pl : Bukkit.getOnlinePlayers()) {
            if (!getSettings(pl).showScoreboard()) {
                Team rec = pl.getScoreboard().getTeam("recording");
                if (rec != null) {
                    if (getSettings(pl).isRecording()) rec.addEntry(p.getName());
                }
                Team t = pl.getScoreboard().getTeam("team");
                if (t != null) {
                    if (!getSettings(pl).isRecording() && !getSettings(p).isAFK()) {

                    }
                }
            }
        }
    }
}
