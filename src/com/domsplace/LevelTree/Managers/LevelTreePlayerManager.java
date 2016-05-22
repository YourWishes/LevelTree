package com.domsplace.LevelTree.Managers;

import com.domsplace.LevelTree.Bases.LevelTreeManagerBase;
import com.domsplace.LevelTree.Enums.ManagerType;
import com.domsplace.LevelTree.Objects.Skill;
import com.domsplace.LevelTree.Objects.SkillOption;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LevelTreePlayerManager extends LevelTreeManagerBase {
    public LevelTreePlayerManager() {
        super(ManagerType.CONFIG);
    }
    
    @Override
   public void tryLoad() throws IOException {
        SkillPlayer.players.clear();
        
        File playersDirectory = new File(getDataFolder(), "players");
        if(!playersDirectory.exists()) {
            playersDirectory.mkdir();
        }
        
        File[] playerFiles = playersDirectory.listFiles();
        
        if(playerFiles == null) {
            return;
        }
        
        for(File pFile : playerFiles) {
            try {
                YamlConfiguration py = YamlConfiguration.loadConfiguration(pFile);
                
                //Load Raws
                String name = py.getString("name");
                double xp = py.getDouble("xp", 0d);
                double hp = py.getDouble("health.max", -1d);
                
                //Convert Data
                OfflinePlayer op = Bukkit.getOfflinePlayer(name);
                Map<SkillOption, Integer> skillOptions = new HashMap<SkillOption, Integer>();
                
                if(py.contains("perks")) {
                    for(String s : ((MemorySection) py.get("perks")).getKeys(false)) {
                        int level = py.getInt("perks." + s, -1);
                        if(level < 1) {
                            continue;
                        }

                        SkillOption option = SkillOption.getSkillOptionByNameExact(s);

                        if(option == null) {
                            continue;
                        }

                        skillOptions.put(option, level);
                    }
                }
                
                //Set Data
                SkillPlayer player = new SkillPlayer(op, xp);
                for(SkillOption option : skillOptions.keySet()) {
                    player.setSkillAbilityLevel(option, skillOptions.get(option));
                }
                
                if(hp >  0 && hp != player.getMaxHealth()) {
                    player.setMaxHealth(hp);
                }
            } catch(Exception ex) {
                log("Failed to load player from " + pFile.getName() + " File");
                continue;
            }
        }
        
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!SkillPlayer.registerPlayer(p)) {
                continue;
            }
            
            savePlayer(SkillPlayer.getPlayer(p));
        }
    }
    
    public void saveAllPlayers() {
        for(SkillPlayer player : SkillPlayer.players.values()) {
            savePlayer(player);
        }
    }
    
    public void savePlayer(SkillPlayer player) {
        try {
            trySavePlayer(player);
        } catch (IOException ex) {
            log("Failed to save " + player.getPlayer().getName() + ", reason: " + ex.getLocalizedMessage());
        }
    }
    
    public void trySavePlayer(SkillPlayer player) throws IOException {
        if(player == null) return;
        
        File playersDirectory = new File(getDataFolder(), "players");
        if(!playersDirectory.exists()) {
            playersDirectory.mkdir();
        }
        
        File playerFile = new File(playersDirectory, player.getPlayer().getName() + ".yml");
        if(!playerFile.exists()) {
            playerFile.createNewFile();
        }
        
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(playerFile);
        
        yml.set("name", player.getPlayer().getName());
        yml.set("xp", player.getXP());
        
        if(player.getMaxHealth() != getPlugin().configManager.yml.getDouble("health.default")) {
            yml.set("health.max", player.getMaxHealth());
        }
        
        for(SkillOption option : player.getSkillAbilities().keySet()) {
            int level = player.getSkillAbility(option);
            
            yml.set("perks." + option.getName(), level);
        }
        
        yml.save(playerFile);
    }
}
