package com.domsplace.LevelTree.Listeners;

import com.domsplace.LevelTree.Bases.LevelTreeBase;
import com.domsplace.LevelTree.Bases.LevelTreeListenerBase;
import com.domsplace.LevelTree.Enums.AbilityType;
import com.domsplace.LevelTree.Events.PlayerCriticalHitEvent;
import com.domsplace.LevelTree.Objects.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SkillEffectsListener extends LevelTreeListenerBase {
    private List<SkillAbilityOptionCooldown> cooldowns = new ArrayList<SkillAbilityOptionCooldown>();
    
    public SkillEffectsListener() {
        for(SkillAbilityOption sao : SkillAbilityOption.registeredSkillAbilityOptions) {
            cooldowns.add(new SkillAbilityOptionCooldown(sao));
        }
    }
    
    public SkillAbilityOptionCooldown getSkillAbilityCooldown(SkillAbilityOption sao) {
        for(SkillAbilityOptionCooldown cooldown : cooldowns) {
            if(cooldown.getAbility().equals(sao)) {
                return cooldown;
            }
        }
        
        return null;
    }
    
    @EventHandler
    public void fastBreakCooldown(BlockBreakEvent e) {
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) {
            return;
        }
        
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                if(sa == null) {
                    continue;
                }
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(sao == null) {
                        continue;
                    }
                    
                    if(!sao.getType().equals(AbilityType.FAST_BREAK)) {
                        continue;
                    }
                    
                    this.getSkillAbilityCooldown(sao).removePlayer(player);
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void fastBreakInteract(PlayerInteractEvent e) {
        if(e.getClickedBlock() == null) {
            return;
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) {
            return;
        }
        
        SkillMaterial mat = SkillMaterial.getMaterialFromBlock(e.getClickedBlock());
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            
            int lvl = abilities.get(so);
            if(lvl < 1) {
                continue;
            }
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.FAST_BREAK)) {
                        continue;
                    }
                    
                    if(!sao.isMaterial(mat)) {
                        continue;
                    }
                    
                    SkillAbilityOptionCooldown saoc = this.getSkillAbilityCooldown(sao);
                    if(saoc.needsCooldown(player)) {
                        continue;
                    }
                    
                    saoc.addPlayer(player);
                    
                    boolean check = flipCoin(sao.getPercent() * player.getSkillAbility(so));
                    if(!check) {
                        continue;
                    }
                    
                    e.getPlayer().addPotionEffect(SkillAbilityOption.makeEffect(sao, player, so));
                    notify(player, "Got effect " + sa.getTitle());
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void doubleDropsCheck(BlockBreakEvent e) {
        if(e.getBlock() == null) {
            return;
        }
        
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) {
            return;
        }
        
        SkillMaterial mat = SkillMaterial.getMaterialFromBlock(e.getBlock());
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            
            int lvl = abilities.get(so);
            if(lvl < 1) {
                continue;
            }
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.MULTIPLE_DROPS)) {
                        continue;
                    }
                    
                    if(!sao.isMaterial(mat)) {
                        continue;
                    }
                    
                    boolean check = flipCoin(sao.getPercent() * player.getSkillAbility(so));
                    if(!check) {
                        continue;
                    }
                    
                    Collection<ItemStack> drops = e.getBlock().getDrops();
                    for(ItemStack is : drops) {
                        Item item = e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), is);
                    }
                    
                    notify(player, "Got ability " + sa.getTitle());
                }
            }
        }
    }
    
    @EventHandler
    public void blastMining(EntityExplodeEvent e) {
        if(e.getEntity() == null) {
            return;
        }
        
        if(!e.getEntityType().equals(EntityType.PRIMED_TNT)) return;
        
        TNTPrimed tnt = (TNTPrimed) e.getEntity();
        
        if(!isPlayer(tnt.getSource())) return;
        
        Player p = (Player) tnt.getSource();
        if(p == null) return;
        
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) {
            return;
        }
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            
            int lvl = abilities.get(so);
            if(lvl < 1) {
                continue;
            }
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.BLAST_MINING)) {
                        continue;
                    }
                    
                    e.setYield((float) sao.getPercent() * player.getSkillAbility(so));
                    notify(player, "Got ability " + so.getTitle());
                    return;
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void loggingListener(BlockBreakEvent e) {
        if(e.getBlock() == null) {
            return;
        }
        
        Player p = e.getPlayer();
        if(p == null) return;
        
        if(!isAxe(p.getItemInHand())) {
            return;
        }
        
        if(!isTree(e.getBlock().getType())) {
            return;
        }
        
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) {
            return;
        }
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            
            int lvl = abilities.get(so);
            if(lvl < 1) {
                continue;
            }
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.LOGGING)) {
                        continue;
                    }
                    LogTree(e.getBlock(), e.getPlayer().getItemInHand());
                    notify(player, "Got ability " + so.getTitle());
                    return;
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleFishingSpeed(PlayerFishEvent e) {
        if(!e.getState().equals(State.FISHING)) return;
        if(e.getHook() == null) return;
        
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) {
            return;
        }
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) continue;
            
            int lvl = abilities.get(so);
            if(lvl < 1) continue;
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.FISHING_SPEED)) continue;
                    
                    double percent = sao.getPercent() * player.getSkillAbility(so);
                    
                    e.getHook().setBiteChance(e.getHook().getBiteChance() * (e.getHook().getBiteChance() * (percent / 100d)));
                    
                    notify(player, "Ability " + sa.getTitle() + ChatDefault + " increased fishing chance by " + percent + "%");
                    return;
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleFishDrops(PlayerFishEvent e) {
        if(!e.getState().equals(State.CAUGHT_FISH)) return;
        if(e.getCaught() == null) return;
        
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) {
            return;
        }
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) continue;
            
            int lvl = abilities.get(so);
            if(lvl < 1) continue;
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.FISHING_DROPS)) continue;
                    
                    boolean check = flipCoin(sao.getPercent() * player.getSkillAbility(so));
                    if(!check) continue;
                    
                    List<SkillMaterial> mats = sao.getMaterials();
                    
                    SkillMaterial sm = mats.get(LevelTreeBase.getRandomNumberBetween(0, mats.size()));
                    ItemStack toDrop = sm.getAsItemStack(1);
                    Item i = e.getPlayer().getWorld().dropItem(e.getPlayer().getLocation(), toDrop);
                    Vector v = e.getCaught().getVelocity();
                    i.setVelocity(v);
                    
                    notify(player, "Ability " + sa.getTitle() + ChatDefault + " fished some tresure!");
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleRandomDrops(BlockBreakEvent e) {
        if(e.getBlock() == null) {
            return;
        }
        
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) {
            return;
        }
        
        SkillMaterial mat = SkillMaterial.getMaterialFromBlock(e.getBlock());
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            
            int lvl = abilities.get(so);
            if(lvl < 1) {
                continue;
            }
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.RANDOM_DROPS)) {
                        continue;
                    }
                    
                    if(!SkillMaterial.containsMaterial(mat, sao.otherMaterials)) {
                        continue;
                    }
                    
                    boolean check = flipCoin(sao.getPercent() * player.getSkillAbility(so));
                    if(!check) {
                        continue;
                    }
                    
                    List<SkillMaterial> mats = sao.getMaterials();
                    
                    SkillMaterial sm = mats.get(LevelTreeBase.getRandomNumberBetween(0, mats.size()));
                    ItemStack toDrop = sm.getAsItemStack(1);
                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), toDrop);
                    notify(player, "Got ability " + sa.getTitle());
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleBowStun(EntityDamageByEntityEvent e) {
        if(e.getEntity() == null) return;
        if(e.getDamager() == null) return;
        
        if(!(e.getEntity() instanceof LivingEntity)) return;
        
        if(!e.getDamager().getType().equals(EntityType.ARROW)) return;
        
        Arrow arrow = (Arrow) e.getDamager();
        if(arrow.getShooter() == null) return;
        if(!arrow.getShooter().getType().equals(EntityType.PLAYER)) return;
        
        Player p = (Player) arrow.getShooter();
        
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return;
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            
            int lvl = abilities.get(so);
            if(lvl < 1) {
                continue;
            }
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.ARCHERY_STUN)) continue;
                    
                    boolean check = flipCoin(sao.getPercent());
                    if(!check) continue;
                    
                    int seconds = sao.seconds * player.getSkillAbility(so);
                    
                    LivingEntity t = (LivingEntity) e.getEntity();
                    PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, seconds * 20, 1);
                    t.addPotionEffect(pe);
                    
                    pe = new PotionEffect(PotionEffectType.CONFUSION, seconds * 20, 3);
                    t.addPotionEffect(pe);
                    
                    notify(player, "Dazed enemy due to " + so.getTitle());
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleBowDamage(EntityDamageByEntityEvent e) {
        if(e.getEntity() == null) return;
        if(e.getDamager() == null) return;
        
        if(!(e.getEntity() instanceof LivingEntity)) return;
        
        if(!e.getDamager().getType().equals(EntityType.ARROW)) return;
        
        Arrow arrow = (Arrow) e.getDamager();
        if(arrow.getShooter() == null) return;
        if(!arrow.getShooter().getType().equals(EntityType.PLAYER)) return;
        
        Player p = (Player) arrow.getShooter();
        
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return;
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) {
                continue;
            }
            
            int lvl = abilities.get(so);
            if(lvl < 1) {
                continue;
            }
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.ARCHERY_DAMAGE)) continue;
                    
                    e.setDamage(e.getDamage() + sao.getPercent());
                    notify(player, "Did critical damage to enemy due to " + so.getTitle());
                }
            }
        }
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handlePoisonTouch(EntityDamageByEntityEvent e) {
        if(e.getEntity() == null) return;
        if(e.getDamager() == null) return;
        if(!isPlayer(e.getDamager())) return;
        if(!(e.getEntity() instanceof LivingEntity)) return;
        
        Player p = (Player) e.getDamager();
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return;
        
        int seconds = -1;
        int adv = 1;
        int power = 1;
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) continue;
            int lvl = abilities.get(so);
            if(lvl < 1) continue;
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.POISON_DAMAGE) || !sao.getType().equals(AbilityType.POISON_INCREASE)) continue;
                    
                    if(sao.getType().equals(AbilityType.POISON_DAMAGE)) {
                        boolean check = flipCoin(sao.getPercent() * player.getSkillAbility(so));
                        if(!check) continue;
                        
                        seconds = sao.seconds * player.getSkillAbility(so);
                    } else {
                        adv += sao.getPercent() * player.getSkillAbility(so);
                    }
                }
            }
        }
        
        if(seconds < 1) return;
        LivingEntity ent = (LivingEntity) e.getEntity();

        PotionEffect poison = new PotionEffect(PotionEffectType.POISON, (adv * seconds) * 20, power);
        ent.addPotionEffect(poison);

        notify(player, "Poised target due to ability.");
    }
    
    //Stops StackOverflow
    public static List<Player> whirlwindPlayer = new ArrayList<Player>();
    @EventHandler(ignoreCancelled=true)
    public void handleCriticalHit(PlayerCriticalHitEvent e) {
        if(whirlwindPlayer.contains(e.getPlayer())) return;
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) return;
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) continue;
            int lvl = abilities.get(so);
            if(lvl < 1) continue;
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.CRITICAL_WHIRLWIND)) continue;
                    
                    Player p = e.getPlayer();
                    //Get Nearby ents
                    
                    whirlwindPlayer.add(p);
                    
                    double radius = sao.getPercent() * player.getSkillAbility(so);
                    List<Entity> ents = p.getNearbyEntities(radius, radius, radius);
                    for(Entity ent : ents) {
                        if(ent == null) continue;
                        if(!(ent instanceof LivingEntity)) continue;
                        if(ent.equals(p)) continue;
                        
                        pushAwayEntity(ent, p, 5);
                        LivingEntity le = (LivingEntity) ent;
                        le.damage(e.getDamage(), p);
                    }
                    
                    notify(player, "Ability " + sa.getTitle() + ChatDefault + " sliced nearby entites.");
                }
            }
        }
        
        whirlwindPlayer.remove(e.getPlayer());
    }
    
    @EventHandler(ignoreCancelled=true)
    public void handleXPBoost(FurnaceExtractEvent e) {
        if(e.getPlayer() == null) return;
        if(e.getBlock() == null) return;
        
        SkillPlayer player = SkillPlayer.getPlayer(e.getPlayer());
        if(player == null) return;
        
        Map<SkillOption, Integer> abilities = player.getSkillAbilities();
        for(SkillOption so : abilities.keySet()) {
            if(so == null) continue;
            int lvl = abilities.get(so);
            if(lvl < 1) continue;
            
            List<SkillAbility> abs = so.getAbilities();
            for(SkillAbility sa : abs) {
                List<SkillAbilityOption> sos = sa.getAbilityOptions();
                for(SkillAbilityOption sao : sos) {
                    if(!sao.getType().equals(AbilityType.FURNACE_XP_BOOST)) continue;
                    Player p = e.getPlayer();
                    
                    e.setExpToDrop(e.getExpToDrop() * sao.seconds);
                    notify(player, "Got " + sao.seconds + " times the extracted XP.");
                    return;
                }
            }
        }
    }
}
