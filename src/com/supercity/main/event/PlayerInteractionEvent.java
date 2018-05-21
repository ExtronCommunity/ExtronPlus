package com.supercity.main.event;

import com.supercity.main.blocks.CustomBlockStorage;
import com.supercity.main.item.ItemBackPack;
import com.supercity.main.SuperCity;
import com.supercity.main.config.ConfigManager;
import com.supercity.main.crops.CropRightClickManager;
import com.supercity.main.inventory.HandItems;
import com.supercity.main.utils.Reference;
import com.supercity.main.utils.Reference.ItemData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Sign;
import org.bukkit.scheduler.BukkitRunnable;

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
            } else if(ItemBackPack.isBackpack(heldItems.getRightHandItem())) {
                if(!ItemBackPack.hasId(heldItems.getRightHandItem())) {
                    ItemBackPack.assignID(heldItems.getRightHandItem());
                }
                ItemBackPack.displayToPlayer(player, ItemBackPack.getBackpackId(heldItems.getRightHandItem()));
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
        if (CustomBlockStorage.onRightClickBlock(player,block,clickedFace)) return;
        ItemStack rightHandItem = player.getInventory().getItemInMainHand();
        if(rightHandItem.hasItemMeta() && rightHandItem.getItemMeta().hasLocalizedName()) {
            if(rightHandItem.getItemMeta().getLocalizedName().equals(ItemData.CRAFTING_TABLE_STICK.getLocName())) {
                player.openWorkbench(player.getLocation(), true);
            } else if(ItemBackPack.isBackpack(rightHandItem)) {
                if(!ItemBackPack.hasId(rightHandItem)) {
                    ItemBackPack.assignID(rightHandItem);
                }
                ItemBackPack.displayToPlayer(player, ItemBackPack.getBackpackId(rightHandItem));
            }
        } else if(Reference.CROPS.contains(block.getType())) {
            if (CropRightClickManager.isMature(block)) {
                CropRightClickManager.handleCropClick(block);
            }
        }
        if (block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST) {
            if (player.isSneaking()) {
                if (rightHandItem.getType() == Material.TRIPWIRE_HOOK) {
                    if (!Reference.TRAPPED_SIGNS_LOCATIONS.contains(block.getLocation())) {
                        rightHandItem.setAmount(rightHandItem.getAmount() - 1);
                        if (rightHandItem.getAmount() > 0) {
                            player.getInventory().setItemInMainHand(rightHandItem);
                        } else {
                            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        }
                        Location loc = block.getLocation();
                        Reference.TRAPPED_SIGNS_LOCATIONS.add(loc);
                        FileConfiguration config = ConfigManager.trappedSignsConfig.get();
                        int i = config.getKeys(false).size();
                        config.set(i + ".x",loc.getBlockX());
                        config.set(i + ".y",loc.getBlockY());
                        config.set(i + ".z",loc.getBlockZ());
                        config.set(i + ".world",loc.getWorld().getName());
                    }
                }
            } else {
                if (Reference.TRAPPED_SIGNS_LOCATIONS.contains(block.getLocation())) {
                    activate(block,(Sign)block.getState().getData());
                }
            }
        }
    }

    private void activate(Block b, Sign sign) {
        Block rb;
        if (sign.isWallSign()) {
            rb = b.getRelative(sign.getAttachedFace());
        } else {
            rb = b.getLocation().getBlock();
        }
        Material m = rb.getType();
        byte data = rb.getData();
        BlockState state = rb.getState();
        rb.setType(Material.REDSTONE_BLOCK);
        new BukkitRunnable() {
            @Override
            public void run() {
                rb.setType(m);
            }
        }.runTaskLater(SuperCity.INSTANCE,20);
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
