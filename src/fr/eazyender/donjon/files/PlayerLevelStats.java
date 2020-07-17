package fr.eazyender.donjon.files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.eazyender.donjon.DonjonMain;

public class PlayerLevelStats {
	
	public static PlayerLevelStats playerLevelStats;
	
	 private static Map<UUID, Long> xpDonjonUnlock = new HashMap<UUID, Long>();
	 private static Map<UUID, Integer> levelDonjon = new HashMap<UUID, Integer>();
	 
	 private static Map<UUID, Long> xpRank = new HashMap<UUID, Long>();
	 private static Map<UUID, Integer> levelRank = new HashMap<UUID, Integer>();
	 
	 private static Map<UUID, Long> skillPoints = new HashMap<UUID, Long>();
	 private static Map<UUID, Integer> levelMagic = new HashMap<UUID, Integer>();
	 private static Map<UUID, Integer> levelDefense = new HashMap<UUID, Integer>();
	 private static Map<UUID, Integer> levelDamage = new HashMap<UUID, Integer>();
	
	  private static File levelStatsFile;
	  private static FileConfiguration levelStatsConfig;
	
	  public PlayerLevelStats() {
		  playerLevelStats = this;
		    registerFile();
		    for (Player ps : Bukkit.getOnlinePlayers()) {
		      loadPlayer(ps);
		    }
	  }
	  
	  public void onDisable() {
		  for (Player ps : Bukkit.getOnlinePlayers()) {
		  ConfigurationSection s = levelStatsConfig.getConfigurationSection(ps.getUniqueId().toString());
		  s.set("xpDonjon", xpDonjonUnlock.get(ps.getUniqueId()));
		  s.set("levelDonjon", levelDonjon.get(ps.getUniqueId()));
		  s.set("xpRank", xpRank.get(ps.getUniqueId()));
		  s.set("levelRank", levelRank.get(ps.getUniqueId()));
		  s.set("skillPoints", skillPoints.get(ps.getUniqueId()));
		  s.set("levelMagic", levelMagic.get(ps.getUniqueId()));
		  s.set("levelDefense", levelDefense.get(ps.getUniqueId()));
		  s.set("levelDamage", levelDamage.get(ps.getUniqueId()));
		
		  }
		    saveFile();
		  }
	  
	  public static void createPlayerLevelStats(Player p) {
		    xpDonjonUnlock.put(p.getUniqueId(), (long) 0);
		    levelDonjon.put(p.getUniqueId(), 0);
		    
		    xpRank.put(p.getUniqueId(), (long) 0);
		    levelRank.put(p.getUniqueId(), 0);
		    
		    skillPoints.put(p.getUniqueId(), (long) 3);
		    levelMagic.put(p.getUniqueId(), 0);
		    levelDefense.put(p.getUniqueId(), 0);
		    levelDamage.put(p.getUniqueId(), 0);
		    
		    
		    ConfigurationSection s = levelStatsConfig.createSection(p.getUniqueId().toString());  
		    s.set("xpDonjon", 0);
			s.set("levelDonjon", 1);
			s.set("xpRank", 0);
			s.set("levelRank", 1);
			s.set("skillPoints", 3);
			s.set("levelMagic", 0);
			s.set("levelDefense", 0);
			s.set("levelDamage", 0);
		    
		    saveFile();
		  }
	  
	  
	 private void registerFile() {
		 levelStatsFile = new File(DonjonMain.instance.getDataFolder(), "PlayerLevelStats.yml");
		 levelStatsConfig = YamlConfiguration.loadConfiguration(levelStatsFile);
		    saveFile();
		  }
	 
	 private static void saveFile() {
		    try {		    
		    	levelStatsConfig.save(levelStatsFile);
		    } catch (IOException iOException) {}
		  }
	 
	 public void loadPlayer(Player p) {
		    if (levelStatsConfig.contains(p.getUniqueId().toString())) {
		      ConfigurationSection s = levelStatsConfig.getConfigurationSection(p.getUniqueId().toString());
		      	long xp_donjon = s.getLong("xpDonjon");
		      	xpDonjonUnlock.put(p.getUniqueId(), xp_donjon);
		      	int level_donjon = s.getInt("levelDonjon");
			    levelDonjon.put(p.getUniqueId(), level_donjon);
			   
			    long xp_Rank = s.getLong("xpRank");
		      	xpRank.put(p.getUniqueId(), xp_Rank);
		      	int level_Rank = s.getInt("levelRank");
			    levelRank.put(p.getUniqueId(), level_Rank);
			    
			    long points_skill = s.getLong("skillPoints");
		      	skillPoints.put(p.getUniqueId(), points_skill);
		      	int points_atq = s.getInt("levelDamage");
			    levelDamage.put(p.getUniqueId(), points_atq);
			    int points_magic = s.getInt("levelMagic");
			    levelMagic.put(p.getUniqueId(), points_magic);
			    int points_def = s.getInt("levelDefense");
			    levelDefense.put(p.getUniqueId(), points_def);
		    } else {
		    	createPlayerLevelStats(p);
		    }
		  }
	 
