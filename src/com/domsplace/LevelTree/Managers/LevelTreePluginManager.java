package com.domsplace.LevelTree.Managers;

import com.domsplace.LevelTree.Bases.LevelTreeManagerBase;
import com.domsplace.LevelTree.Enums.ManagerType;
import java.io.IOException;
import java.io.InputStream;
import org.bukkit.configuration.file.YamlConfiguration;

public class LevelTreePluginManager extends LevelTreeManagerBase {
    public YamlConfiguration yml;
    
    public LevelTreePluginManager() {
        super(ManagerType.PLUGIN);
    }
    
    @Override
    public void tryLoad() throws IOException {
        InputStream is = getStream("plugin.yml");
        yml = YamlConfiguration.loadConfiguration(is);
    }
    
    public String getPluginName() {
        return yml.getString("name");
    }
    
    public String getPluginVersion() {
        return yml.getString("version");
    }
}
