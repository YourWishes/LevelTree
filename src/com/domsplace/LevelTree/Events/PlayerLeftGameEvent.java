package com.domsplace.LevelTree.Events;

import com.domsplace.LevelTree.Bases.LevelTreeCustomEventBase;
import com.domsplace.LevelTree.Enums.LeaveType;
import org.bukkit.entity.Player;

public class PlayerLeftGameEvent extends LevelTreeCustomEventBase {
    private Player player;
    private LeaveType type;
    
    public PlayerLeftGameEvent(Player player, LeaveType type) {
        this.player = player;
        this.type = type;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public LeaveType getType() {
        return this.type;
    }
}
