package com.domsplace.LevelTree.Bases;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LevelTreeCommandBase extends LevelTreeBase implements CommandExecutor {
    public static final List<LevelTreeCommandBase> commands = new ArrayList<LevelTreeCommandBase>();
    
    public static Command registerCommand(LevelTreeCommandBase command) {
        Command cmd = getPlugin().getCommand(command.getCommand());
        getPlugin().getCommand(command.getCommand()).setExecutor(command);
        
        return cmd;
    }
    
    public static void setAllPermissionMessage(String message) {
        for(LevelTreeCommandBase cmd : commands) {
            cmd.setPermissionMessage(message);
        }
    }
    
    //Instance
    private String command;
    private Command cmd;
    
    public LevelTreeCommandBase(String command) {
        this.command = command;
        this.cmd = registerCommand(this);
        this.setPermissionMessage(PERMISSION_ERROR);
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public Command getCmd() {
        return this.cmd;
    }
    
    public LevelTreeCommandBase setPermissionMessage(String message) {
        this.getCmd().setPermissionMessage(message);
        return this;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase(this.getCommand())) {
            if(!hasPermission(sender, cmd.getPermission(), true)) {
                return true;
            }
            return this.commandRecieved(sender, cmd, label, args);
        }
        return false;
    }
    
    public boolean commandRecieved(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
}
