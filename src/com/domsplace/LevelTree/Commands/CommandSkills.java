package com.domsplace.LevelTree.Commands;

import com.domsplace.LevelTree.Bases.LevelTreeCommandBase;
import com.domsplace.LevelTree.Objects.Skill;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSkills extends LevelTreeCommandBase {
    public CommandSkills() {
        super("Skills");
    }
    
    @Override
    public boolean commandRecieved(CommandSender sender, Command cmd, String label, String[] args) {
        if(!isPlayer(sender)) {
            sender.sendMessage(ChatError + "This command can only be run by players.");
            return true;
        }
        
        Player p = (Player) sender;
        SkillPlayer player = SkillPlayer.getPlayer(p);
        if(player == null) return true;
        
        sendMessage(sender, "Opening purchased Skills.");
        p.openInventory(Skill.getPreviousSkillsInventory());
        return true;
    }
}
