package com.domsplace.LevelTree.Commands;

import com.domsplace.LevelTree.Bases.LevelTreeCommandBase;
import com.domsplace.LevelTree.Objects.Skill;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSkillTree extends LevelTreeCommandBase {
    public CommandSkillTree() {
        super("SkillTree");
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
        sendMessage(sender, "Opening skill tree.");
        sendMessage(sender, "You have " + ChatImportant + player.getPoints(true) + ChatDefault + " points to spend.");
        p.openInventory(Skill.getSkillsInventory());
        return true;
    }
}
