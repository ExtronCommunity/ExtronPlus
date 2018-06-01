package com.supercity.main.commands;

import com.supercity.main.recording.ActivityException;
import com.supercity.main.recording.ActivityManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandMyActivity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try {
            ActivityManager.displayActivityOf(commandSender.getName(),commandSender);
        } catch (ActivityException e) {
            commandSender.sendMessage(e.getMessage());
        }
        return true;
    }
}
