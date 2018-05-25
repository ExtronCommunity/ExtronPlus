package com.supercity.main.commands;

import com.supercity.main.recording.RecordingManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRecording implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if (args.length == 0) {
            if (RecordingManager.getSettings(p).isRecording()) {
                RecordingManager.setRecording(p, false);
            } else {
                RecordingManager.setRecording(p, true);
            }
        } else if (args[0].equalsIgnoreCase("status")) {
            p.sendMessage("RECORDING: " + RecordingManager.getSettings(p).isRecording());
            p.sendMessage("SCOREBOARD: " + RecordingManager.getSettings(p).showScoreboard());
            p.sendMessage("CHAT: " + RecordingManager.getSettings(p).showChat());
            p.sendMessage("BED ALERTS: " + RecordingManager.getSettings(p).sendSleepAlerts());
        } else if (args[0].equalsIgnoreCase("enable") && sender.isOp()) {
            RecordingManager.enable();
        } else if (args[0].equalsIgnoreCase("disable") && sender.isOp()) {
            RecordingManager.disable();
        }
        return true;
    }
}
