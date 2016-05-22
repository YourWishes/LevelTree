package com.domsplace.LevelTree.Commands;

import com.domsplace.LevelTree.Bases.LevelTreeCommandBase;
import com.domsplace.LevelTree.Bases.LevelTreeManagerBase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandLevelTree extends LevelTreeCommandBase {
    public CommandLevelTree() {
        super("LevelTree");
    }
    
    @Override
    public boolean commandRecieved(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length > 0) {
            String c = args[0];
            if(c.equalsIgnoreCase("reload")) {
                if(!hasPermission(sender, "reload")) {
                    return true;
                }
                
                sendMessage(sender, "Reloading Config...");
                LevelTreeManagerBase.loadAll();
                sendMessage(sender, ChatImportant +  "Reloaded Config!");
                return true;
            }
            
            if(c.equalsIgnoreCase("save")) {
                if(!hasPermission(sender, "save")) {
                    return true;
                }
                
                sendMessage(sender, "Saving data...");
                getPlugin().playerManager.saveAllPlayers();
                sendMessage(sender, ChatImportant + "Saved Data!");
                return true;
            }
            
            sender.sendMessage(ChatError + "Invalid sub command.");
            return false;
        }
        
        String[] msgs = new String[]{
            ChatDefault + "\t\t\t\t\t\t== " + ChatImportant + getPlugin().getName() + 
            " v" + getPlugin().pluginManager.getPluginVersion() + ChatDefault + 
            " ==",
            "\tProgrammed by " + Dom,
            "\tConcept by " + ChatColor.GREEN + "Douglas",
            "",
            "\t\t" + ChatColor.GOLD.toString() + ChatColor.ITALIC + getPlugin().pluginManager.yml.getString("website")
        };
        
        sendMessage(sender, msgs);
        return true;
    }
}
