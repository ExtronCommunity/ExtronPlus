package com.supercity.main.enchants;

import com.supercity.main.event.result.BlockBreakResult;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.omg.CORBA.DATA_CONVERSION;

public class AutoSmelt extends CustomEnchant {
    @Override
    public String getId() {
        return "auto_smelt";
    }

    @Override
    public String getName() {
        return "Auto Smelt";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean hasConflict(Enchantment e) {
        return e == Enchantment.SILK_TOUCH || e == Enchantment.LOOT_BONUS_BLOCKS;
    }

    @Override
    public boolean hasConflict(CustomEnchant e) {
        return false;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public EnchRarity getRarity() {
        return EnchRarity.EPIC;
    }

    @Override
    public BlockBreakResult onBreakBlock(Player player, Block block, boolean dropItems, int expToDrop) {
        if (dropItems) {
            System.out.println("break block");
            Material newItem = null;
            int damage = 0;
            switch (block.getType()) {
                case IRON_ORE:
                    newItem = Material.IRON_INGOT;
                    break;
                case LOG:
                    newItem = Material.COAL;
                    damage = 1;
                    break;
                case LOG_2:
                    newItem = Material.COAL;
                    damage = 1;
                    break;
                case GOLD_ORE:
                    newItem = Material.GOLD_INGOT;
                    break;
                case SAND:
                    newItem = Material.GLASS;
                    break;
                case STONE:
                    newItem = Material.STONE;
                    break;
                case CLAY:
                    newItem = Material.HARD_CLAY;
                    break;
                case NETHERRACK:
                    newItem = Material.CLAY_BRICK;
                    break;
                case BRICK:
                    newItem = Material.BRICK;
                    damage = 2;
                    break;
                case CACTUS:
                    newItem = Material.INK_SACK;
                    damage = 2;
                    break;
                case SPONGE:
                    if (block.getData() == 1) {
                        newItem = Material.SPONGE;
                        damage = 0;
                    }
                    break;
            }
            if (newItem != null) {
                block.getWorld().dropItemNaturally(block.getLocation(),new ItemStack(newItem,1,(short)damage));
                return new BlockBreakResult(false,false,expToDrop);
            }
        }
        return super.onBreakBlock(player, block, dropItems, expToDrop);
    }
}
