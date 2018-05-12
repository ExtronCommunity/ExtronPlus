package com.supercity.main.event;

import com.supercity.main.SuperCity;
import com.supercity.main.utils.Reference;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleShiftEvent implements Listener {

    private void onPlayerStartSneaking(Player p) {
        if(SuperCity.INSTANCE.getJetpackHandler().wearingJetpack(p) && !SuperCity.INSTANCE.getJetpackHandler().hasFuelInInventory(p) && !SuperCity.INSTANCE.getJetpackHandler().hasFuel(p)) {
            p.sendMessage(Reference.JETPACK_NO_FUEL_IN_INVENTORY);
        }
    }

    private void onPlayerStopSneaking(Player p) {

    }


    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();
        if(!p.isSneaking()) {
            onPlayerStartSneaking(p);
        } else {
            onPlayerStopSneaking(p);
        }
    }

    @EventHandler
    public void onPlayerPickItem(EntityPickupItemEvent e) {
        System.out.println(e.getItem().getItemStack().hashCode());
    }

}
