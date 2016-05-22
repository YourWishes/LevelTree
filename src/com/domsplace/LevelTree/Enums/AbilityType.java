package com.domsplace.LevelTree.Enums;

import com.domsplace.LevelTree.Bases.LevelTreeEnumBase;
import java.util.ArrayList;
import java.util.List;

public class AbilityType extends LevelTreeEnumBase {
    //Constants
    private static final List<AbilityType> types = new ArrayList<AbilityType>();
    
    public static final AbilityType FAST_BREAK = new AbilityType("Fast Break");
    public static final AbilityType MULTIPLE_DROPS = new AbilityType("Multiple Drops");
    public static final AbilityType BLAST_MINING = new AbilityType("Blast Mining");
    public static final AbilityType LOGGING = new AbilityType("Logging");
    public static final AbilityType RANDOM_DROPS = new AbilityType("Random Drops");
    public static final AbilityType FISHING_SPEED = new AbilityType("Fishing Speed");
    public static final AbilityType FISHING_DROPS = new AbilityType("Fishing Drops");
    public static final AbilityType ARCHERY_STUN = new AbilityType("Archery Stun");
    public static final AbilityType ARCHERY_DAMAGE = new AbilityType("Archery Damage");
    public static final AbilityType POISON_DAMAGE = new AbilityType("Poison Damage");
    public static final AbilityType POISON_INCREASE = new AbilityType("Poison Increase");
    public static final AbilityType CRITICAL_WHIRLWIND = new AbilityType("Critical Whirlwind");
    public static final AbilityType FURNACE_XP_BOOST = new AbilityType("Furnace XP Boost");
    
    //Static
    public static AbilityType getAbilityType(String name) {
        for(AbilityType type : AbilityType.getTypes()) {
            if(type.getType().toLowerCase().contains(name.toLowerCase())) {
                return type;
            }
            
            if(type.getType().toLowerCase().replaceAll(" ", "").contains(name.toLowerCase())) {
                return type;
            }
        }
        
        return null;
    }
    
    private static void registerType(AbilityType type) {
        AbilityType.getTypes().add(type);
    }
    
    public static List<AbilityType> getTypes() {
        return AbilityType.types;
    }
    
    //Instance
    private String type;
    
    public AbilityType(String type) {
        this.type = type;
        AbilityType.registerType(this);
    }
    
    public String getType() {
        return this.type;
    }
}
