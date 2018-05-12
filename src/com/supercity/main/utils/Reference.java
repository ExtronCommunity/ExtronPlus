package com.supercity.main.utils;

import com.supercity.main.backpack.ItemBackPack;
import com.supercity.main.config.ConfigManager;
import com.supercity.main.item.CraftingTableStick;
import com.supercity.main.item.CustomItem;
import com.supercity.main.item.HeatWalkerBoots;
import com.supercity.main.item.PickaxeCurseOfBreaking;
import com.supercity.main.jetpack.ItemJetpack;
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
    public static final String PLAYER_ENTERED_BED = "§a%player is now sleeping.";
    public static final String ANOTHER_PLAYER_ENTERED_BED = "§a%player is also sleeping";
    public static final String PLAYER_3_ENTERED_BED = "§a%player saw %player1 and %player2 are §asleeping, §aand wanted to join the party";
    public static final String CANCEL_SLEEP = "§4[NO]";
    public static final String CANCEL_SLEEP_2_PEOPLE = "§4[PLS NO]";
    public static final String CANCEL_SLEEP_3_PEOPLE = "§4[NO I WANT THE NIGHT]";
    public static final String[] PLAYER_4_ENTERED_BED = {"§aSuddenly, %player hopped into a bed","§a%player was tired","§a%player was afraid of phantoms, and so he went to sleep"};
    public static final String[] CANCEL_SLEEP_4_PEOPLE = {"§4[...]","§4[WHY EVEN, SOMEONE ELSE IS ALREADY SLEEPING]","§4[BUT I WANT DA NITE PLZ]","§4[IM HUNTING MONSTERS.. DO NOT DISTURB]"};
    public static final String CANCEL_SLEEP_HOVER_MESSAGE = "Click me to cancel one player sleep for this night";
    public static final String DEATH_MESSAGE_JETPACK = "%name enjoyed jetpacks too much";
    public static final String PLAYER_STARTED_RECORDING = "%player §cis now recording.";
    public static final String PLAYER_STOPPED_RECORDING = "%player §ais no longer recording.";
    public static final String MYSELF_STARTED_RECORDING = "You have started recording!";
    public static final String MYSELF_STOPPED_RECORDING = "You have stopped recording!";
    public static final String CHAT_SHOWN = "The chat is now visible!";
    public static final String CHAT_HIDDEN = "Chat is now hidden. F3 + D to clear current chat!";

    public static final int ONE_PLAYER_SLEEP_COOLDOWN = 80;
    public static final int AFK_TIMER_LIMIT = 1200;


    public static final int ROWS_IN_BACKPACK = ConfigManager.backpackConfig.get().getInt("rows", 2);

    public enum ItemData {
        JETPACK(Material.ELYTRA, "§6Jetpack", "itemJetpack", true, ItemJetpack.class, "§eTo use, equip it and look down", "§eYou also need some fuel in your inventory"),
        PICK(Material.DIAMOND_PICKAXE, "DiamondPickaxe", "BreakingPickaxe", false, PickaxeCurseOfBreaking.class, "§cCurse of Breaking"),
        CRAFTING_TABLE_STICK(Material.STICK, "§6Crafting Table on a stick", "craftingTableStick", false, CraftingTableStick.class),
        BACKPACK(Material.LEATHER, "§6Backpack", "backpack", false, ItemBackPack.class),
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
            this(material, displayName, locName,itemClass, null);
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

