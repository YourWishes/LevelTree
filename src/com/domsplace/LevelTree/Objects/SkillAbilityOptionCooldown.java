package com.domsplace.LevelTree.Objects;

import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import java.util.ArrayList;
import java.util.List;

public class SkillAbilityOptionCooldown extends LevelTreeObjectBase {
    private SkillAbilityOption ability;
    private List<SkillPlayer> playerCooldowns;
    
    public SkillAbilityOptionCooldown(SkillAbilityOption ability) {
        this.ability = ability;
        this.playerCooldowns = new ArrayList<SkillPlayer>();
    }
    
    public SkillAbilityOption getAbility() {
        return this.ability;
    }
    
    public List<SkillPlayer> getPlayers() {
        return this.playerCooldowns;
    }
    
    public boolean needsCooldown(SkillPlayer player) {
        return this.getPlayers().contains(player);
    }
    
    public void removePlayer(SkillPlayer player) {
        this.getPlayers().remove(player);
    }
    
    public void addPlayer(SkillPlayer player) {
        this.getPlayers().add(player);
    }
}
