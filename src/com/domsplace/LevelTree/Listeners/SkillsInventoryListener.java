package com.domsplace.LevelTree.Listeners;

import com.domsplace.LevelTree.Bases.LevelTreeListenerBase;
import com.domsplace.LevelTree.Objects.Skill;
import com.domsplace.LevelTree.Objects.SkillOption;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class SkillsInventoryListener extends LevelTreeListenerBase {
    @EventHandler
    public void blockSkillsMenu(InventoryClickEvent e) {
        if(!isPlayer(e.getWhoClicked())) {
            return;
        }
        
        Player p = (Player) e.getWhoClicked();
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) {
            return;
        }
        
        if(!Skill.isSkillsInventory(e.getInventory())) {
            return;
        }
        e.setCancelled(true);
        
        if(!e.getSlotType().equals(SlotType.CONTAINER)) {
            return;
        }
        
        Skill clickedSkill = Skill.getSkillFromSlot(e.getSlot());
        if(clickedSkill == null) {
            return;
        }
        
        p.closeInventory();
        p.openInventory(clickedSkill.getInventory(player));
        p.sendMessage(ChatDefault + "Opening " + clickedSkill.getTitle() + ChatDefault + " Skill Tree.");
    }
    
    @EventHandler
    public void blockSkillOption(InventoryClickEvent e) {
        if(!isPlayer(e.getWhoClicked())) {
            return;
        }
        
        Player p = (Player) e.getWhoClicked();
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) {
            return;
        }
        
        Skill skill = Skill.getSkillFromInventory(e.getInventory(), player);
        if(skill == null) {
            return;
        }
        e.setCancelled(true);
        
        if(!e.getSlotType().equals(SlotType.CONTAINER)) {
            return;
        }
        
        SkillOption option = skill.getSkillFromSlot(e.getSlot(), player);
        if(option == null) {
            return;
        }
        
        if(player.hasMaxPurchased(option)) {
            p.sendMessage(ChatError + "You can't purchase this.");
            p.closeInventory();
            p.openInventory(skill.getInventory(player));
            return;
        }
        
        if(!player.hasPreRequisits(option)) {
            p.sendMessage(ChatError + "You can't purchase this.");
            p.closeInventory();
            p.openInventory(skill.getInventory(player));
            return;
        }
        
        if(player.getPoints() < option.getRelativeCost(player)) {
            p.sendMessage(ChatError + "You can't purchase this, you need " + Math.ceil(option.getRelativeCost(player) - player.getPoints()) + " more points.");
            p.closeInventory();
            p.openInventory(skill.getInventory(player));
            return;
        }
        
        p.closeInventory();
        option.runPurchase(player);
        sendMessage(player, "Purchased " + option.getTitle());
        p.openInventory(skill.getInventory(player));
    }
    
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(!isPlayer(e.getPlayer())) return;
        
        Player p = (Player) e.getPlayer();
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return;
        
        Skill skill = Skill.getSkillFromInventory(e.getInventory(), player);
        if(skill == null) return;
        skill.removeInventory(player);
    }
    
    @EventHandler
    public void blockPreviousSkillsMenu(InventoryClickEvent e) {
        if(!isPlayer(e.getWhoClicked())) return;
        
        Player p = (Player) e.getWhoClicked();
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return;
        
        if(!Skill.isPreviousSkillsInventory(e.getInventory())) return;
        e.setCancelled(true);
        
        if(!e.getSlotType().equals(SlotType.CONTAINER)) return;
        
        Skill clickedSkill = Skill.getSkillFromSlot(e.getSlot());
        if(clickedSkill == null) return;
        
        p.closeInventory();
        p.openInventory(clickedSkill.getPrevInventory(player));
        p.sendMessage(ChatDefault + "Opening Purchased Skills for " + clickedSkill.getTitle() + ChatDefault + ".");
    }
    
    @EventHandler
    public void handlePrevSkillsClose(InventoryCloseEvent e) {
        if(!isPlayer(e.getPlayer())) return;
        
        Player p = (Player) e.getPlayer();
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return;
        
        Skill skill = Skill.getPrevSkillFromInventory(e.getInventory(), player);
        if(skill == null) return;
        skill.removePrevInventory(player);
    }
    
    @EventHandler
    public void blockPrevSkillOption(InventoryClickEvent e) {
        if(!isPlayer(e.getWhoClicked())) return;
        
        Player p = (Player) e.getWhoClicked();
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return;
        
        Skill skill = Skill.getPrevSkillFromInventory(e.getInventory(), player);
        if(skill == null) return;
        e.setCancelled(true);
        
        if(!e.getSlotType().equals(SlotType.CONTAINER)) return;
    }
}
