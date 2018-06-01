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
        for (String e : sb.getEntries()) {
            OfflinePlayer p = Bukkit.getOfflinePlayer(e);
            if (p != null) {
                Score score = played.getScore(e);
                commandSender.sendMessage(String.format(ChatColor.AQUA + "%-18s  " + ChatColor.RED + "%d",e,score.getScore()));
            }
        }
        return true;
    }
}
