package com.domsplace.LevelTree.Bases;

import com.domsplace.LevelTree.LevelTreePlugin;
import com.domsplace.LevelTree.Objects.SkillMaterial;
import com.domsplace.LevelTree.Objects.SkillPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class LevelTreeBase {
    public static final boolean DEBUG_MODE = true;
    public static final boolean NOTIFY_PLAYERS = true;
    public static final String PERMISSION_ERROR = ChatColor.RED + "You don't have permission to do this.";
    
    public static String Dom = ChatColor.LIGHT_PURPLE + "Dom";
    
    public static LevelTreeBase instance;
    public static LevelTreePlugin plugin;
    
    public static String ChatError = ChatColor.RED.toString();
    public static String ChatDefault = ChatColor.GRAY.toString();
    public static String ChatImportant = ChatColor.BLUE.toString();
    
    public static String pluginName = "PluginName";
    
    public static void setPlugin(LevelTreePlugin plugin) {
        LevelTreeBase.plugin = plugin;
        pluginName = plugin.getName();
    }
    
    public static LevelTreePlugin getPlugin() {
        return LevelTreeBase.plugin;
    }
    
    public static void sendMessage(CommandSender player, String message) {
        String msg = message;
        msg = msg.replaceAll("\t", "    ");
        player.sendMessage(ChatDefault + msg);  
    }
    
    public static void sendMessage(CommandSender player, String[] message) {
        for(String s : message) {
            sendMessage(player, s);
        }
    }
    
    public static void sendMessage(CommandSender player, Object message) {
        sendMessage(player, ChatDefault + message.toString());
    }
    
    public static void notify(SkillPlayer player, Object message) {
        if(!NOTIFY_PLAYERS) {
            return;
        }
        
        sendMessage(player, message);
    }
    
    public static void sendMessage(SkillPlayer player, Object message) {
        if(!player.getPlayer().isOnline()) {
            return;
        }
        
        sendMessage(player.getPlayer().getPlayer(), message);
    }
    
    public static void msgConsole(String message) {
        sendMessage(Bukkit.getConsoleSender(), message);
    }
    
    public static void broadcast(String message) {
        msgConsole(message);
        for(Player p : Bukkit.getOnlinePlayers()) {
            sendMessage(p, message);
        }
    }
    
    public static void error(String message) {
        msgConsole(ChatColor.DARK_RED + "[" + pluginName + "] Error: " + ChatColor.WHITE);
    }
    
    
    public static void debug(Object object) {
        if(!DEBUG_MODE) {
            return;
        }
        
        broadcast(ChatColor.LIGHT_PURPLE + "Debug: " + ChatColor.AQUA + object.toString());
    }
    
    public static boolean isPlayer(Object object) {
        if(object == null) return false;
        return (object instanceof Player);
    }
    
    public static void runCommands(List<String> commands, OfflinePlayer player) {
        Player p = null;
        if(player.isOnline()) {
            p = player.getPlayer();
        }
        
        for(String s : commands) {
            CommandSender sender;
            if(s.startsWith("@")) {
                sender = Bukkit.getConsoleSender();
                s = s.replaceFirst("@", "");
            } else if(p == null) {
                continue;
            } else if(s.startsWith("#")) {
                p.chat(s.replaceFirst("#", ""));
                continue;
            } else {
                sender = p;
            }
            
            if(s.equalsIgnoreCase("")) {
                continue;
            }
            
            s = s.replaceAll("%p%", player.getName());
            Bukkit.dispatchCommand(sender, s);
        }
    }
    
    public static void runCommands(List<String> commands, OfflinePlayer player, int num) {
        List<String> cmds = new ArrayList<String>();
        
        for(String s : commands) {
            s = s.replaceAll("%n%", Integer.toString(num));
            cmds.add(s);
        }
        
        runCommands(cmds, player);
    }
    
    public static ItemStack getItemStackFromString(String string) {
        SkillMaterial sMaterial = SkillMaterial.fromString(string);
        return sMaterial.getAsItemStack(1);
    }
    
    public static void log(Object obj) {
        getPlugin().getLogger().info(obj.toString());
    }
    
    public static String colorise(String message) {
        String[] normvalues = { "&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f", "&l", "&m", "&n", "&k", "&r", "&o" };
        String[] coloredvalues = { "§0", "§1", "§2", "§3", "§4", "§5", "§6", "§7", "§8", "§9", "§a", "§b", "§c", "§d", "§e", "§f", "§l", "§m", "§n", "§k", "§r", "§o" };
        
        for(int i = 0; i < normvalues.length; i++) {
            message = message.replaceAll(normvalues[i], coloredvalues[i]);
        }
        
        return message;
    }
    
    public static boolean hasPermission(CommandSender sender, String node) {
        return hasPermission(sender, node, false);
    }
    
    public static boolean hasPermission(CommandSender sender, String node, boolean dontPrefix) {
        if(!dontPrefix) {
            node = getPlugin().getName() + "." + node;
        }
        
        if(sender.hasPermission(node)) {
            return true;
        }
        
        sendMessage(sender, PERMISSION_ERROR);
        return false;
    }
    
    public static SkillPlayer getOfflinePlayer(CommandSender sender, String name) {
        OfflinePlayer player = getOfflinePlayer(name, sender);
        if(player == null) return null;
        SkillPlayer plyr = SkillPlayer.getPlayer(player);
        if(plyr == null) return null;
        return plyr;
    }
    
    public static OfflinePlayer getOfflinePlayer(String player, CommandSender sender) {
        OfflinePlayer p = Bukkit.getPlayer(player);
        if(p == null) {
            p = Bukkit.getOfflinePlayer(player);
            return p;
        }
        
        if(!(sender instanceof Player)) {
            return p;
        }
        
        if(p.isOnline() && !((Player) sender).canSee(p.getPlayer())) {
            p = Bukkit.getOfflinePlayer(player);
        }
        
        return p;
    }
    
    public static Player getPlayer(String player, CommandSender sender) {
        Player p = Bukkit.getPlayer(player);
        if(p == null) {
            return null;
        }
        
        if(!(sender instanceof Player)) {
            return p;
        }
        
        if(p.isOnline() && !((Player) sender).canSee(p.getPlayer())) {
            p = Bukkit.getPlayerExact(player);
        }
        
        return p;
    }
    
    public static int getRandomNumberBetween(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt(max - min) + min;
        return randomNumber;
    }
    
    public static boolean flipCoin(double percentChance) {
        int r = getRandomNumberBetween(0, 100);
        return r < percentChance;
    }
    
    public static void LogTree(Block base, ItemStack axe) {
        if(base == null) {
            return;
        }
        leavesSinceLog = 0;
        checkAdjacentLogging(base, axe, false);
    }
    
    private static int leavesSinceLog = 0;
    
    private static void checkAdjacentLogging(Block b, ItemStack axe, boolean brk) {
        if(brk) {
            short hard = 1;
            if(!b.breakNaturally(axe)) return;
            short old = axe.getDurability();
            short dmg = (short) (old + hard);
            axe.setDurability(dmg);
        }
        
        if(axe.getDurability() >= axe.getType().getMaxDurability()) {
            return;
        }
        
        for(BlockFace bf : BlockFace.values()) {
            if(bf == null) {
                continue;
            }
            
            Block b2 = b.getRelative(bf);
            if(b2 == null) {
                continue;
            }
            
            if(b2.getType() == null) {
                continue;
            }
            
            if(!b2.getType().equals(Material.LOG) && !b2.getType().equals(Material.LEAVES)) {
                continue;
            }
            
            if(b2.getType().equals(Material.LEAVES)) {
                if(leavesSinceLog > 30) {
                    continue;
                }
                leavesSinceLog ++;
            }
            
            checkAdjacentLogging(b.getRelative(bf), axe, true);
        }
    }
    
    private static Material[] axes = new Material[] {
        Material.DIAMOND_AXE,
        Material.WOOD_AXE,
        Material.IRON_AXE,
        Material.GOLD_AXE,
        Material.STONE_AXE
    };
    
    private static Material[] tree = new Material[] {
        Material.LOG,
        Material.LEAVES
    };
    
    public static boolean isAxe(ItemStack is) {
        if(is == null) {
            return false;
        }
        
        for(Material m : axes) {
            if(m.equals(is.getType())) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean isTree(Material m) {
        if(m == null) {
            return false;
        }
        
        for(Material m2 : tree) {
            if(m2.equals(m)) return true;
        }
        
        return false;
    }
    
    public static Map<Player, Long> lastDamage = new HashMap<Player, Long>();
    public static boolean inDanger(Player player) {
        if(!lastDamage.containsKey(player)) return false;
        long last = lastDamage.get(player);
        long now = System.currentTimeMillis();
        
        return ((now - last) >= (60*1000));
    }
    
    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
    
    public static int getInt(String string) {
        if(!isInt(string)) return -1;
        return Integer.parseInt(string);
    }
    
    public static void pushAwayEntity(Entity entity, Player p, double speed) {
        Vector unitVector = entity.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
        entity.setVelocity(unitVector.multiply(speed));
    }
}
