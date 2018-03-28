package com.redsponge.extron.plus.utils;

import com.redsponge.extron.plus.item.CustomItem;
import com.redsponge.extron.plus.item.HeatWalkerBoots;
import com.redsponge.extron.plus.item.PickaxeCurseOfBreaking;
import com.redsponge.extron.plus.jetpack.ItemJetpack;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Reference {

    public static final String TRAPPED_SIGN_NAME = "trapped_sign";

    public static final String CANT_USE_COMMAND = ChatColor.DARK_RED + "You cannot use this command!";
    public static final String INVALID_ITEM_LOC_NAME = ChatColor.DARK_RED + "%name is not a valid name!";
    public static final String SUCCESSFULLY_ADDED_ITEM  = ChatColor.GREEN + "Item %name was added to your inventory!";
    public static final String JETPACK_NO_FUEL_IN_INVENTORY = ChatColor.RED + "You do not have any fuel in your inventory!";
    public static String DEATH_MESSAGE_JETPACK = "%name enjoyed jetpacks too much";

    public enum ItemData {
        JETPACK(Material.ELYTRA, "§6Jetpack", "itemJetpack", true, ItemJetpack.class, "§eTo use, equip it and look down", "§eYou also need some fuel in your inventory"),
        PICK(Material.DIAMOND_PICKAXE, "DiamondPickaxe", "BreakingPickaxe", false, PickaxeCurseOfBreaking.class, "§cCurse of Breaking"),
        HEATWALK_BOOTS(Material.DIAMOND_BOOTS,"§cHeatWalker Boots","heatwalkerboots",true, HeatWalkerBoots.class,"§7Heat Walker II");

        private String displayName, locName;
        private boolean hasEnchantedGlint;
        private Material material;
        private String[] lore;
        private boolean hasLore;
        private Class<? extends CustomItem> itemClass;

        ItemData(Material material, String displayName, String locName, boolean hasEnchantedGlint, Class<? extends CustomItem> itemClass, String... lore) {
            this.material = material;
            this.displayName = displayName;
            this.locName = locName;
            this.lore = lore;
            this.hasEnchantedGlint = hasEnchantedGlint;
            this.itemClass = itemClass;
            System.out.println(locName);
            hasLore = true;
        }

        ItemData(Material material, String displayName, String locName, Class<? extends CustomItem> itemClass, String... lore) {
            this(material, displayName, locName, false, itemClass, lore);
        }

        ItemData(Material material, String displayName, String locName, Class<? extends CustomItem> itemClass) {
            this(material, displayName, locName,itemClass,  null);
            hasLore = false;
        }

        ItemData(Material material, String displayName, String locName, boolean hasEnchantedGlint, Class<? extends CustomItem> itemClass) {
            this(material, displayName, locName,hasEnchantedGlint, itemClass, null);
            hasLore = false;
        }

        public String getLocName() {
            return locName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public boolean hasEnchantedGlint() {
            return hasEnchantedGlint;
        }

        public boolean hasLore() {
            return hasLore;
        }

        public String[] getLore() {
            return lore;
        }

        public Material getMaterial() {
            return material;
        }

        public static List<String> getAllLocalizedNames() {
            List<String> l = new ArrayList<>();
            for(ItemData data : ItemData.values()) {
                l.add(data.locName);
                System.out.println(data.locName);
            }
            return l;
        }

        public static CustomItem getItemFromLocName(String locName) {
            try {
                for (ItemData data : ItemData.values()) {
                    if (data.getLocName().equals(locName)) {
                        return data.itemClass.getConstructor().newInstance();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

