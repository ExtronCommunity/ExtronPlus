package com.supercity.main.creepers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreeperCheck implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player) || !sender.isOp()) {
            return false;
        }
        Player p = (Player) sender;
        Block b = p.getTargetBlock(null,120);
        if (b == null) {
            p.sendMessage("You must look at a block to check if it was exploded by a creeper.");
            return true;
        }
        ExplosionInfo info = CreeperChecker.getExplosionAt(b.getLocation());
        if (info == null) {
            p.sendMessage(ChatColor.RED + "No creeper explosion info found at this block.");
        } else {
            p.sendMessage(ChatColor.GREEN + "A creeper explosion was found!");
            p.sendMessage("  ID: " + info.getId());
            p.sendMessage("  Location: " + b.getLocation().getBlockX() + ", " + b.getLocation().getBlockY() + ", " + b.getLocation().getBlockZ());
            p.sendMessage(String.format("  Date: %1$td/%1$tm/%1$ty at %1$tH:%1$tM:%1$tS",info.getDate()));
            p.sendMessage("  Cause: " + Bukkit.getPlayer(info.getCause()).getName());
        }
        return true;
    }
}