	 public long getAStatsPoints(Player p,int id) {
		    if (playerExist(p) && id <= 3 && id > 0) {	
		    	switch(id) {
		    	case 1 : return levelDamage.get(p.getUniqueId());
		    	case 2 : return levelDefense.get(p.getUniqueId());
		    	case 3 : return levelMagic.get(p.getUniqueId());
		    	}
		    }else {System.out.println("Player doesn't exist");}
		    return 0;
		  }
	 public long getSkillPoints(Player p) {
		    if (playerExist(p)) {	
		    		return skillPoints.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");
		    return 0;}
		  }
	 public long getXpDonjon(Player p) {
		    if (playerExist(p)) {	
		    		return xpDonjonUnlock.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");
		    return 0;}
		  }
	 public int getLevelDonjon(Player p) {
		    if (playerExist(p)) {	
		    		return levelDonjon.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");
		    return 0;}
		  }
	 public long getXpRank(Player p) {
		    if (playerExist(p)) {	
		    		return xpRank.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");
		    return 0;}
		  }
	 public int getLevelRank(Player p) {
		    if (playerExist(p)) {	
		    		return levelRank.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");
		    return 0;}
		  }
	 
	 public int getLevelRank(OfflinePlayer p) {
		    if (playerExist(p)) {	
		    		return levelRank.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");
		    return 0;}
		  }
	 
	 public void setStatsPoints(Player p, int id, int newValue) {
		    if (playerExist(p) && id <= 3 && id > 0) {
		    	switch(id) {
		    	case 1 : levelDamage.replace(p.getUniqueId(), newValue);
		    	break;
		    	case 2 : levelDefense.replace(p.getUniqueId(), newValue);
		    	break;
		    	case 3 : levelMagic.replace(p.getUniqueId(), newValue);
		    	break;
		    	}
		    }else {System.out.println("Player doesn't exist");}
		  }
	 
	 public void setSkillPoints(Player p, long newValue) {
		    if (playerExist(p)) {
		    	skillPoints.replace(p.getUniqueId(), newValue);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 
	 public void setXpDonjon(Player p, long newValue) {
		    if (playerExist(p)) {
		    	xpDonjonUnlock.replace(p.getUniqueId(), newValue);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 public void setLevelDonjon(Player p, int newValue) {
		    if (playerExist(p)) {
		    	levelDonjon.replace(p.getUniqueId(), newValue);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 
	 public void setXpRank(Player p, long newValue) {
		    if (playerExist(p)) {
		    	xpRank.replace(p.getUniqueId(), newValue);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 public void setLevelRank(Player p, int newValue) {
		    if (playerExist(p)) {
		    	levelRank.replace(p.getUniqueId(), newValue);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 
	
	 
	 public void unloadPlayer(Player p) {
		 	onDisable();
		    if (playerExist(p)) {
		    	levelDamage.remove(p.getUniqueId());
		    	levelDefense.remove(p.getUniqueId());
		    	levelMagic.remove(p.getUniqueId());
		    	skillPoints.remove(p.getUniqueId());
		    	xpDonjonUnlock.remove(p.getUniqueId());
		    	levelDonjon.remove(p.getUniqueId());
		    	xpRank.remove(p.getUniqueId());
		    	levelRank.remove(p.getUniqueId());
		    }
		  }
	 
	 public static PlayerLevelStats getPlayerLevelStats() { return playerLevelStats;  }
	 
	 public boolean playerExist(Player p) { return xpDonjonUnlock.containsKey(p.getUniqueId()) && levelDonjon.containsKey(p.getUniqueId()); }
	 
	 public boolean playerExist(OfflinePlayer p) { return xpDonjonUnlock.containsKey(p.getUniqueId()) && levelDonjon.containsKey(p.getUniqueId()); }


}

