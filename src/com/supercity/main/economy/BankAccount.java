package com.supercity.main.economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BankAccount {

    private OfflinePlayer owner;
    private int money;
    private UUID accountID;

    public BankAccount(OfflinePlayer owner, UUID accountID) {
        this.owner = owner;
        this.accountID = accountID;
        this.money = 0;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean pay(int amount) {
        if (this.money >= amount) {
            this.money -= amount;
            return true;
        }
        return false;
    }

    public void add(int amount) {
        this.money += amount;
    }

    public int getMoney() {
        return money;
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public UUID getAccountID() {
        return accountID;
    }
}
