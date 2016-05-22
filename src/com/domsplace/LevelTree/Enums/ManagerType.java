package com.domsplace.LevelTree.Enums;

import com.domsplace.LevelTree.Bases.LevelTreeEnumBase;
import java.util.ArrayList;
import java.util.List;

public class ManagerType extends LevelTreeEnumBase {
    public static final List<ManagerType> TYPES = new ArrayList<ManagerType>();
    
    public static final ManagerType PLUGIN = new ManagerType("Plugin");
    public static final ManagerType CONFIG = new ManagerType("Configuration");
    public static final ManagerType COMMAND = new ManagerType("Command");
    public static final ManagerType LISTENER = new ManagerType("Listener");
    public static final ManagerType THREAD = new ManagerType("Thread");
    
    //Instance
    private String name;
    
    public ManagerType(String name) {
        this.name = name;
        TYPES.add(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public static ManagerType getType(String guess) {
        for(ManagerType mt : TYPES) {
            if(!mt.getName().toLowerCase().contains(guess.toLowerCase())) {
                continue;
            }
            return mt;
        }
        return null;
    }
}
