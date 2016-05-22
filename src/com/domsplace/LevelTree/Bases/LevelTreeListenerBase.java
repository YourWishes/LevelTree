package com.domsplace.LevelTree.Bases;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class LevelTreeListenerBase extends LevelTreeBase implements Listener {
    public static final List<LevelTreeListenerBase> listeners = new ArrayList<LevelTreeListenerBase>();
    
    public static void registerListener(LevelTreeListenerBase listener) {
        Bukkit.getPluginManager().registerEvents(listener, getPlugin());
        LevelTreeListenerBase.listeners.add(listener);
    }
    
    public LevelTreeListenerBase() {
        LevelTreeListenerBase.registerListener(this);
    }
}
