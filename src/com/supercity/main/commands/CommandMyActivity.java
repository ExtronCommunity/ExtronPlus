package com.supercity.main.commands;

import com.supercity.main.recording.ActivityException;
import com.supercity.main.recording.ActivityManager;
import com.supercity.main.utils.Reference;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMyActivity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            ActivityManager.displayActivityOf(commandSender.getName(),commandSender);
            int ticks = ActivityManager.getActivityOf(commandSender.getName(),0);
            if (ActivityManager.toHours(ticks) < Reference.HOURS_PER_WEEK) {
                commandSender.sendMessage(ChatColor.YELLOW + "Hour goal needed: " + Reference.HOURS_PER_WEEK);
                commandSender.sendMessage(ChatColor.YELLOW + "Ticks left: " + ((Reference.HOURS_PER_WEEK * 60 * 60 * 20) - ticks));
            }
        } catch (ActivityException e) {
            commandSender.sendMessage(e.getMessage());
        }
        return true;
    }
}
