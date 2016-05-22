package com.domsplace.LevelTree.Listeners;

import com.domsplace.LevelTree.Bases.LevelTreeBase;
import com.domsplace.LevelTree.Bases.LevelTreeListenerBase;
import com.domsplace.LevelTree.Events.PlayerLeftGameEvent;
import com.domsplace.LevelTree.Objects.Skill;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;

public class PlayerListener extends LevelTreeListenerBase {
    @EventHandler
    public void registerFirstJoin(PlayerJoinEvent e) {
        SkillPlayer.registerPlayer(e.getPlayer());
        
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) return;
        boolean maxHealth = e.getPlayer().getMaxHealth() == e.getPlayer().getHealth();
        e.getPlayer().setMaxHealth(player.getMaxHealth());
        if(maxHealth) e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
    }
    
    @EventHandler
    public void playerLeaveGame(PlayerLeftGameEvent e) {
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) {
            return;
        }
        SkillPlayer.savePlayer(player);
    }
    
    @EventHandler(ignoreCancelled=true)
    public void fixHearts(PlayerRespawnEvent e) {
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) return;
        boolean maxHealth = e.getPlayer().getMaxHealth() == e.getPlayer().getHealth();
        e.getPlayer().setMaxHealth(player.getMaxHealth());
        if(maxHealth) e.getPlayer().setHealth(e.getPlayer().getMaxHealth());
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleLastDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() == null) return;
        if(!isPlayer(e.getEntity())) return;
        
        Player p = (Player) e.getEntity();
        long now = System.currentTimeMillis();
        if(LevelTreeBase.lastDamage.containsKey(p)) LevelTreeBase.lastDamage.remove(p);
        
        LevelTreeBase.lastDamage.put(p, now);
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() == null) return;
        if(e.getDamager() == null) return;
        
        if(!isPlayer(e.getEntity()) && !isPlayer(e.getDamager())) return;
        
        if(isPlayer(e.getEntity()) && isPlayer(e.getDamager())) {
            //PvP
            
            Player damager = (Player) e.getDamager();
            SkillPlayer damagePlayer = SkillPlayer.getPlayer(damager);
            if(damagePlayer == null) {
                return;
            }
            
            double oldXP = damagePlayer.getXP();
            damagePlayer.addXP(4 * e.getDamage());
            checkLevelUp(damagePlayer, oldXP);
            
            return;
        }
        
        if(!isPlayer(e.getDamager())) return;
        Player damager = (Player) e.getDamager();
        SkillPlayer damagePlayer = SkillPlayer.getPlayer(damager);
        if(damagePlayer == null) {
            return;
        }
        double oldXP = damagePlayer.getXP();
        damagePlayer.addXP(2 * e.getDamage());
        checkLevelUp(damagePlayer, oldXP);
    }
    
    private void checkLevelUp(SkillPlayer player, double oldXP) {
        double oldPoints = Math.floor(SkillPlayer.getPointsFromXP(oldXP));
        double newPoints = player.getPoints(true);
        
        if(oldPoints >= newPoints) return;
        
        if(!player.getPlayer().isOnline()) return;
        
        sendMessage(player, "You gained a Skill Point!");
        if(inDanger(player.getPlayer().getPlayer())) return;
        Inventory inv = Skill.getSkillsInventory();
        sendMessage(player, "You have " + ChatImportant + player.getPoints(true) + ChatDefault + " points to spend.");
        player.getPlayer().getPlayer().openInventory(inv);
    }
}
