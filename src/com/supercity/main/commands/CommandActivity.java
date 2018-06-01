package com.supercity.main.commands;

import com.supercity.main.recording.ActivityException;
import com.supercity.main.recording.ActivityManager;
import com.supercity.main.utils.Reference;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class CommandActivity implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(Reference.CANT_USE_COMMAND);
            return true;
        }
        try {
            if (args.length > 0) {
                if ("previous".equalsIgnoreCase(args[0])) {
                    ActivityManager.displayLastWeek(commandSender);
                } else {
                    try {
                        int i = Integer.parseInt(args[0]);
                        if (args.length > 1) {
                            ActivityManager.displayActivityOf(args[1],i,commandSender);
                        } else {
                            ActivityManager.displayWeek(i, commandSender);
                        }
                    } catch (NumberFormatException e) {
                        ActivityManager.displayActivityOf(args[0],commandSender);
                    }
                }
                return true;
            }
            ActivityManager.displayCurrentWeek(commandSender);
        } catch (ActivityException e) {
            commandSender.sendMessage(e.getMessage());
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            list.add("previous");
        }
        if (strings.length < 3) {
            list.addAll(ActivityManager.getPlayers());
        }
        return list;
    }

}
