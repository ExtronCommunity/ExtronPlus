package com.redsponge.extron.plus.event;

import com.redsponge.extron.plus.inventory.HandItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractionEvent implements Listener {

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();
        if(a.name().contains("AIR")) {
            onPlayerClickAir(p, null, a);
        }
    }

    public void onPlayerClickAir(Player p, HandItems heldItems, Action a){

    }

    public void onPlayerRightClickAir(Player player, HandItems heldItems) {

    }

    public void onPlayerLeftClickAir(Player player, HandItems heldItems) {

    }

}
