package com.supercity.main.commands;

import com.supercity.main.item.ItemBackPack;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandGetBackpack implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player) || !commandSender.isOp()) {
            return true;
        }
        Player p = (Player) commandSender;
        if(strings.length < 1) {
            return false;
        }
        try {
            Integer.parseInt(strings[0]);
        } catch(Exception e) {
            return false;
        }

        int id = Integer.parseInt(strings[0]);
        ItemBackPack backPack = new ItemBackPack();
        ItemBackPack.assignID(backPack, id);
        p.getInventory().addItem(backPack);
        p.sendMessage(ChatColor.GREEN + "Given you backpack " + id);
        return true;
    }
}
