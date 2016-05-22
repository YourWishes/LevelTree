package com.domsplace.LevelTree.Events;

import com.domsplace.LevelTree.Bases.LevelTreeCustomEventBase;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerCriticalHitEvent extends LevelTreeCustomEventBase {
    private Player killer;
    private Entity target;
    private double damage;
    
    public PlayerCriticalHitEvent(Player player, Entity hit, double damageDealt) {
        this.killer = player;
        this.target = hit;
        this.damage = damageDealt;
    }
    
    public Player getPlayer() {
        return this.killer;
    }
    
    public Entity getHit() {
        return this.target;
    }
    
    public double getDamage() {
        return this.damage;
    }
}
