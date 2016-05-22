package com.domsplace.LevelTree.Commands;

import com.domsplace.LevelTree.Bases.LevelTreeCommandBase;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandAddMaxHealth extends LevelTreeCommandBase {
    public CommandAddMaxHealth() {
        super("AddMaxHealth");
    }
    
    @Override
    public boolean commandRecieved(CommandSender sender, Command cmd, String label, String[] args) {
        SkillPlayer player;
        
        if(args.length < 0) {
            sender.sendMessage(ChatError + "Please enter a player name.");
            return false;
        }
        
        if(args.length < 1) {
            sender.sendMessage(ChatError + "Please enter the amount of health to add.");
            return false;
        }
        
        double amt;
        try {
            String a = args[1];
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
        
        sendMessage(sender, "Added " + ChatImportant + amt + ChatDefault + " to " + ChatImportant + player.getPlayer().getName() + ChatDefault + "'s Max Health.");
        player.addMaxHealth(amt);
        return true;
    }
}
