package com.domsplace.LevelTree.Objects;

import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class SkillMaterial extends LevelTreeObjectBase {
    //Constants
    public static final SkillMaterial TNT = new SkillMaterial(Material.TNT.getId());
    
    //Static
    public static List<SkillMaterial> getMaterialsFromList(List<String> strings) {
        List<SkillMaterial> materials = new ArrayList<SkillMaterial>();
        for(String s : strings) {
            SkillMaterial sm = new SkillMaterial(s);
            materials.add(sm);
        }
        
        return materials;
    }

    public static SkillMaterial getMaterialFromBlock(Block block) {
        int id = block.getTypeId();
        byte data = block.getData();
        
        return new SkillMaterial(id, data);
    }
    
    public static boolean containsMaterial(SkillMaterial material, List<SkillMaterial> materials) {
        for(SkillMaterial mat : materials) {
            if(!mat.equals(material)) {
                continue;
            }
            
            return true;
        }
        
        return false;
    }

    public static SkillMaterial fromString(String string) {
        return new SkillMaterial(string);
    }
    
    //Instance
    private int id;
    private byte data;
    
    private Map<Enchantment, Integer> enchants;
    
    public SkillMaterial(int id) {
        this(id, (byte) -1);
    }
    
    public SkillMaterial(int id, byte data) {
        this.id = id;
        this.data = data;
        this.enchants = new HashMap<Enchantment, Integer>();
    }
    
    public SkillMaterial(String line) {
        this.enchants = new HashMap<Enchantment, Integer>();
        String[] enchants = line.split("\\|");
        
        try {
            for(int i = 1; i < enchants.length; i++) {
                String[] spl = enchants[i].split(":");
                Enchantment e = Enchantment.getByName(spl[0].toUpperCase());
                
                if(e == null) {
                    log(enchants[i] + " was an invalid enchantment!");
                    continue;
                }
                
                int lvl = e.getMaxLevel();
                
                if(spl.length > 1) {
                    if(isInt(spl[1])) {
                        lvl = getInt(spl[1]);
                    }
                }
                this.enchants.put(e, lvl);
            }
        } catch(Exception ex) {
        }
        
        String[] parts = enchants[0].split(":");
        id = Integer.parseInt(parts[0]);
        
        if(parts.length > 1) {
            this.data = Byte.parseByte(parts[1]);
        } else {
            this.data = -1;
        }
    }
    
    public int getID() {
        return this.id;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public Map<Enchantment, Integer> getEnchantments() {
        return this.enchants;
    }
    
    public boolean doesRequiresData() {
        return this.getData() < 0;
    }
    
    public boolean compare(SkillMaterial material) {
        boolean intSame = this.getID() == material.getID();
        boolean dataSame = true;
        if(this.doesRequiresData() && material.doesRequiresData()) {
            dataSame = this.getData() == material.getData();
        }
        
        return intSame && dataSame;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof SkillMaterial) {
            return this.compare((SkillMaterial) o);
        }
        
        return super.equals(o);
    }
    
    @Override
    public String toString() {
        return this.getID() + ":" + this.getData();
    }
    
    public Material getAsMaterial() {
        return Material.getMaterial(this.getID());
    }
    
    public MaterialData getAsMaterialData() {
        return this.getAsMaterial().getNewData(this.getData());
    }

    public ItemStack getAsItemStack(int stackSize) {
        ItemStack is =  this.getAsMaterialData().toItemStack(stackSize);
        
        for(Enchantment e : this.getEnchantments().keySet()) {
            is.addUnsafeEnchantment(e, this.getEnchantments().get(e));
        }
        
        return is;
    }
}
