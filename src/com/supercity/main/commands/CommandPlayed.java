package com.supercity.main.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

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
            OfflinePlayer p = Bukkit.getPlayer(e);
            if (p != null) {
                Score score = played.getScore(e);
                int iScore = score.getScore();
                String msg = ChatColor.AQUA + e;
                int spacesToAdd = 16 - e.length() + 6;
                StringBuilder builder = new StringBuilder(msg);
                for(int i = 0; i < spacesToAdd; i++) {
                    builder.append(" ");
                }
                builder.append(ChatColor.RED);
                builder.append(iScore);
                commandSender.sendMessage(builder.toString());
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
