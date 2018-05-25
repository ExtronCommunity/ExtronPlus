package com.supercity.main.economy;

import com.supercity.main.item.ItemLirra;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EconomyManager {

    private static Map<UUID,BankAccount> accounts = new HashMap<>();

    public static void init() {

    }


    public static BankAccount getAccount(OfflinePlayer p) {
        BankAccount a = accounts.get(p.getUniqueId());
        if (a == null) {
            a = new BankAccount(p,UUID.randomUUID());
            //accounts.put(p.getUniqueId(),a);
        }
        return a;
    }

    public static void deposit(Player p, ItemStack stack) {
        if (!ItemLirra.isLirra(stack)) return;
        int value = ItemLirra.getValue(stack);
        getAccount(p).add(value);
    }

    public static List<OfflinePlayer> getBankPlayers() {
        List<OfflinePlayer> list = new ArrayList<>();
        for (BankAccount acc : accounts.values()) {
            list.add(acc.getOwner());
        }
        return list;
    }
}
