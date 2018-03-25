package com.redsponge.extron.plus.event;

import com.redsponge.extron.plus.ExtronPlus;
import com.redsponge.extron.plus.utils.Reference;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleShiftEvent implements Listener {

    private void onPlayerStartSneaking(Player p) {
        if(ExtronPlus.INSTANCE.getJetpackHandler().wearingJetpack(p) && !ExtronPlus.INSTANCE.getJetpackHandler().hasFuelInInventory(p) && !ExtronPlus.INSTANCE.getJetpackHandler().hasFuel(p)) {
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

}
