package com.domsplace.LevelTree.Commands;

import com.domsplace.LevelTree.Bases.LevelTreeCommandBase;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddEXP extends LevelTreeCommandBase {
    public CommandAddEXP() {
        super("AddEXP");
    }
    
    @Override
    public boolean commandRecieved(CommandSender sender, Command cmd, String label, String[] args) {
        
        SkillPlayer player;
        
        if(args.length < 0) {
            sender.sendMessage(ChatError + "Please enter a player name.");
            return false;
        }
        
        if(args.length < 1) {
            sender.sendMessage(ChatError + "Please enter the amount of XP to add.");
            return false;
        }
        
        double amt;
        boolean l = false;
        try {
            
            String a = args[1];
            
            if(a.toLowerCase().endsWith("l")) {
                l = true;
                a = a.toUpperCase().replaceAll("L", "");
            }
            
            amt = Double.parseDouble(a);
        } catch(NumberFormatException ex) {
            sender.sendMessage(ChatError + "Please enter a valid number.");
            return false;
        }
        
        player = getOfflinePlayer(sender, args[0]);
        if(player == null) {
            sender.sendMessage(ChatError + "That player has never played before.");
            return true;
        }
        
        if(l) {
            sendMessage(sender, "Added " + ChatImportant + amt + ChatDefault + " Points to " + ChatImportant + player.getPlayer().getName() + ChatDefault + ".");
            player.addPoints(amt);
        } else {
            sendMessage(sender, "Added " + ChatImportant + amt + ChatDefault + " XP to " + ChatImportant + player.getPlayer().getName() + ChatDefault + ".");
            player.addXP(amt);
        }
        return true;
    }
}
