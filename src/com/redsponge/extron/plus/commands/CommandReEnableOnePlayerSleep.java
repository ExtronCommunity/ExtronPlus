package com.redsponge.extron.plus.commands;

import com.redsponge.extron.plus.ExtronPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReEnableOnePlayerSleep implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender.isOp()) {
            if(ExtronPlus.INSTANCE.getOnePlayerSleepHandler().isDoSkipNight()) {
                commandSender.sendMessage("§cSadly, one player sleep is currently enabled..");
            } else {
                Bukkit.broadcastMessage("§aOne player sleep was re-enabled by " + commandSender.getName());
                ExtronPlus.INSTANCE.getOnePlayerSleepHandler().setDoSkipNight(true);
            }
        } else {
            commandSender.sendMessage("§You cannot use this command!");
        }
        return true;
    }
}
