package com.supercity.main.commands;

import com.supercity.main.recording.ActivityManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.ints.IntComparator;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class CommandPlayed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective played = sb.getObjective("played");
        if (played == null) {
            commandSender.sendMessage("No played objective found!");
            return true;
        }
        commandSender.sendMessage("             Played");
        LinkedList<String> list = sb.getEntries().stream().sorted(Comparator.comparingInt(this::getScore)).collect(Collectors.toCollection(LinkedList::new));
        Iterator<String> iter = list.descendingIterator();
        while (iter.hasNext()) {
            String e = iter.next();
            OfflinePlayer p = Bukkit.getOfflinePlayer(e);
            if (p != null) {
                Score score = played.getScore(e);
                commandSender.sendMessage(ChatColor.AQUA + e + "    " + ChatColor.RED + score.getScore());
            }
        }
        return true;
    }

    private int getScore(String p) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective played = sb.getObjective("played");
        return played == null ? 0 : played.getScore(p).getScore();
    }

    private int getMaxScore(Scoreboard sb, Objective played) {
        int max = 0;
        for (String s : sb.getEntries()) {
            int i = played.getScore(s).getScore();
            if (i > max) {
                max = i;
            }
        }
        return max;
    }
}
