package com.supercity.main.enchants;

import com.supercity.main.SuperCity;
import com.supercity.main.event.result.BlockBreakResult;
import com.supercity.main.event.result.EntityResult;
import com.supercity.main.event.result.DamageResult;
import com.supercity.main.event.result.MoveResult;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CustomEnchant implements Listener {

    public static CustomEnchant INSTANCE;

    public CustomEnchant() {
        INSTANCE = this;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract int getMaxLevel();

    public abstract EnchantmentTarget getTarget();

    public abstract boolean hasConflict(Enchantment e);

    public abstract boolean hasConflict(CustomEnchant e);

    public Player getPlayer(Entity e) {
        if (e instanceof Player) {
            return (Player)e;
        }
        return null;
    }

    public abstract boolean isTreasure();

    public abstract boolean isCursed();

    public abstract EnchRarity getRarity();

    public ToolType getToolType() {
        switch (getTarget()) {
            case ALL:
                return ToolType.ALL;
            case ARMOR:
                return ToolType.ARMOR;
            case ARMOR_FEET:
                return ToolType.FEET;
            case ARMOR_LEGS:
                return ToolType.LEGS;
            case ARMOR_TORSO:
                return ToolType.CHEST;
            case ARMOR_HEAD:
                return ToolType.HEAD;
            case WEAPON:
                return ToolType.HOLDABLE;
            case TOOL:
                return ToolType.HOLDABLE;
            case BOW:
                return ToolType.HOLDABLE;
            case FISHING_ROD:
                return ToolType.HOLDABLE;
            case BREAKABLE:
                return ToolType.ALL;
        }
        return ToolType.ALL;
    }

    public int getEnchantLevel(Player p) {
        ToolType type = this.getToolType();
        ItemStack[] stacks = type.holding(p.getInventory());
        for (ItemStack s : stacks) {
            int i = this.getLevel(s);
            if (i > 0) {
                return i;
            }
        }
        return 0;
    }

    private int getLevel(ItemStack item) {
        NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
        if (tag != null) {
            NBTTagList list = tag.getList("CustomEnchs", 10);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    NBTTagCompound e = list.get(i);
                    String id = e.getString("id");
                    if (id != null && id.equalsIgnoreCase(this.getId())) {
                        return e.getInt("lvl");
                    }
                }
            }
        }
        return 0;
    }

    @EventHandler
    public final void damage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (this.has((Player) e.getDamager())) {
                if (e.getEntity() instanceof Player) {
                    DamageResult result = this.onUseAtPlayer((Player) e.getDamager(), (Player) e.getEntity(), e.getDamage());
                    result.modify(e);
                } else {
                    DamageResult result = this.onUseAtEntity((Player)e.getDamager(),e.getEntity(),e.getDamage());
                    result.modify(e);
                }
            }
        } else if (e.getEntity() instanceof Player) {
            if (this.has((Player) e.getEntity())) {
                DamageResult result = this.onAttackedByEntity((Player) e.getEntity(), e.getDamager(), e.getDamage());
                result.modify(e);
            }
        }
    }

    @EventHandler
    public final void breakBlock(BlockBreakEvent e) {
        if (this.has(e.getPlayer())) {
            BlockBreakResult result = this.onBreakBlock(e.getPlayer(),e.getBlock(),e.isDropItems(),e.getExpToDrop());
            result.modify(e);
        }
    }

    @EventHandler
    public final void shootArrow(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            if (this.has((Player) e.getEntity())) {
                EntityResult result = this.onShootArrow((Player)e.getEntity(),e.getBow(),e.getForce(),e.getProjectile());
                result.modify(e);
            }
        }
    }

    protected EntityResult onShootArrow(Player shooter, ItemStack bow, float force, Entity projectile) {
        return EntityResult.DEFAULT;
    }

    protected BlockBreakResult onBreakBlock(Player player, Block block, boolean dropItems, int expToDrop) {
        return BlockBreakResult.DEFAULT;
    }

    @EventHandler
    public final void move(PlayerMoveEvent e) {
        if (this.has(e.getPlayer())) {
            MoveResult result = this.onMove(e.getPlayer(), e.getFrom(), e.getTo());
            result.modify(e);
        }
    }

    public boolean has(Player p) {
        int level = this.getEnchantLevel(p);
        return level > 0;
    }

    @EventHandler
    public final void durability(PlayerItemDamageEvent e) {
        if (this.has(e.getPlayer())) {
            DamageResult result = this.onLoseDurability(e.getPlayer(),e.getItem(),e.getDamage());
            result.modify(e);
        }
    }

    protected DamageResult onLoseDurability(Player player, ItemStack item, int damage) {
        return DamageResult.DEFAULT;
    }

    protected MoveResult onMove(Player player, Location from, Location to) {
        return MoveResult.DEFAULT;
    }

    protected DamageResult onAttackedByEntity(Player target, Entity damager, double damage) {
        return DamageResult.DEFAULT;
    }

    protected DamageResult onUseAtEntity(Player damager, Entity target, double damage) {
        return DamageResult.DEFAULT;
    }

    protected DamageResult onUseAtPlayer(Player damager, Player target, double damage) {
        return DamageResult.DEFAULT;
    }

    public boolean isValid(EnchantItemEvent e) {
        if (e.isCancelled()) return false;
        if (e.getEnchantsToAdd().size() == 0) return false;
        if (e.getEnchantsToAdd().size() > 6) return false;
        if (this.isTreasure()) return false;
        for (Enchantment ench : e.getEnchantsToAdd().keySet()) {
            if (hasConflict(ench)) {
                return false;
            }
        }
        return this.getTarget().includes(e.getItem().getType());
    }

    public boolean isCustomValid(List<CustomEnchant> enchs) {
        for (CustomEnchant ench : enchs) {
            if (this.hasConflict(ench)) {
                return false;
            }
        }
        return true;
    }

    public void addToItem(ItemStack item, int level) {
        if (level <= 0) {
            System.out.println("cant add level 0 enchants!");
            return;
        }
        NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        if (this.getLevel(item) == 0) {
            NBTTagList list = tag.getList("CustomEnchs",10);
            NBTTagCompound enchTag = new NBTTagCompound();
            enchTag.setString("id",this.getId());
            enchTag.setInt("lvl",level);
            list.add(enchTag);
            tag.set("CustomEnchs",list);
            Bukkit.getUnsafe().modifyItemStack(item,tag.toString());
            ItemMeta m = item.getItemMeta();
            if (m != null) {
                List<String> lore = m.getLore();
                if (lore == null) {
                    lore = new ArrayList<>();
                }
                lore.add((isCursed() ? "§c" : "§7") + this.getName() + " " + getLevelSuffix(level,this.getMaxLevel()));
                m.setLore(lore);
                item.setItemMeta(m);
            }
        }
    }

    public static String getLevelSuffix(int level, int maxLevel) {
        if (maxLevel == 1) {
            return "";
        } else if (level == 1) {
            return "I";
        } else if (level == 2) {
            return "II";
        } else if (level == 3) {
            return "III";
        } else if (level == 4) {
            return "IV";
        } else {
            return level == 5 ? "V" : String.valueOf(level);
        }
    }

    public static void addTo(ItemStack stack,int level) {
        INSTANCE.addToItem(stack,level);
    }

    public boolean tryAddingToHeld(Player p, int level) {
        ItemStack s = p.getInventory().getItemInMainHand();
        if (s.getType() == Material.AIR) {
            return false;
        }
        if (this.getLevel(s) > 0) {
            return false;
        }
        for (Enchantment e : s.getEnchantments().keySet()) {
            if (this.hasConflict(e)) {
                return false;
            }
        }
        for (CustomEnchant e : CustomEnchant.getCustomEnchants(CraftItemStack.asNMSCopy(s))) {
            if (this.hasConflict(e)) {
                return false;
            }
        }
        if (!this.getTarget().includes(s.getType())) {
            return false;
        }
        this.addToItem(s,level);
        return true;
    }

    private static List<CustomEnchant> getCustomEnchants(net.minecraft.server.v1_12_R1.ItemStack nms) {
        List<CustomEnchant> list = new ArrayList<>();
        NBTTagCompound tag = nms.getTag();
        if (tag == null) {
            return list;
        }
        NBTTagList enchs = tag.getList("CustomEnchs",10);
        if (enchs == null) {
            return list;
        }
        for (int i = 0; i < enchs.size(); i++) {
            NBTTagCompound c = enchs.get(i);
            if (c != null) {
                String id = c.getString("id");
                if (id != null) {
                    CustomEnchant enchant = SuperCity.INSTANCE.getCustomEnchant(id);
                    if (enchant != null) {
                        list.add(enchant);
                    }
                }
            }
        }
        return list;
    }

    public static boolean hasCustomEnchants(ItemStack stack) {
        return CraftItemStack.asNMSCopy(stack).getTag() != null && CraftItemStack.asNMSCopy(stack).getTag().hasKey("CustomEnchs");
    }

    public enum ToolType {
        HOLDABLE {
            @Override
            public ItemStack[] holding(PlayerInventory inv) {
                return new ItemStack[] {inv.getItemInMainHand()};
            }
        },
        ARMOR {
            @Override
            public ItemStack[] holding(PlayerInventory inv) {
                return inv.getArmorContents();
            }
        },
        FEET {
            @Override
            public ItemStack[] holding(PlayerInventory inv) {
                return new ItemStack[] {inv.getBoots()};
            }
        },
        LEGS {
            @Override
            public ItemStack[] holding(PlayerInventory inv) {
                return new ItemStack[] {inv.getLeggings()};
            }
        },
        CHEST {
            @Override
            public ItemStack[] holding(PlayerInventory inv) {
                return new ItemStack[] {inv.getChestplate()};
            }
        },
        HEAD {
            @Override
            public ItemStack[] holding(PlayerInventory inv) {
                return new ItemStack[] {inv.getHelmet()};
            }
        }, ALL {
            @Override
            public ItemStack[] holding(PlayerInventory inv) {
                List<ItemStack> stacks = new ArrayList<>(Arrays.asList(HOLDABLE.holding(inv)));
                stacks.addAll(Arrays.asList(ARMOR.holding(inv)));
                return stacks.toArray(new ItemStack[0]);
            }
        };

        public abstract ItemStack[] holding(PlayerInventory inv);
    }

}