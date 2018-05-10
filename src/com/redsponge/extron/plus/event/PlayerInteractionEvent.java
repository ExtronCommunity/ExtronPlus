package com.redsponge.extron.plus.event;

import com.redsponge.extron.plus.inventory.HandItems;
import com.redsponge.extron.plus.utils.Reference.ItemData;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractionEvent implements Listener{

    /**
     * Called when player clicks air!
     */
    public void onPlayerClickAir(Player p, HandItems heldItems, Action a){
        if(a.name().contains("LEFT")) onPlayerLeftClickAir(p, heldItems);
        else if(a.name().contains("RIGHT")) onPlayerRightClickAir(p, heldItems);
    }

    /**
     * Called when player clicks block!
     */
    public void onPlayerClickBlock(Player p, Block clickedBlock, BlockFace clickedFace, Action a) {
        if(a.name().contains("LEFT")) onPlayerLeftClickBlock(p, clickedBlock, clickedFace);
        else if(a.name().contains("RIGHT")) onPlayerRightClickBlock(p, clickedBlock, clickedFace);
    }

    /**
     * Called when player right clicks air AND holds an item (off or main hand)
     */
    public void onPlayerRightClickAir(Player player, HandItems heldItems) {
        //player.sendMessage("Right Click Air!");
        if(heldItems.getRightHandItem().hasItemMeta() && heldItems.getRightHandItem().getItemMeta().hasLocalizedName()) {
            if(heldItems.getRightHandItem().getItemMeta().getLocalizedName().equals(ItemData.CRAFTING_TABLE_STICK.getLocName())) {
                player.openWorkbench(player.getLocation(), true);
            }
        }



    }

    /**
     * Called when player punches air
     */
    public void onPlayerLeftClickAir(Player player, HandItems heldItems) {
        //player.sendMessage("Left Click Air!");
    }

    /**
     * Called when player punches block
     */
    public void onPlayerLeftClickBlock(Player player, Block block, BlockFace clickedFace) {
        //player.sendMessage("Left Click Block!");
    }

    /**
     * Called when player right clicks block
     */
    public void onPlayerRightClickBlock(Player player, Block block, BlockFace clickedFace) {
        //player.sendMessage("Right Click Block!");
        ItemStack rightHandItem = player.getInventory().getItemInMainHand();
        if(rightHandItem.hasItemMeta() && rightHandItem.getItemMeta().hasLocalizedName()) {
            if(rightHandItem.getItemMeta().getLocalizedName().equals(ItemData.CRAFTING_TABLE_STICK.getLocName())) {
                player.openWorkbench(player.getLocation(), true);
            }
        }
    }

    /**
     * Called when player interacts with physical objects (only pressure plates for now)
     * see onPlayerRightClickBlock(Player player, Block block, BlockFace clickedFace) for right clickable blocks
     */
    public void onPlayerClickPressurePlate(Player player) {
        //player.sendMessage("Pressure Plate!");
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        Action a = event.getAction();
        //p.sendMessage("INTERACT!");
        if(a.name().contains("AIR")) {
            onPlayerClickAir(p, new HandItems(p.getInventory().getItemInMainHand(), p.getInventory().getItemInOffHand()), a);
        } else if(a.name().contains("BLOCK")) {
            onPlayerClickBlock(p, event.getClickedBlock(), event.getBlockFace(), a);
        } else if(a.name().contains("PHYSICAL")) {
            onPlayerClickPressurePlate(p);
        }
    }

}
