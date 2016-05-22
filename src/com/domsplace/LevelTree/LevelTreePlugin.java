package com.domsplace.LevelTree;

import com.domsplace.LevelTree.Bases.*;
import com.domsplace.LevelTree.Commands.*;
import com.domsplace.LevelTree.Listeners.*;
import com.domsplace.LevelTree.Managers.*;
import com.domsplace.LevelTree.Hooks.*;
import com.domsplace.LevelTree.Objects.Skill;
import com.domsplace.LevelTree.Threads.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelTreePlugin extends JavaPlugin {
    public boolean pluginEnabled = false;
    
    //Define Managers
    public LevelTreePluginManager pluginManager = new LevelTreePluginManager();
    public LevelTreeConfigManager configManager = new LevelTreeConfigManager();
    public LevelTreePlayerManager playerManager = new LevelTreePlayerManager();
    
    @Override
    public void onEnable() {
        //Statisize
        LevelTreeBase.setPlugin(this);
        
        //Load in data
        if(!LevelTreeManagerBase.loadAll()) {
            disable();
            return;
        }
        
        //Hook into plugins
        MagicSpellsHook.tryHook();
        
        //Load Listeners
        CustomEventListener customEventListener = new CustomEventListener();
        SkillsInventoryListener skillsInventoryListener = new SkillsInventoryListener();
        PlayerListener playerListener = new PlayerListener();
        MagicSpellsListener magicListener = new MagicSpellsListener();
        SkillEffectsListener skillListener = new SkillEffectsListener();
        
        //Start Threads
        SavePlayersThread saveThread = new SavePlayersThread();
        
        //Load in Commands
        CommandLevelTree levelTreeCommand = new CommandLevelTree();
        CommandSkillTree skillTreeCommand = new CommandSkillTree();
        CommandCheckEXP checkEXPCommand = new CommandCheckEXP();
        CommandAddEXP addEXPCommand = new CommandAddEXP();
        CommandAddMana addManaCommand = new CommandAddMana();
        CommandSkills skillsCommand = new CommandSkills();
        CommandAddMaxHealth addMaxHealthCommand = new CommandAddMaxHealth();
        
        //Finalize
        this.pluginEnabled = true;
    }
    
    @Override
    public void onDisable() {
        if(!this.pluginEnabled) {
            return;
        }
        
        //Stop Threads
        LevelTreeThreadBase.stopAllThreads();
        
        //Save Data
        playerManager.saveAllPlayers();
        
        //Cleanup
    }
    
    public void disable() {
        Bukkit.getPluginManager().disablePlugin(this);
    }
}
