package com.domsplace.LevelTree.Managers;

import com.domsplace.LevelTree.Bases.LevelTreeBase;
import com.domsplace.LevelTree.Bases.LevelTreeManagerBase;
import com.domsplace.LevelTree.Bases.LevelTreeObjectBase;
import com.domsplace.LevelTree.Enums.AbilityType;
import com.domsplace.LevelTree.Enums.ManagerType;
import com.domsplace.LevelTree.Objects.Skill;
import com.domsplace.LevelTree.Objects.SkillAbility;
import com.domsplace.LevelTree.Objects.SkillAbilityOption;
import com.domsplace.LevelTree.Objects.SkillMaterial;
import com.domsplace.LevelTree.Objects.SkillOption;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class LevelTreeConfigManager extends LevelTreeManagerBase {
    public YamlConfiguration yml;
    
    public LevelTreeConfigManager() {
        super(ManagerType.CONFIG);
    }
    
    @Override
    public void tryLoad() throws IOException {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        
        File config = new File(getDataFolder(), "config.yml");
        if(!config.exists()) {
            config.createNewFile();
        }
        
        yml = YamlConfiguration.loadConfiguration(config);
        
        if(!yml.contains("multiplier")) {
            yml.set("multiplier", 2000);
        }
        
        if(!yml.contains("health.default")) {
            yml.set("health.default", 20d);
        }
        
        //Default Data
        List<String> descriptions;
        if(!yml.contains("skills")) {
            yml.set("skills.Woodcutting.title", "&cWoodcutting");
            yml.set("skills.Woodcutting.item", "17:0");
            descriptions = new ArrayList<String>();
            descriptions.add("&6Lets you cut wood quicker.");
            descriptions.add("&9Higher drop rate.");
            yml.set("skills.Woodcutting.description", descriptions);
            
            yml.set("skills.Mining.title", "&8Mining");
            yml.set("skills.Mining.item", "278:0:1");
            descriptions = new ArrayList<String>();
            descriptions.add("&7Lets you mine faster.");
            descriptions.add("&6Higher drop rate.");
            yml.set("skills.Mining.description", descriptions);
            
            yml.set("skills.Digging.title", "&3Shovelling");
            yml.set("skills.Digging.item", "277");
            descriptions = new ArrayList<String>();
            descriptions.add("&7Diggy Diggy Hole (faster)");
            descriptions.add("&6Higher drop rate.");
            yml.set("skills.Digging.description", descriptions);
            
            yml.set("skills.Fishing.title", "&6Fishing");
            yml.set("skills.Fishing.item", "349");
            descriptions = new ArrayList<String>();
            descriptions.add("&bIncrease your chance of getting fish.");
            yml.set("skills.Fishing.description", descriptions);
            
            yml.set("skills.Archery.title", "&4Archery");
            yml.set("skills.Archery.item", "261");
            descriptions = new ArrayList<String>();
            descriptions.add("&8Increase damage dealt.");
            yml.set("skills.Archery.description", descriptions);
            
            yml.set("skills.Swords.title", "&7Swords");
            yml.set("skills.Swords.item", "267");
            descriptions = new ArrayList<String>();
            descriptions.add("&6Increase damage dealt.");
            yml.set("skills.Swords.description", descriptions);
            
            yml.set("skills.Smelting.title", "&8Smelting");
            yml.set("skills.Smelting.item", "145");
            descriptions = new ArrayList<String>();
            descriptions.add("&6Increase smelting chances");
            yml.set("skills.Smelting.description", descriptions);
            
            yml.set("skills.Magic.title", "&9&oMagic");
            yml.set("skills.Magic.item", "280|ARROW_FIRE");
            descriptions = new ArrayList<String>();
            descriptions.add("&5Increase Mana!");
            yml.set("skills.Magic.description", descriptions);
        }
        
        List<String> strings;
        if(!yml.contains("abilities")) {
            yml.set("abilities.GoldNugget.name", "&eGold Nugget");
            strings = new ArrayList<String>();
            strings.add("4:0");
            strings.add("1:0");
            yml.set("abilities.GoldNugget.fastbreak", strings);
            yml.set("abilities.GoldNugget.fastbreakpercent", 10d);
            
            yml.set("abilities.DirtMiner.name", "&6Dirt Miner");
            strings = new ArrayList<String>();
            strings.add("3");
            strings.add("13");
            strings.add("82");
            yml.set("abilities.DirtMiner.fastbreak", strings);
            yml.set("abilities.DirtMiner.fastbreakpercent", 10d);
            
            yml.set("abilities.DoubleDrops.name", "&9Double Drops");
            strings = new ArrayList<String>();
            strings.add("56:0");
            strings.add("14:0");
            strings.add("15:0");
            strings.add("16:0");
            strings.add("73");
            strings.add("74");
            strings.add("21");
            strings.add("153");
            strings.add("129");
            yml.set("abilities.DoubleDrops.multipledrops", strings);
            yml.set("abilities.DoubleDrops.multipledropspercent", 5);
            
            yml.set("abilities.BlastMining.name", "&6Blast Mining");
            strings = new ArrayList<String>();
            yml.set("abilities.BlastMining.blastminingpercent", 100);
            
            yml.set("abilities.DoubleWood.name", "&9Double Wood");
            strings = new ArrayList<String>();
            strings.add("17:0");
            strings.add("17:1");
            strings.add("17:2");
            strings.add("17:3");
            yml.set("abilities.DoubleWood.multipledrops", strings);
            yml.set("abilities.DoubleWood.multipledropspercent", 5);
            
            yml.set("abilities.Logging.name", "&2Logging");
            strings = new ArrayList<String>();
            yml.set("abilities.Logging.logger", true);
            
            yml.set("abilities.RandomDrops.name", "&bRanodm Drops");
            strings = new ArrayList<String>();
            strings.add("264");
            yml.set("abilities.RandomDrops.randomdrops", strings);
            yml.set("abilities.RandomDrops.randomdropspercent", 5);
            strings = new ArrayList<String>();
            strings.add("3");
            strings.add("13");
            strings.add("82");
            yml.set("abilities.RandomDrops.randomdropscondition", strings);
            
            yml.set("abilities.MasterAngler.name", "&1Master Angler");
            strings = new ArrayList<String>();
            yml.set("abilities.MasterAngler.fishingspeed", 10);
            
            yml.set("abilities.FishJunk.name", "&eTreasure Fisher");
            strings = new ArrayList<String>();
            strings.add("298");
            strings.add("299");
            strings.add("300");
            strings.add("301");
            yml.set("abilities.FishJunk.fishingdrops", strings);
            yml.set("abilities.FishJunk.fishingdropspercent", 10);
            
            yml.set("abilities.MagicFisher.name", "&5Magic Fisher");
            strings = new ArrayList<String>();
            strings.add("298:0|PROTECTION_ENVIRONMENTAL:1");
            strings.add("299:0|PROTECTION_ENVIRONMENTAL:1");
            strings.add("300:0|PROTECTION_ENVIRONMENTAL:1");
            strings.add("301:0|PROTECTION_ENVIRONMENTAL:1");
            yml.set("abilities.MagicFisher.fishingdrops", strings);
            yml.set("abilities.MagicFisher.fishingdropspercent", 3);
            
            yml.set("abilities.Daze.name", "&8Daze");
            strings = new ArrayList<String>();
            yml.set("abilities.Daze.dazepercent", 20);
            yml.set("abilities.Daze.dazeseconds", 3);
            
            yml.set("abilities.CriticalBow.name", "&cBow Damage");
            strings = new ArrayList<String>();
            yml.set("abilities.CriticalBow.bowdamage", 1);
            
            yml.set("abilities.PoisonTouch.name", "&9Poison Touch");
            strings = new ArrayList<String>();
            yml.set("abilities.PoisonTouch.poisondamage", 10);
            yml.set("abilities.PoisonTouch.poisondamageduration", 3);
            
            yml.set("abilities.IncreasePT.name", "null");
            strings = new ArrayList<String>();
            yml.set("abilities.IncreasePT.poisondamageincrease", 2);
            
            yml.set("abilities.AOESwirl.name", "AOESwirl");
            strings = new ArrayList<String>();
            yml.set("abilities.AOESwirl.criticalwhirlwind", 2);
            
            yml.set("abilities.XPBoost.name", "&8XPBoost");
            strings = new ArrayList<String>();
            yml.set("abilities.XPBoost.furnacexpmultiplier", 3);
        }
        
        /*
         * PERKS
         * 
         */
        List<String> commands;
        List<String> abilities;
        List<String> dependant;
        if(!yml.contains("perks")) {
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.GoldNugget.title", "&eGold Nugget");
            yml.set("perks.GoldNugget.cost", 3);
            yml.set("perks.GoldNugget.max", 4);
            yml.set("perks.GoldNugget.category", "Mining");
            yml.set("perks.GoldNugget.levelmultiplier", 2);
            commands.add("@say %p% upgraded their Gold Nugget level to %n%!");
            commands.add("#I can feel a strange power from this pickaxe!");
            commands.add("me has an essence of power *");
            abilities.add("GoldNugget");
            descriptions.add("Increases mining speed 10%");
            yml.set("perks.GoldNugget.commands", commands);
            yml.set("perks.GoldNugget.abilities", abilities);
            yml.set("perks.GoldNugget.descriptions", descriptions);
            yml.set("perks.GoldNugget.item", "371");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.DoubleDrops.title", "&9Double Drops!");
            yml.set("perks.DoubleDrops.cost", 3);
            yml.set("perks.DoubleDrops.max", 4);
            yml.set("perks.DoubleDrops.category", "Mining");
            yml.set("perks.DoubleDrops.levelmultiplier", 2);
            commands.add("me is feeling lucky *");
            abilities.add("DoubleDrops");
            descriptions.add("&6Have a 5% greater chance of getting double the drops!");
            yml.set("perks.DoubleDrops.commands", commands);
            yml.set("perks.DoubleDrops.abilities", abilities);
            yml.set("perks.DoubleDrops.descriptions", descriptions);
            yml.set("perks.DoubleDrops.item", "264");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.BlastMining.title", "&6BLAST Mining!");
            yml.set("perks.BlastMining.cost", 3);
            yml.set("perks.BlastMining.max", 1);
            yml.set("perks.BlastMining.category", "Mining");
            abilities.add("BlastMining");
            descriptions.add("&3Get 100% of dropped items from TNT.");
            dependant.add("DoubleDrops:4");
            yml.set("perks.BlastMining.abilities", abilities);
            yml.set("perks.BlastMining.descriptions", descriptions);
            yml.set("perks.BlastMining.dependant", dependant);
            yml.set("perks.BlastMining.item", "46");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.DoubleWood.title", "&6Double Logs");
            yml.set("perks.DoubleWood.cost", 3);
            yml.set("perks.DoubleWood.max", 5);
            yml.set("perks.DoubleWood.category", "Woodcutting");
            yml.set("perks.DoubleWood.levelmultiplier", 2);
            abilities.add("DoubleWood");
            descriptions.add("&9Get double the logs from woodcutting!");
            yml.set("perks.DoubleWood.abilities", abilities);
            yml.set("perks.DoubleWood.descriptions", descriptions);
            yml.set("perks.DoubleWood.item", "17");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.Logging.title", "&2Logger");
            yml.set("perks.Logging.cost", 10);
            yml.set("perks.Logging.max", 1);
            yml.set("perks.Logging.category", "Woodcutting");
            abilities.add("Logging");
            dependant.add("DoubleWood:5");
            descriptions.add("&6Cut down the whole tree at once.");
            yml.set("perks.Logging.abilities", abilities);
            yml.set("perks.Logging.descriptions", descriptions);
            yml.set("perks.Logging.dependant", dependant);
            yml.set("perks.Logging.item", "279");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.DirtMiner.title", "&6Dirt Digger");
            yml.set("perks.DirtMiner.cost", 10);
            yml.set("perks.DirtMiner.max", 4);
            yml.set("perks.DirtMiner.category", "Digging");
            yml.set("perks.DirtMiner.levelmultiplier", 2);
            abilities.add("DirtMiner");
            descriptions.add("&7Mine Clay, Dirt and Gravel 10% Faster!");
            yml.set("perks.DirtMiner.abilities", abilities);
            yml.set("perks.DirtMiner.descriptions", descriptions);
            yml.set("perks.DirtMiner.item", "82");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.TreasureHunter.title", "&eTreasure Hunter");
            yml.set("perks.TreasureHunter.cost", 10);
            yml.set("perks.TreasureHunter.max", 1);
            yml.set("perks.TreasureHunter.category", "Digging");
            abilities.add("RandomDrops");
            descriptions.add("&3Get random items when shovelling.");
            dependant.add("DirtMiner:4");
            yml.set("perks.TreasureHunter.abilities", abilities);
            yml.set("perks.TreasureHunter.descriptions", descriptions);
            yml.set("perks.TreasureHunter.dependant", dependant);
            yml.set("perks.TreasureHunter.item", "284");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.MasterAngler.title", "&9Master Angler");
            yml.set("perks.MasterAngler.cost", 10);
            yml.set("perks.MasterAngler.max", 3);
            yml.set("perks.MasterAngler.category", "Fishing");
            abilities.add("MasterAngler");
            descriptions.add("&bGet a 10% Fishing Chance increase");
            yml.set("perks.MasterAngler.abilities", abilities);
            yml.set("perks.MasterAngler.descriptions", descriptions);
            yml.set("perks.MasterAngler.item", "346");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.TresureFisher.title", "&3Treasure Fisher");
            yml.set("perks.TresureFisher.cost", 10);
            yml.set("perks.TresureFisher.max", 2);
            yml.set("perks.TresureFisher.category", "Fishing");
            abilities.add("FishJunk");
            descriptions.add("&9Get random items when fishing.");
            yml.set("perks.TresureFisher.abilities", abilities);
            yml.set("perks.TresureFisher.descriptions", descriptions);
            yml.set("perks.TresureFisher.item", "54");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.MagicFisher.title", "&5Magic Fisher");
            yml.set("perks.MagicFisher.cost", 10);
            yml.set("perks.MagicFisher.max", 1);
            yml.set("perks.MagicFisher.category", "Fishing");
            abilities.add("MagicFisher");
            descriptions.add("&9Get random &6&oenchanted&r&9 items when fishing.");
            dependant.add("TresureFisher:2");
            dependant.add("MasterAngler:3");
            yml.set("perks.MagicFisher.abilities", abilities);
            yml.set("perks.MagicFisher.descriptions", descriptions);
            yml.set("perks.MagicFisher.dependant", dependant);
            yml.set("perks.MagicFisher.item", "350");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.Daze.title", "&8Daze");
            yml.set("perks.Daze.cost", 10);
            yml.set("perks.Daze.max", 4);
            yml.set("perks.Daze.category", "Archery");
            abilities.add("Daze");
            descriptions.add("&9Have a +3 second chance to daze your enemy 1/5 times");
            yml.set("perks.Daze.abilities", abilities);
            yml.set("perks.Daze.descriptions", descriptions);
            yml.set("perks.Daze.item", "377");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.BowDamage.title", "&cBow Damage");
            yml.set("perks.BowDamage.cost", 10);
            yml.set("perks.BowDamage.max", 5);
            yml.set("perks.BowDamage.category", "Archery");
            abilities.add("CriticalBow");
            descriptions.add("&9Have a +1 damage boost.");
            yml.set("perks.BowDamage.abilities", abilities);
            yml.set("perks.BowDamage.descriptions", descriptions);
            yml.set("perks.BowDamage.item", "262");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.PoisonTouch.title", "&9Poison Touch");
            yml.set("perks.PoisonTouch.cost", 10);
            yml.set("perks.PoisonTouch.max", 5);
            yml.set("perks.PoisonTouch.category", "Swords");
            abilities.add("PoisonTouch");
            descriptions.add("&5Have a +10% chance of poisoning when using swords.");
            yml.set("perks.PoisonTouch.abilities", abilities);
            yml.set("perks.PoisonTouch.descriptions", descriptions);
            yml.set("perks.PoisonTouch.item", "376");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.PoisonTouchIncrease.title", "&9Poison Touch &b(Duration Increase)");
            yml.set("perks.PoisonTouchIncrease.cost", 10);
            yml.set("perks.PoisonTouchIncrease.max", 5);
            yml.set("perks.PoisonTouchIncrease.category", "Swords");
            abilities.add("IncreasePT");
            descriptions.add("&3Increase Poison Touch duration by +2 seconds.");
            dependant.add("PoisonTouch");
            yml.set("perks.PoisonTouchIncrease.abilities", abilities);
            yml.set("perks.PoisonTouchIncrease.descriptions", descriptions);
            yml.set("perks.PoisonTouchIncrease.dependant", dependant);
            yml.set("perks.PoisonTouchIncrease.item", "347");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.AOESwirl.title", "&fAOE &bSwirl");
            yml.set("perks.AOESwirl.cost", 10);
            yml.set("perks.AOESwirl.max", 1);
            yml.set("perks.AOESwirl.category", "Swords");
            abilities.add("AOESwirl");
            descriptions.add("&3&oHit enemies in a radius on critical hit.");
            yml.set("perks.AOESwirl.abilities", abilities);
            yml.set("perks.AOESwirl.descriptions", descriptions);
            yml.set("perks.AOESwirl.item", "288");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.FurnaceXP.title", "&8Furnace XP Boost");
            yml.set("perks.FurnaceXP.cost", 10);
            yml.set("perks.FurnaceXP.max", 1);
            yml.set("perks.FurnaceXP.category", "Smelting");
            abilities.add("XPBoost");
            descriptions.add("&7Get 3 times the normally extracted experience from Furnaces.");
            yml.set("perks.FurnaceXP.abilities", abilities);
            yml.set("perks.FurnaceXP.descriptions", descriptions);
            yml.set("perks.FurnaceXP.item", "61");
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.ManaBoost.title", "&9Additional Mana");
            yml.set("perks.ManaBoost.cost", 10);
            yml.set("perks.ManaBoost.max", 4);
            yml.set("perks.ManaBoost.category", "Magic");
            descriptions.add("&5Add +25 Mana points to your max Mana!");
            commands.add("@addmana %p% 25");
            yml.set("perks.ManaBoost.descriptions", descriptions);
            yml.set("perks.ManaBoost.item", "384");
            yml.set("perks.ManaBoost.levelmultiplier", 2);
            yml.set("perks.ManaBoost.commands", commands);
            
            commands = new ArrayList<String>();
            abilities = new ArrayList<String>();
            descriptions = new ArrayList<String>();
            dependant = new ArrayList<String>();
            yml.set("perks.HealthBoost.title", "&cIncrease Health");
            yml.set("perks.HealthBoost.cost", 10);
            yml.set("perks.HealthBoost.max", 10);
            yml.set("perks.HealthBoost.category", "Magic");
            descriptions.add("&4Add a heart of health to your Max Health.");
            commands.add("@addmaxhealth %p% 2");
            yml.set("perks.HealthBoost.descriptions", descriptions);
            yml.set("perks.HealthBoost.item", "322:1|DURABILITY:5");
            yml.set("perks.HealthBoost.levelmultiplier", 2);
            yml.set("perks.HealthBoost.commands", commands);
        }
        
        //Save changes
        yml.save(config);
        
        //Load in Data
        LevelTreeObjectBase.pointsCurve = yml.getInt("multiplier");
        
        Skill.skills.clear();
        Skill.reset();
        for(String s : ((MemorySection) yml.get("skills")).getKeys(false)) {
            String key = "skills." + s + ".";
            
            String title = yml.getString(key + "title");
            ItemStack is = getItemStackFromString(yml.getString(key + "item"));
            if(is == null) {
                log("Failed to load Skill: " + title + " - Invalid ItemStack");
                continue;
            }
            descriptions = getListWithFallback(key + "description");
            
            List<String> desc = new ArrayList<String>();
            for(String st : descriptions) {
                st = colorise(st);
                desc.add(st);
            }
            
            title = colorise(title);
            Skill skill = new Skill(s, title, is, desc);
        }
        
        SkillAbility.getSkillAbilities().clear();
        SkillAbilityOption.registeredSkillAbilityOptions.clear();
        for(String s : ((MemorySection) yml.get("abilities")).getKeys(false)) {
            String key = "abilities." + s + ".";
            
            String title = colorise(yml.getString(key + "name"));
            
            SkillAbility sa = new SkillAbility(s, title);
            
            List<SkillAbilityOption> options = new ArrayList<SkillAbilityOption>();
            if(yml.contains(key + "fastbreak")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.FAST_BREAK);
                if(yml.contains(key + "fastbreakpercent")) {
                    option.setPercent(yml.getDouble(key + "fastbreakpercent"));
                } else {
                    option.setPercent(100d);
                }
                option.setMaterials(SkillMaterial.getMaterialsFromList(yml.getStringList(key + "fastbreak")));
                options.add(option);
            }
            
            if(yml.contains(key + "multipledrops")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.MULTIPLE_DROPS);
                if(yml.contains(key + "multipledrops")) {
                    option.setPercent(yml.getDouble(key + "multipledropspercent"));
                } else {
                    option.setPercent(100d);
                }
                option.setMaterials(SkillMaterial.getMaterialsFromList(yml.getStringList(key + "multipledrops")));
                options.add(option);
            }
            
            if(yml.contains(key + "blastminingpercent")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.BLAST_MINING);
                option.setPercent(yml.getDouble(key + "blastminingpercent"));
                options.add(option);
            }
            
            if(yml.contains(key + "logger") && yml.getBoolean(key + "logger")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.LOGGING);
                options.add(option);
            }
            
            if(yml.contains(key + "randomdrops")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.RANDOM_DROPS);
                option.setPercent(yml.getDouble(key + "randomdropspercent"));
                option.setMaterials(SkillMaterial.getMaterialsFromList(yml.getStringList(key + "randomdrops")));
                option.otherMaterials = SkillMaterial.getMaterialsFromList(yml.getStringList(key + "randomdropscondition"));
                options.add(option);
            }
            
            if(yml.contains(key + "fishingspeed")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.FISHING_SPEED);
                option.setPercent(yml.getDouble(key + "fishingspeed"));
                options.add(option);
            }
            
            if(yml.contains(key + "fishingdrops")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.FISHING_DROPS);
                option.setPercent(yml.getDouble(key + "fishingdropspercent"));
                option.setMaterials(SkillMaterial.getMaterialsFromList(yml.getStringList(key + "fishingdrops")));
                options.add(option);
            }
            
            if(yml.contains(key + "dazeseconds")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.ARCHERY_STUN);
                option.setPercent(yml.getDouble(key + "dazepercent"));
                option.seconds = yml.getInt(key + "dazeseconds");
                options.add(option);
            }
            
            if(yml.contains(key + "bowdamage")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.ARCHERY_DAMAGE);
                option.setPercent(yml.getDouble(key + "bowdamage"));
                options.add(option);
            }
            
            if(yml.contains(key + "poisondamage")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.POISON_DAMAGE);
                option.setPercent(yml.getDouble(key + "poisondamage"));
                option.seconds = yml.getInt(key + "poisondamageduration");
                options.add(option);
            }
            
            if(yml.contains(key + "poisondamageincrease")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.POISON_INCREASE);
                option.setPercent(yml.getDouble(key + "poisondamageincrease"));
                options.add(option);
            }
            
            if(yml.contains(key + "criticalwhirlwind")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.CRITICAL_WHIRLWIND);
                option.setPercent(yml.getDouble(key + "criticalwhirlwind"));
                options.add(option);
            }
            
            if(yml.contains(key + "furnacexpmultiplier")) {
                SkillAbilityOption option = new SkillAbilityOption(AbilityType.FURNACE_XP_BOOST);
                option.seconds = yml.getInt(key + "furnacexpmultiplier");
                options.add(option);
            }
            
            sa.setAbilityOptions(options);
        }
        
        for(String s : ((MemorySection) yml.get("perks")).getKeys(false)) {
            String key = "perks." + s + ".";
            
            String title = colorise(yml.getString(key + "title"));
            int cost = yml.getInt(key + "cost", 0);
            int max = yml.getInt(key + "max", -1);
            int multiplier = yml.getInt(key + "levelmultiplier", 2);
            
            commands = getListWithFallback(key + "commands");
            abilities = getListWithFallback(key + "abilities");
            descriptions = getListWithFallback(key + "descriptions", true);
            dependant = getListWithFallback(key + "dependant");
            
            Skill skill = Skill.getSkill(yml.getString(key + "category"));
            if(skill == null) {
                log("Failed to add perk " + s + ", Category invalid.");
                continue;
            }
            
            ItemStack is = LevelTreeBase.getItemStackFromString(yml.getString(key + "item"));
            
            SkillOption option = new SkillOption(s, title, max, multiplier, cost, is, descriptions, commands, abilities, dependant);
            skill.addOption(option);
        }
    }
    
    public List<String> getListWithFallback(String key) {
        return getListWithFallback(key, false);
    }
    
    public List<String> getListWithFallback(String key, boolean colorise) {
        List<String> list = new ArrayList<String>();
        
        if(yml.contains(key)) {
            list = yml.getStringList(key);
            if(colorise) {
                List<String> stuff = new ArrayList<String>();
                for(String s : list) {
                    stuff.add(colorise(s));
                }
                
                list = stuff;
            }
        }
        
        return list;
    }
}
