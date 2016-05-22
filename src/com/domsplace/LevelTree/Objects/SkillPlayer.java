package com.domsplace.LevelTree.Objects;

import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class SkillPlayer extends LevelTreeObjectBase {
    public static final Map<OfflinePlayer, SkillPlayer> players = new HashMap<OfflinePlayer, SkillPlayer>();
    
    public static double getXPFromPoints(double points) {
        return points * LevelTreeObjectBase.pointsCurve;
    }
    
    public static double getPointsFromXP(double xp) {
        return xp / LevelTreeObjectBase.pointsCurve;
    }
    
    public static SkillPlayer getPlayer(OfflinePlayer player) {
        if(!players.containsKey(player)) {
            return null;
        }
        
        return players.get(player);
    }
    
    public static SkillPlayer getPlayer(Player player) {
        return getPlayer(Bukkit.getOfflinePlayer(player.getName()));
    }
    
    public static boolean registerPlayer(Player player) {
        return registerPlayer(Bukkit.getOfflinePlayer(player.getName()));
    }
    
    public static boolean registerPlayer(OfflinePlayer player) {
        if(players.containsKey(player)) {
            return false;
        }
        try {
            SkillPlayer plyr = new SkillPlayer(player);
            
            return true;
        } catch(Exception ex ) {
            return false;
        }
    }
    
    public static void savePlayer(SkillPlayer player) {
        player.save();
    }
    
    //Instance
    private OfflinePlayer player;
    private double xp;
    private Map<SkillOption, Integer> skills;
    private double maxHealth = 20d;
    
    public SkillPlayer(OfflinePlayer player) {
        this(player, 0d);
    }
    
    public SkillPlayer(OfflinePlayer player, double xp) {
        this.player = player;
        this.xp = xp;
        this.skills = new HashMap<SkillOption, Integer>();
        SkillPlayer.players.put(player, this);
        this.maxHealth = getPlugin().configManager.yml.getDouble("health.default");
    }
    
    public OfflinePlayer getPlayer() {
        return this.player;
    }
    
    public double getMaxHealth() {
        return this.maxHealth;
    }
    
    public SkillPlayer setMaxHealth(double max) {
        this.maxHealth = max;
        if(this.getPlayer().isOnline()) this.getPlayer().getPlayer().setMaxHealth(this.getMaxHealth());
        return this;
    }
    
    public SkillPlayer addMaxHealth(double amt) {
        return this.setMaxHealth(this.getMaxHealth() + amt);
    }
    
    public double getXP() {
        return this.xp;
    }
    
    public SkillPlayer setXP(double xp) {
        this.xp = xp;
        return this;
    }
    
    public SkillPlayer addXP(double xp) {
        return this.setXP(this.getXP() + xp);
    }
    
    public double getPoints() {
        return getPoints(false);
    }
    
    public double getPoints(boolean round) {
        double points = SkillPlayer.getPointsFromXP(this.getXP());
        if(round) {
            points = Math.floor(points);
        }
        
        return points;
    }
    
    public SkillPlayer setPoints(double points) {
        return this.setXP(SkillPlayer.getXPFromPoints(points));
    }
    
    public SkillPlayer addPoints(double points) {
        return this.setPoints(this.getPoints() + points);
    }
    
    public SkillPlayer removePoints(double points) {
        return this.addPoints(-points);
    }
    
    public double pointDifference(int cost) {
        return pointDifference(cost, false);
    }
    
    public Map<SkillOption, Integer> getSkillAbilities() {
        return this.skills;
    }
    
    public int getSkillAbility(SkillOption ability) {
        if(!this.getSkillAbilities().containsKey(ability)) {
            return 0;
        }
        
        return this.getSkillAbilities().get(ability);
    }
    
    public void setSkillAbilityLevel(SkillOption ability, int amt) {
        if(this.getSkillAbilities().containsKey(ability)) {
            this.getSkillAbilities().remove(ability);
        }
        
        this.getSkillAbilities().put(ability, amt);
    }
    
    public void addSkillAbilityLevel(SkillOption ability, int amt) {
        this.setSkillAbilityLevel(ability, this.getSkillAbility(ability) + amt);
    }

    public double pointDifference(int cost, boolean round) {
        double t = this.getPoints(round) - cost;
        double p = cost - this.getPoints(round);
        
        return Math.min(t, p);
    }

    public boolean hasPurchased(SkillOption so) {
        if(!this.getSkillAbilities().containsKey(so)) return false;
        if(this.getSkillAbility(so) < 1) return false;
        return true;
    }

    public boolean hasMaxPurchased(SkillOption so) {
        if(so.getMax() == -1) {
            return false;
        }
        
        return this.getSkillAbility(so) >= so.getMax();
    }
    
    public boolean hasRequisit(SkillOption so) {
        return hasRequisit(so, -1);
    }
    
    public boolean hasRequisit(SkillOption so, int lvl) {
        if(!this.getSkillAbilities().containsKey(so)) {
            return false;
        }
        
        int l = this.getSkillAbility(so);
        if(l < 1) {
            return false;
        }
        
        if(lvl > 0) {
            return l >= lvl;
        }
        
        return true;
    }
    
    public boolean hasPreRequisits(SkillOption skilloption) {
        Map<SkillOption, Integer> minReqs = skilloption.getPreRequisits();
        for(SkillOption so : minReqs.keySet()) {
            int i = minReqs.get(so);
            if(this.hasRequisit(so, i)) {
                continue;
            }
            return false;
        }
        return true;
    }
    
    public void save() {
        getPlugin().playerManager.savePlayer(this);
    }
}
