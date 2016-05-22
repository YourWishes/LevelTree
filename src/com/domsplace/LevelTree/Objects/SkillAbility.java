package com.domsplace.LevelTree.Objects;

import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import java.util.ArrayList;
import java.util.List;

public class SkillAbility extends LevelTreeObjectBase {
    public static SkillAbility getAbility(String name) {
        for(SkillAbility ability : SkillAbility.getSkillAbilities()) {
            if(ability == null) {
                continue;
            }
            
            if(!ability.getName().toLowerCase().contains(name.toLowerCase())) {
                continue;
            }
            
            return ability;
        }
        
        return null;
    }
    public static SkillAbility getAbilityExact(String name) {
        for(SkillAbility ability : SkillAbility.getSkillAbilities()) {
            if(ability == null) {
                continue;
            }
            
            if(!ability.getName().equals(name)) {
                continue;
            }
            
            return ability;
        }
        
        return null;
    }
    
    private static List<SkillAbility> skillAbilities = new ArrayList<SkillAbility>();
    private static void registerSkillAbility(SkillAbility ability) {
        SkillAbility.skillAbilities.add(ability);
    }
    
    public static List<SkillAbility> getSkillAbilities() {
        return SkillAbility.skillAbilities;
    }
    
    //Instance
    private String name;
    private String title;
    private List<SkillAbilityOption> abilityOptions;
    
    public SkillAbility(String name, String title) {
        this.name = name;
        this.title = title;
        this.abilityOptions = new ArrayList<SkillAbilityOption>();
        SkillAbility.registerSkillAbility(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public List<SkillAbilityOption> getAbilityOptions() {
        return this.abilityOptions;
    }

    public void setAbilityOptions(List<SkillAbilityOption> options) {
        this.abilityOptions = new ArrayList<SkillAbilityOption>(options);
    }
}
