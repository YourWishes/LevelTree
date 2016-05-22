package com.domsplace.LevelTree.Commands;

import com.domsplace.LevelTree.Bases.LevelTreeCommandBase;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCheckEXP extends LevelTreeCommandBase {
    public CommandCheckEXP() {
        super("CheckEXP");
    }
    
    @Override
    public boolean commandRecieved(CommandSender sender, Command cmd, String label, String[] args) {
        
        SkillPlayer player;
        
        if(args.length > 0) {
            if(!hasPermission(sender, "checkexp.others")) {
                return true;
            }
            
            player = getOfflinePlayer(sender, args[0]);
        } else if(!isPlayer(sender)) {
            sender.sendMessage(ChatError + "Please enter a player name.");
            return false;
        } else {
            Player p = (Player) sender;
            player = SkillPlayer.getPlayer(p);
        }
        
        if(player == null) {
            sender.sendMessage(ChatError + "That player has never played before.");
            return true;
        }
        
        String[] msgs = new String[]{
            ChatDefault + "XP for: " + ChatImportant + player.getPlayer().getName(),
            ChatDefault + "XP: " + ChatImportant + player.getXP(),
            ChatDefault + "Points: " + ChatImportant + player.getPoints(false)
        };
        
        sendMessage(sender, msgs);
        return true;
    }
}
