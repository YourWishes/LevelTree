package com.domsplace.LevelTree.Hooks;

import com.domsplace.LevelTree.Bases.LevelTreeHookBase;
import com.nisovin.magicspells.MagicSpells;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class MagicSpellsHook extends LevelTreeHookBase {
    public static MagicSpells magicSpells;
    
    public static com.nisovin.magicspells.MagicSpells getMagicSpells() {
        try {
            Plugin p = Bukkit.getPluginManager().getPlugin("MagicSpells");
            
            if(p == null || !(p instanceof com.nisovin.magicspells.MagicSpells) || !p.isEnabled()) {
                return null;
            }
            
            return (com.nisovin.magicspells.MagicSpells) p;
        } catch(Exception ex) {
            return null;
        } catch(NoClassDefFoundError ex) {
            return null;
        }
    }
    
    private static boolean isMagicSpellsLoaded() {
        return MagicSpellsHook.getMagicSpells() != null;
    }
    
    public static boolean isMagicSpellsHooked() {
        return magicSpells != null;
    }
    
    public static void tryHook() {
        log("Attempting to hook into MagicSpells...");
        if(!isMagicSpellsLoaded()) {
            log("Failed to hook into MagicSpells! These features will be disabled.");
            return;
        }
        magicSpells = MagicSpellsHook.getMagicSpells();
        log("Hooked Successfully!");
    }
    
    public static void unHook() {
        magicSpells = null;
    }

    public static boolean isMagicSpells(Plugin p) {
        try {
            return p instanceof com.nisovin.magicspells.MagicSpells;
        } catch(NoClassDefFoundError e) {
            return false;
        }
    }
}
