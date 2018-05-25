package com.supercity.main.commands;

import com.supercity.main.SuperCity;
import com.supercity.main.enchants.CustomEnchant;
import com.supercity.main.utils.Reference;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandCustomEnchant implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(!(commandSender instanceof Player) || !commandSender.isOp()) {
            commandSender.sendMessage(Reference.CANT_USE_COMMAND);
            return true;
        }
        Player p = (Player)commandSender;
        if (args.length == 0) {
            return false;
        }
        String id = args[0];
        CustomEnchant ench = SuperCity.INSTANCE.getCustomEnchant(id);
        if (ench == null) {
            p.sendMessage("Unknown custom enchantment!");
            return true;
        }
        int level = 1;
        if (args.length > 1) {
            try {
                level = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                p.sendMessage("Invalid enchantment level!");
            }
        }
        if (ench.tryAddingToHeld(p,level)) {
            p.sendMessage("Enchanting succeed!");
        } else {
            p.sendMessage("Unable to enchant held item");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (strings.length == 1) {
            for (CustomEnchant e : SuperCity.INSTANCE.customEnchants) {
                if (strings[0].equals("") || e.getId().toLowerCase().startsWith(strings[0].toLowerCase())) {
                    list.add(e.getId());
                }
            }
        }
        return list;
    }
}
