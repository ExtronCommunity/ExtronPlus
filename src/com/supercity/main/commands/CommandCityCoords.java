package com.supercity.main.commands;

import com.supercity.main.config.ConfigManager;
import com.supercity.main.utils.Reference;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCityCoords implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length != 3 && args.length != 1 || !commandSender.isOp()) {
            int x = ConfigManager.cityDataConfig.get().getInt(Reference.CITY_COORDS_CONFIG_PATH + ".x");
            int y = ConfigManager.cityDataConfig.get().getInt(Reference.CITY_COORDS_CONFIG_PATH + ".y");
            int z = ConfigManager.cityDataConfig.get().getInt(Reference.CITY_COORDS_CONFIG_PATH + ".z");
            commandSender.sendMessage("The city coords are: ");
            commandSender.sendMessage(ChatColor.GREEN + "" + x + " " + y + " " + z);
        } else if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            int x;
            int y;
            int z;
            x = y = z = 0;
            if(args.length == 1 && args[0].equals("here")) {
                x = (int) p.getLocation().getX();
                y = (int) p.getLocation().getY();
                z = (int) p.getLocation().getZ();
            } else if(args.length == 3) {
                x = Integer.parseInt(args[0]);
                y = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
            } else {
                return false;
            }
            ConfigManager.cityDataConfig.get().set(Reference.CITY_COORDS_CONFIG_PATH + ".x", x);
            ConfigManager.cityDataConfig.get().set(Reference.CITY_COORDS_CONFIG_PATH + ".y", y);
            ConfigManager.cityDataConfig.get().set(Reference.CITY_COORDS_CONFIG_PATH + ".z", z);
            ConfigManager.cityDataConfig.save();
        }
        return true;
    }
}
