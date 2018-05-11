package com.supercity.main.commands;

import com.supercity.main.SuperCity;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandDontSkipNight implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(SuperCity.INSTANCE.getOnePlayerSleepHandler().getInBed().isEmpty()) {
            commandSender.sendMessage("§cNo one is currently sleeping");
        }
        else if(SuperCity.INSTANCE.getOnePlayerSleepHandler().isDoSkipNight()) {
            SuperCity.INSTANCE.getOnePlayerSleepHandler().setDoSkipNight(false);
            Bukkit.broadcastMessage("§cOne player sleep was disabled for this night by " + commandSender.getName());
            for(Player p : SuperCity.INSTANCE.getOnePlayerSleepHandler().getInBed()) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20, 1, false, false));
            }
        }
        return true;
    }
}
