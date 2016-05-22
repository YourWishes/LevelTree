package com.domsplace.LevelTree.Listeners;

import com.domsplace.LevelTree.Bases.LevelTreeListenerBase;
import com.domsplace.LevelTree.Enums.LeaveType;
import com.domsplace.LevelTree.Events.PlayerCriticalHitEvent;
import com.domsplace.LevelTree.Events.PlayerLeftGameEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class CustomEventListener extends LevelTreeListenerBase {
    
    @EventHandler
    public void onPlayerKicked(PlayerKickEvent e) {
        PlayerLeftGameEvent event = new PlayerLeftGameEvent(e.getPlayer(), LeaveType.PLAYER_KICKED);
        event.fireEvent();
    }
    
    @EventHandler
    public void onPlayerLeaveGame(PlayerQuitEvent e) {
        PlayerLeftGameEvent event = new PlayerLeftGameEvent(e.getPlayer(), LeaveType.PLAYER_QUIT);
        event.fireEvent();
    }
    
    @EventHandler(ignoreCancelled=true)
    public void onCriticalHit(EntityDamageByEntityEvent e) {
        if(e.isCancelled()) return;
        if(e.getDamager() == null) return;
        if(e.getEntity() == null) return;
        if(!isPlayer(e.getDamager())) return;
        
        Player p = (Player) e.getDamager();
        if(p.isInsideVehicle()) return;
        if(p.isSneaking()) return;
        if(p.getVelocity().getY() >= -0.5) return;
        if(p.getLocation().getBlock() != null && !p.getLocation().getBlock().getType().equals(Material.AIR)) return;
        if(p.hasPotionEffect(PotionEffectType.BLINDNESS)) return;
        
        PlayerCriticalHitEvent event = new PlayerCriticalHitEvent(p, e.getEntity(), e.getDamage());
        event.fireEvent();
        if(event.isCancelled()) e.setCancelled(true);
    }
}
