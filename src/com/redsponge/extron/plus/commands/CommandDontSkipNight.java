package com.redsponge.extron.plus.commands;

import com.redsponge.extron.plus.ExtronPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDontSkipNight implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(ExtronPlus.INSTANCE.getSleepSkippingHandler().isDoSkipNight()) {
            ExtronPlus.INSTANCE.getSleepSkippingHandler().setDoSkipNight(false);
            Bukkit.broadcastMessage("§cOne player sleep was disabled by " + commandSender.getName());
        }
        return true;
    }
}
