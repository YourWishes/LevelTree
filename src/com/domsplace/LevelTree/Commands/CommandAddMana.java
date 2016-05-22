package com.domsplace.LevelTree.Commands;

import com.domsplace.LevelTree.Bases.LevelTreeCommandBase;
import com.domsplace.LevelTree.Hooks.MagicSpellsHook;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.mana.ManaChangeReason;
import com.nisovin.magicspells.mana.ManaHandler;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAddMana extends LevelTreeCommandBase {
    public CommandAddMana() {
        super("AddMana");
    }
    
    @Override
    public boolean commandRecieved(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length < 1) {
            sendMessage(sender, ChatError + "Please enter a player name.");
            return false;
        }
        
        if(args.length < 2) {
            sendMessage(sender, ChatError + "Please enter an amount.");
            return false;
        }
        
        if(!MagicSpellsHook.isMagicSpellsHooked()) {
            sendMessage(sender, ChatError + "MagicSpells currently isn't hooked.");
            return true;
        }
        
        OfflinePlayer player = getOfflinePlayer(args[0], sender);
        if(player == null || !player.isOnline()) {
            sendMessage(sender, ChatError + args[0] + " isn't online!");
            return true;
        }
        
        Player p = player.getPlayer();
        
        int amt;
        try {
            amt = Integer.parseInt(args[1]);
        } catch(Exception ex) {
            sendMessage(sender, ChatError + "Please enter a valid number!");
            return false;
        }
        
        ManaHandler mh = MagicSpells.getManaHandler();
        mh.setMaxMana(p, mh.getMaxMana(p) + amt);
        mh.addMana(p, mh.getMaxMana(p), ManaChangeReason.REGEN);
        
        sendMessage(sender, "Added " + ChatImportant + amt + ChatDefault + " mana to " + ChatImportant + p.getName() + ChatDefault + ".");
        return true;
    }
}
