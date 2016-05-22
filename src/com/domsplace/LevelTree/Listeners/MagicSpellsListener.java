package com.domsplace.LevelTree.Listeners;

import com.domsplace.LevelTree.Bases.LevelTreeListenerBase;
import com.domsplace.LevelTree.Hooks.MagicSpellsHook;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public class MagicSpellsListener extends LevelTreeListenerBase {
    @EventHandler
    public void magicSpellsDisabledListener(PluginDisableEvent e) {
        Plugin p = e.getPlugin();
        if(!MagicSpellsHook.isMagicSpells(p)) {
            return;
        }
        
        log("MagicSpells was disabled! Unhooking...");
        MagicSpellsHook.unHook();
    }
    
    @EventHandler
    public void magicSpellsEnabledListener(PluginEnableEvent e) {
        Plugin p = e.getPlugin();
        if(!MagicSpellsHook.isMagicSpells(p)) {
            return;
        }
        
        log("MagicSpells was enabled! Hooking...");
        MagicSpellsHook.tryHook();
    }
}
