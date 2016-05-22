package com.domsplace.LevelTree.Enums;

import com.domsplace.LevelTree.Bases.LevelTreeEnumBase;
import java.util.ArrayList;
import java.util.List;

public class LeaveType extends LevelTreeEnumBase {
    //Constants
    private static final List<LeaveType> leaveTypes = new ArrayList<LeaveType>();
    
    public static final LeaveType PLAYER_QUIT = new LeaveType("Player Quit");
    public static final LeaveType PLAYER_KICKED = new LeaveType("Player Kicked");
    
    //Statics
    private static void registerType(LeaveType type) {
        LeaveType.getTypes().add(type);
    }
    
    public static List<LeaveType> getTypes() {
        return LeaveType.leaveTypes;
    }
    
    //Instance
    private String type;
    
    public LeaveType(String type) {
        this.type = type;
        LeaveType.registerType(this);
    }
    
    public String getType() {
        return this.type;
    }
}
