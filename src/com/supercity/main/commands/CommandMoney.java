package com.supercity.main.commands;

import com.supercity.main.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandMoney implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player p = (Player)sender;
        if (args.length == 0) {
            p.sendMessage(ChatColor.GOLD + "You currently have " + EconomyManager.getAccount(p).getMoney() + " Lirras in your bank account.");
        } else if (p.isOp()) {
            if (args.length == 1) return false;
            String sc = args[0].toLowerCase();
            OfflinePlayer target = p;
            int a = 0;
            try {
                a = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    p.sendMessage("Unknown player " + args[1]);
                    return true;
                }
                if (sc.equalsIgnoreCase("get")) {
                    p.sendMessage("Player " + target.getName() + " has " + EconomyManager.getAccount(target).getMoney() + " lirras in his bank account.");
                    return true;
                }
                if (args.length > 2) {
                    try {
                        a = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e2) {
                        p.sendMessage("Invalid number '" + args[2] + "'");
                        return true;
                    }
                } else {
                    return false;
                }
            }
            switch (sc) {
                case "add":
                case "give":
                    EconomyManager.getAccount(target).add(a);
                    p.sendMessage("Added " + a + " lirras to " + target.getName() + "'s bank account!");
                    if (p.isOnline()) {
                        ((Player)target).sendMessage("+" + a + " lirras from " + p.getName());
                    }
                    break;
                case "take":
                case "remove":
                    if (EconomyManager.getAccount(target).pay(a)) {
                        p.sendMessage("Removed " + a + "lirras from " + target.getName() + "'s bank account!");
                        if (p.isOnline()) {
                            ((Player) target).sendMessage("-" + a + "lirras taken by " + p.getName());
                        }
                    } else {
                        p.sendMessage(target.getName() + " don't have enough money on his account to pay that amount.");
                    }
                    break;
                    default:
                        p.sendMessage("Unknown sub command " + sc + "!");
                        break;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            String[] sc = {"add","remove","get","give","take"};
            for (String s : sc) {
                if (s.startsWith(args[0].toLowerCase()) || args[0].isEmpty()) {
                    list.add(s);
                }
            }
        }
        if (args.length == 2) {
            for (OfflinePlayer p : EconomyManager.getBankPlayers()) {
                if (p.getName().toLowerCase().startsWith(args[1].toLowerCase()) || args[1].isEmpty()) {
                    list.add(p.getName());
                }
            }
        }
        return list;
    }
}
