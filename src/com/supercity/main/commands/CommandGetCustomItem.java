package com.supercity.main.commands;

import com.supercity.main.utils.Reference;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandGetCustomItem implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player) || !commandSender.isOp()) {
            commandSender.sendMessage(Reference.CANT_USE_COMMAND);
            return true;
        }
        if(!Reference.ItemData.getAllLocalizedNames().contains(strings[0])) {
            commandSender.sendMessage(Reference.INVALID_ITEM_LOC_NAME.replaceAll("%name", strings[0]));
            return true;
        }
        Player p = (Player) commandSender;
        p.getInventory().addItem(Reference.ItemData.getItemFromLocName(strings[0]));
        p.sendMessage(Reference.SUCCESSFULLY_ADDED_ITEM.replaceAll("%name", strings[0]));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player) || !commandSender.isOp()) {
            return Collections.emptyList();
        }
        return Reference.ItemData.getAllLocalizedNames();
    }
}
