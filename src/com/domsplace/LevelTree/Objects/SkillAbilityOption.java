package com.domsplace.LevelTree.Objects;

import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import com.domsplace.LevelTree.Enums.AbilityType;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SkillAbilityOption extends LevelTreeObjectBase {
    public static List<SkillAbilityOption> registeredSkillAbilityOptions = new ArrayList<SkillAbilityOption>();

    public static PotionEffect makeEffect(SkillAbilityOption option, SkillPlayer player, SkillOption parent) {
        PotionEffectType pet;
        AbilityType t = option.getType();
        if(t.equals(AbilityType.FAST_BREAK)) {
            pet = PotionEffectType.FAST_DIGGING;
        } else {
            pet = PotionEffectType.CONFUSION;
        }
        
        int duration = player.getSkillAbility(parent) * 20 * parent.getMultiplier();
        int amp = player.getSkillAbility(parent);
        
        PotionEffect pe = new PotionEffect(pet, duration, amp);
        
        return pe;
    }
    
    private AbilityType type;
    private double percent;
    private List<SkillMaterial> materials;
    public List<SkillMaterial> otherMaterials;
    public int seconds;
    
    public SkillAbilityOption(AbilityType type) {
        this.type = type;
        this.materials = new ArrayList<SkillMaterial>();
        registeredSkillAbilityOptions.add(this);
    }
    
    public double getPercent() {
        return this.percent;
    }
    
    public AbilityType getType() {
        return this.type;
    }
    
    public List<SkillMaterial> getMaterials() {
        return this.materials;
    }
    
    public void setMaterials(List<SkillMaterial> materials) {
        this.materials = new ArrayList<SkillMaterial>(materials);
    }
    
    public void setPercent(double percent) {
        this.percent = percent;
    }

    public boolean isMaterial(SkillMaterial material) {
        return SkillMaterial.containsMaterial(material, this.getMaterials());
    }
}
