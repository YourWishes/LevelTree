package com.domsplace.LevelTree.Objects;

import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Skill extends LevelTreeObjectBase {
    public static final List<Skill> skills = new ArrayList<Skill>();
    
    public static String SkillsName = "Skill Tree";
    public static String PreviousSkillsName = "Purchased Skills";
    private static Inventory skillsInventory;
    private static Inventory previousSkillsInventory;
    
    private static void registerSkill(Skill skill) {
        Skill.skills.add(skill);
    }
    
    public static boolean isSkillsInventory(Inventory inv) {
        return Skill.getSkillsInventory().getName().equals(inv.getName());
    }
    
    public static boolean isPreviousSkillsInventory(Inventory inv) {
        return Skill.getPreviousSkillsInventory().getName().equals(inv.getName());
    }
    
    public static Inventory getSkillsInventory() {
        if(skillsInventory != null) {
            return skillsInventory;
        }
        
        Inventory inv = Bukkit.createInventory(null, 27, SkillsName);
        
        for(Skill skill : skills) {
            inv.addItem(skill.getItemStack());
        }
        
        skillsInventory = inv;
        return inv;
    }

    public static Inventory getPreviousSkillsInventory() {
        if(previousSkillsInventory != null) return previousSkillsInventory;
        
        Inventory inv = Bukkit.createInventory(null, 27, PreviousSkillsName);
        
        for(Skill skill : skills) {
            inv.addItem(skill.getItemStack());
        }
        
        previousSkillsInventory = inv;
        return previousSkillsInventory;
    }
    
    public static Skill getSkillFromSlot(int slot) {
        try {
            return skills.get(slot);
        } catch(Exception e) {
            return null;
        }
    }

    public static Skill getSkill(String string) {
        if(string == null) {
            return null;
        }
        
        for(Skill s : Skill.skills) {
            if(s == null) {
                continue;
            }
            
            if(!s.getName().toLowerCase().contains(string.toLowerCase())) {
                continue;
            }
            
            return s;
        }
        
        return null;
    }

    public static Skill getSkillFromInventory(Inventory inventory, SkillPlayer player) {
        for(Skill skill : Skill.skills) {
            if(skill == null) {
                continue;
            }
            
            Inventory inv = skill.getInventory(player);
            if(inv == null) {
                continue;
            }
            
            if(!inv.getName().equalsIgnoreCase(inventory.getName())) {
                continue;
            }
            
            return skill;
        }
        
        return null;
    }

    public static Skill getPrevSkillFromInventory(Inventory inventory, SkillPlayer player) {
        for(Skill skill : Skill.skills) {
            if(skill == null) continue;
            Inventory inv = skill.getPrevInventory(player);
            if(inv == null) continue;
            if(!inv.getName().equalsIgnoreCase(inventory.getName())) continue;
            return skill;
        }
        return null;
    }
    
    public static void reset() {
        if(Skill.skillsInventory != null) {
            for(HumanEntity ent : Skill.skillsInventory.getViewers()) {
                if(ent == null) continue;
                ent.closeInventory();
            }
        }
        if(Skill.previousSkillsInventory != null) {
            for(HumanEntity ent : Skill.previousSkillsInventory.getViewers()) {
                if(ent == null) continue;
                ent.closeInventory();
            }
        }
        
        Skill.previousSkillsInventory = null;
        Skill.skillsInventory = null;
    }
    
    //Instance
    private String name;
    private String title;
    private ItemStack item;
    
    private List<String> descriptions;
    private List<SkillOption> skillOptions;
    
    private Map<SkillPlayer, Inventory> inventories;
    private Map<SkillPlayer, Inventory> prevInventories;
    
    public Skill(String name, String title, ItemStack item, List<String> descriptions) {
        this.name = name;
        this.title = title;
        this.item = item;
        
        this.descriptions = new ArrayList<String>(descriptions);
        this.inventories = new HashMap<SkillPlayer, Inventory>();
        this.prevInventories = new HashMap<SkillPlayer, Inventory>();
        
        skillOptions = new ArrayList<SkillOption>();
        
        Skill.registerSkill(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public List<SkillOption> getSkillOptions() {
        return this.skillOptions;
    }
    
    public List<String> getDescriptions() {
        return this.descriptions;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public ItemStack getItemStack() {
        return this.getItemStack(this.getItem().getAmount());
    }

    public void addOption(SkillOption option) {
        this.skillOptions.add(option);
    }
    
    public ItemStack getItemStack(int stackSize) {
        ItemStack is = new ItemStack(this.getItem());
        ItemMeta im = is.getItemMeta();
        
        im.setDisplayName(this.getTitle());
        im.setLore(this.getDescriptions());
        
        is.setItemMeta(im);
        is.setAmount(stackSize);
        return is;
    }
    
    public void removeInventory(SkillPlayer player) {
        if(!this.inventories.containsKey(player)) {
            return;
        }
        
        this.inventories.remove(player);
    }
    
    public Inventory getInventory(SkillPlayer player) {
        if(this.inventories.containsKey(player)) {
            return this.inventories.get(player);
        }
        
        Inventory inv = Bukkit.createInventory(null, 27, this.getChestTitle());
        
        for(SkillOption so : this.getSkillOptions()) {
            if(player.hasMaxPurchased(so)) {
                continue;
            }
            
            if(!player.hasPreRequisits(so)) {
                continue;
            }
            
            inv.addItem(so.getItemStack(player.getSkillAbility(so)+1));
        }
        
        this.inventories.put(player, inv);
        
        return inv;
    }
    
    public void removePrevInventory(SkillPlayer player) {
        if(!this.prevInventories.containsKey(player)) {
            return;
        }
        
        this.prevInventories.remove(player);
    }
    
    public Inventory getPrevInventory(SkillPlayer player) {
        if(this.prevInventories.containsKey(player)) {
            return this.prevInventories.get(player);
        }
        
        Inventory inv = Bukkit.createInventory(null, 27, this.getPreviousChestTitle());
        
        for(SkillOption so : this.getSkillOptions()) {
            if(!player.hasPurchased(so)) {
                continue;
            }
            
            inv.addItem(so.getItemStack(player.getSkillAbility(so)));
        }
        
        this.prevInventories.put(player, inv);
        
        return inv;
    }
    
    public String getChestTitle() {
        String title = this.getTitle();
        if(title.length() <= 26) {
            return title;
        }
        
        log("Title for " + this.getName() + " was longer than 25 characters.");
        return title.substring(0, 25);
    }
    
    public String getPreviousChestTitle() {
        String title = "+" + this.getTitle();
        if(title.length() <= 26) {
            return title;
        }
        
        log("Title for " + this.getName() + " was longer than 25 characters.");
        return title.substring(0, 25);
    }

    public SkillOption getSkillOptionFromSlot(int slot) {
        try {
            return this.getSkillOptions().get(slot);
        } catch(Exception ex) {
            return null;
        }
    }

    public SkillOption getSkillFromSlot(int slot, SkillPlayer player) {
        Inventory inv = this.getInventory(player);
        if(inv == null) return null;
        
        ItemStack is;
        try {
            is = inv.getItem(slot);
        } catch(Exception ex) {
            return null;
        }
        
        if(is == null) return null;
        
        for(SkillOption so : this.getSkillOptions()) {
            if(so == null) {
                continue;
            }
            
            if(!so.equals(is)) {
                continue;
            }
            
            return so;
        }
        
        return null;
    }

    public SkillOption getPrevSkillFromSlot(int slot, SkillPlayer player) {
        Inventory inv = this.getPrevInventory(player);
        if(inv == null) return null;
        
        ItemStack is;
        try {
            is = inv.getItem(slot);
        } catch(Exception ex) {
            return null;
        }
        
        if(is == null) return null;
        
        for(SkillOption so : this.getSkillOptions()) {
            if(so == null) {
                continue;
            }
            
            if(!so.equals(is)) {
                continue;
            }
            
            return so;
        }
        
        return null;
    }
}
