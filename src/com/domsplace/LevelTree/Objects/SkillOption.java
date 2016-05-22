package com.domsplace.LevelTree.Objects;

import com.domsplace.LevelTree.Bases.LevelTreeBase;
import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class SkillOption extends LevelTreeObjectBase {
    //Constants
    private static final List<SkillOption> skillOptions = new ArrayList<SkillOption>();
    
    //Static
    public static SkillOption getSkillOptionByNameExact(String s) {
        for(SkillOption skillOption : SkillOption.getSkillOptions()) {
            if(!skillOption.getName().equals(s)) {
                continue;
            }
            
            return skillOption;
        }
        return null;
    }
    
    public static List<SkillOption> getSkillOptions() {
        return SkillOption.skillOptions;
    }
    
    private static void registerSkillOption(SkillOption option) {
        SkillOption.getSkillOptions().add(option);
    }
    
    //Instance
    private String name;
    private String title;
    private int max;
    private int mutliplier;
    private int cost;
    private ItemStack item;
    
    private List<String> descriptions;
    private List<String> commands;
    private List<SkillAbility> abilities;
    private List<String> dependancies;
    
    public SkillOption(String name, String title, int max, int multiplier, int cost, ItemStack is, 
            List<String> descriptions, List<String> commands, List<String> abilities, List<String> dependancies) {
        
        this(name, title, max, multiplier, cost, is, descriptions, commands, null, dependancies, false);
        
        List<SkillAbility> cAbilities = new ArrayList<SkillAbility>();
        for(String s : abilities) {
            SkillAbility sa = SkillAbility.getAbilityExact(s);
            if(sa == null) {
                continue;
            }
            cAbilities.add(sa);
        }
        this.abilities = cAbilities;
    }
    
    public SkillOption(String name, String title, int max, int multiplier, int cost, ItemStack is, 
            List<String> descriptions, List<String> commands, List<SkillAbility> abilities, List<String> dependancies, boolean parse) {
        this.name = name;
        this.title = title;
        this.cost = cost;
        this.item = is;
        this.max = max;
        this.mutliplier = multiplier;
        
        this.descriptions = new ArrayList<String>(descriptions);
        this.commands = new ArrayList<String>(commands);
        this.dependancies = new ArrayList<String>(dependancies);
        
        if(parse) {
            this.abilities = new ArrayList<SkillAbility>(abilities);
        }
        
        SkillOption.registerSkillOption(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public int getMax() {
        return this.max;
    }
    
    public int getMultiplier() {
        return this.mutliplier;
    }
    
    public int getCost() {
        return this.cost;
    }
    
    public List<String> getDescriptions() {
        return this.descriptions;
    }
    
    public List<String> getCommands() {
        return this.commands;
    }
    
    public List<SkillAbility> getAbilities() {
        return this.abilities;
    }
    
    public ItemStack getItemStack() {
        return this.getItemStack(this.getCost());
    }
    
    public ItemStack getItemStack(int stackSize) {
        ItemStack is = new ItemStack(this.getItem());
        MaterialData md = is.getData();
        md.setData(this.getItem().getData().getData());
        is.setData(md);
        
        ItemMeta im = is.getItemMeta();
        
        im.setDisplayName(this.getTitle());
        im.setLore(this.getDescriptions());
        
        is.setItemMeta(im);
        is.setAmount(stackSize);
        return is;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof ItemStack) {
            return this.isStackSame((ItemStack) o);
        }
        
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    public boolean isStackSame(ItemStack is) {
        ItemStack im = this.getItemStack();
        
        if(im == null) {
            return false;
        }
        
        boolean id = im.getType().equals(is.getType());
        try {
            boolean n = im.getItemMeta().getDisplayName().equalsIgnoreCase(is.getItemMeta().getDisplayName());
            return n && id;
        } catch(Exception e) {
            return id;
        }
    }

    public double getRelativeCost(SkillPlayer player) {
        double c = this.getCost();
        int purchases = player.getSkillAbility(this);
        int multiplier = this.getMultiplier();
        return c * (mutliplier * (purchases) + 1);
    }

    public void runPurchase(SkillPlayer sPlayer) {
        sPlayer.addSkillAbilityLevel(this, 1);
        sPlayer.removePoints(this.getRelativeCost(sPlayer));
        LevelTreeBase.runCommands(this.getCommands(), sPlayer.getPlayer(), sPlayer.getSkillAbility(this));
        sPlayer.save();
    }
    
    public List<String> getDepandancies() {
        return this.dependancies;
    }

    public Map<SkillOption, Integer> getPreRequisits() {
        Map<SkillOption, Integer> d = new HashMap<SkillOption, Integer>();
        for(String s : this.getDepandancies()) {
            String[] split = s.split(":");
            SkillOption so = SkillOption.getSkillOptionByNameExact(split[0]);
            int a = -1;
            try {
                a = Integer.parseInt(split[1]);
            } catch(Exception e) {
            }
            d.put(so, a);
        }
        return d;
    }
}
