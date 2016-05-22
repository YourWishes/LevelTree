package com.domsplace.LevelTree.Bases;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class LevelTreeThreadBase extends LevelTreeBase implements Runnable {
    //Static
    private static List<LevelTreeThreadBase> tasks = new ArrayList<LevelTreeThreadBase>();
    
    public static List<LevelTreeThreadBase> getThreads() {
        return LevelTreeThreadBase.tasks;
    }
    
    private static void registerThread(LevelTreeThreadBase thread) {
        LevelTreeThreadBase.getThreads().add(thread);
    }

    public static void stopAllThreads() {
        for(LevelTreeThreadBase thread : getThreads()) {
            thread.getThread().cancel();
        }
    }
    
    //Instance
    private BukkitTask thread;
    private boolean asynchronous;
    
    public LevelTreeThreadBase(long delay, long repeat) {
        this(delay, repeat, false);
    }
    
    public LevelTreeThreadBase(long delay, long repeat, boolean async) {
        if(!async) {
            this.thread = Bukkit.getScheduler().runTaskTimer(getPlugin(), this, delay, repeat);
        } else {
            this.thread = Bukkit.getScheduler().runTaskTimerAsynchronously(getPlugin(), this, delay, repeat);
        }
        
        this.asynchronous = async;
    }
    
    public boolean isAsynchronous() {
        return this.asynchronous;
    }
    
    public BukkitTask getThread() {
        return this.thread;
    }

    @Override
    public void run() {
    }
}
