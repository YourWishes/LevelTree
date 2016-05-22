package com.domsplace.LevelTree.Threads;

import com.domsplace.LevelTree.Bases.LevelTreeThreadBase;

public class SavePlayersThread extends LevelTreeThreadBase {
    public SavePlayersThread() {
        super(400, 12000);
    }
    
    @Override
    public void run() {
        log("Saving data...");
        getPlugin().playerManager.saveAllPlayers();
        log("Done!");
    }
}
