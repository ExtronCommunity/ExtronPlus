package com.supercity.main.commands;

import com.supercity.main.recording.RecordingManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToggleSB implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return false;
        RecordingManager.toggleScoreboard((Player)sender);
        return true;
    }
}
