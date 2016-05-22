package com.domsplace.LevelTree.Bases;

import com.domsplace.LevelTree.Enums.ManagerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LevelTreeManagerBase extends LevelTreeBase {
    public static List<LevelTreeManagerBase> managers = new ArrayList<LevelTreeManagerBase>();
    
    //Static Methods
    public static File getDataFolder() {
        return getPlugin().getDataFolder();
    }
    
    public static InputStream getStream(String name) {
        return getPlugin().getResource(name);
    }
    
    public static void registerManager(LevelTreeManagerBase instance) {
        LevelTreeManagerBase.managers.add(instance);
    }

    public static boolean loadAll() {
        boolean successful = true;
        
        for(LevelTreeManagerBase mb : LevelTreeManagerBase.managers) {
            if(mb.load()) {
                continue;
            }
            
            successful = false;
            error("Failed to load " + mb.getType().getName() + ".");
            break;
        }
        
        return successful;
    }
    
    //Instance
    private ManagerType type;
    
    public LevelTreeManagerBase(ManagerType type) {
        this.type = type;
        LevelTreeManagerBase.registerManager(this);
    }
    
    public ManagerType getType() {
        return this.type;
    }
    
    public boolean load() {
        try {
            tryLoad();
        } catch(IOException e) {
            return false;
        }
        
        return true;
    }
    
    public void tryLoad() throws IOException {
    }
}
