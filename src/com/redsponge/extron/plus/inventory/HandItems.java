package com.redsponge.extron.plus.inventory;

import org.bukkit.inventory.ItemStack;

public class HandItems {

    private ItemStack rightHand, leftHand;

    public HandItems(ItemStack rightHand, ItemStack leftHand) {
        this.rightHand = rightHand;
        this.leftHand = leftHand;
    }

    public ItemStack getRightHandItem() {
        return rightHand;
    }

    public ItemStack getLeftHandItem() {
        return leftHand;
    }
}
