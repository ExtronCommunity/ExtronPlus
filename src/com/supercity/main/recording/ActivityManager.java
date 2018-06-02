package com.supercity.main.recording;

import com.supercity.main.config.ConfigManager;
import com.supercity.main.utils.ListUtils;
import com.supercity.main.utils.Reference;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class ActivityManager {

    private static Scoreboard sb;
    private static Objective weekPlayed;
    private static Date lastReset;
    private static int weekID;
    private static Map<Integer,Map<String,Integer>> scoreMap;

    public static void init() {
        sb = Bukkit.getScoreboardManager().getMainScoreboard();
        weekPlayed = getOrCreateObjective();
        long last = ConfigManager.config.getLong("last_week_reset",-1);
        if (last == -1) {
            lastReset = new Date();
            ConfigManager.config.set("last_week_reset",lastReset.getTime());
        } else {
            lastReset = new Date(last);
        }
        weekID = ConfigManager.config.getInt("week_id",-1);
        if (weekID == -1) {
            weekID = 0;
            ConfigManager.config.set("week_id",weekID);
        }
        scoreMap = new HashMap<>();
        ConfigurationSection data = ConfigManager.config.getConfigurationSection("activity_data");
        if (data != null) {
            for (String s : data.getKeys(false)) {
                try {
                    int i = Integer.parseInt(s);
                    Map<String,Integer> map = new HashMap<>();
                    for (String entry : data.getConfigurationSection(s).getKeys(false)) {
                        int score = data.getConfigurationSection(s).getInt(entry);
                        map.put(entry,score);
                    }
                    scoreMap.put(i,map);

                } catch (Exception ignored) {

                }
            }
        }
        ConfigManager.saveConfig();
    }

    private static Objective getOrCreateObjective() {
        Objective o = sb.getObjective(getObjectiveName());
        if (o == null) {
            o = sb.registerNewObjective(getObjectiveName(),"stat.playOneMinute");
        }
        return o;
    }

    private static String getObjectiveName() {
        return "played_week";
    }

    public static void reset() {
        weekPlayed.unregister();
        weekPlayed = getOrCreateObjective();
        lastReset = new Date();
        saveCurrentWeek();
        weekID++;
        ConfigManager.config.set("week_id",weekID);
        ConfigManager.config.set("last_week_reset",lastReset.getTime());
        ConfigManager.saveConfig();
    }

    public static void save() {
        saveCurrentWeek();
        ConfigManager.saveConfig();
    }

    private static void saveCurrentWeek() {
        Map<String,Integer> map = new HashMap<>();
        for (String p : sb.getEntries()) {
            if (Bukkit.getOfflinePlayer(p) == null) continue;
            Score s = weekPlayed.getScore(p);
            map.put(p,s.getScore());
            ConfigManager.config.set("activity_data." + weekID + "." + p,s.getScore());
        }
        scoreMap.put(weekID,map);
    }


    public static void tick() {
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) < lastReset.getDay()) {
            reset();
        }
    }

    private static int getCurrentScore(String s) {
        Score score = weekPlayed.getScore(s);
        return score.getScore();
    }

    public static void displayCurrentWeek(CommandSender sender) throws ActivityException {
        displayWeek(0,sender);
    }

    public static void displayLastWeek(CommandSender sender) throws ActivityException {
        displayWeek(1,sender);
    }

    public static void displayActivityOf(String s, CommandSender sender) throws ActivityException {
        displayActivityOf(s,0,sender);
    }

    public static void displayActivityOf(String s, int weeksAgo, CommandSender sender) throws ActivityException {
        int i = getActivityOf(s,weeksAgo);
        sender.sendMessage(getDisplayTitle(weeksAgo,s));
        sendActivity(sender, s, i);
    }

    private static String getDisplayTitle(int weeksAgo, String player) {
        if (weeksAgo == 0) {
            return "Activity of " + player + " for the current week: (#" + weekID + ")";
        } else if(weeksAgo == 1) {
            return "Activity of " + player + "for the previous week: (#" + (weekID-1) + ")";
        }
        return "Activity of " + player + " " + weeksAgo + " weeks ago: (#" + (weekID-weeksAgo) + ")";
    }

    private static String getDisplayTitle(int weeksAgo) {
        if (weeksAgo == 0) {
            return "Activity for the current week: (#" + weekID + ")";
        } else if(weeksAgo == 1) {
            return "Activity for the previous week: (#" + (weekID-1) + ")";
        }
        return "Activity of " + weeksAgo + " weeks ago: (#" + (weekID-weeksAgo) + ")";
    }

    public static void displayWeek(int weeksAgo, CommandSender sender) throws ActivityException {
        boolean success = false;
        for (String s : sb.getEntries()) {
            int i = getActivityOf(s,weeksAgo);
            if (!success) {
                sender.sendMessage(getDisplayTitle(weeksAgo));
            }
            success = true;
            sendActivity(sender,s,i);
        }
    }

    public static int getActivityOf(String p, int weeksAgo) throws ActivityException {
        if (p == null || !ListUtils.containsIgnoreCase(sb.getEntries(),p)) throw new ActivityException("Unknown player " + p);
        if (weeksAgo == 0) {
            return getCurrentScore(p);
        } else {
            int week = weekID - weeksAgo;
            Map<String,Integer> m = scoreMap.get(week);
            if (m == null) {
                throw new ActivityException("No activity data for that week!");
            } else {
                Integer i = m.get(p);
                if (i == null) {
                    throw new ActivityException("No activity data for " + p + " that week!");
                } else {
                    return i;
                }
            }
        }
    }

    private static void sendActivity(CommandSender sender, String p, int ticks) {
        int h = toHours(ticks);
        ChatColor c = h < Reference.HOURS_PER_WEEK ? ChatColor.RED : ChatColor.GREEN;
        int m = toMinutes(ticks) - 60 * h;
        if (h == 0) {
            sender.sendMessage(c + p + ": " + m + "m");
        } else {
            sender.sendMessage(c + p + ": " + String.format("%,d",h) + (m == 0 ? "" : ":" + String.format("%02d",m)) + "h");
        }
    }

    private static int toMinutes(int i) {
        return (int) Math.floor(i / 20.0 / 60.0);
    }

    public static int toHours(int i) {
        return (int) Math.floor(toMinutes(i)/60.0);
    }

    public static Set<String> getPlayers() {
        return sb.getEntries();
    }
}
