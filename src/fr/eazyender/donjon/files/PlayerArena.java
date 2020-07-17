package fr.eazyender.donjon.files;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.eazyender.donjon.DonjonMain;

/**
 * @author Ender
 *
 */
public class PlayerArena {
		
		public static PlayerArena playerArena;
		
		 public static Map<String, Long> kills = new HashMap<String, Long>();
		 public static Map<String, Integer> deaths = new HashMap<String, Integer>();
		
		  private static File PlayerArenaFile;
		  private static FileConfiguration PlayerArenaConfig;
		
		  public PlayerArena() {
			  playerArena = this;
			    registerFile();
			    for (Player ps : Bukkit.getOnlinePlayers()) {
			      loadPlayer(ps);
			    }
			    for (OfflinePlayer ps : Bukkit.getOfflinePlayers()) {
				      loadPlayer(ps);
				    }
		  }
		  
		  public void onDisable() {
			  for (Player ps : Bukkit.getOnlinePlayers()) {
			  ConfigurationSection s = PlayerArenaConfig.getConfigurationSection(ps.getUniqueId().toString());
			  s.set("kills", kills.get(ps.getUniqueId().toString()));
			  s.set("deaths", deaths.get(ps.getUniqueId().toString()));
			
			  }
			  for (OfflinePlayer ps : Bukkit.getOfflinePlayers()) {
				  ConfigurationSection s = PlayerArenaConfig.getConfigurationSection(ps.getUniqueId().toString());
				  s.set("kills", kills.get(ps.getUniqueId().toString()));
				  s.set("deaths", deaths.get(ps.getUniqueId().toString()));
				
				  }
			    saveFile();
			  }
		  
		  public static void createPlayerArena(Player p) {
			  	kills.put(p.getUniqueId().toString(), (long) 0);
			    deaths.put(p.getUniqueId().toString(), 0);		    
			    
			    ConfigurationSection s = PlayerArenaConfig.createSection(p.getUniqueId().toString());  
			    s.set("kills", 0);
				s.set("deaths", 0);
			    
			    saveFile();
			  }
		  
		  public static void createPlayerArena(OfflinePlayer p) {
			  	kills.put(p.getUniqueId().toString(), (long) 0);
			    deaths.put(p.getUniqueId().toString(), 0);		    
			    
			    ConfigurationSection s = PlayerArenaConfig.createSection(p.getUniqueId().toString());  
			    s.set("kills", 0);
				s.set("deaths", 0);
			    
			    saveFile();
			  }
		  
		  public static Map<String, Long> getTop(){

				Map<String, Long> sortedMap = kills.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed())
						.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
				
				return sortedMap;
		  }
		  
		  
		 private void registerFile() {
			 PlayerArenaFile = new File(DonjonMain.instance.getDataFolder(), "PlayerArenaConfig.yml");
			 PlayerArenaConfig = YamlConfiguration.loadConfiguration(PlayerArenaFile);
			    saveFile();
			  }
		 
		 private static void saveFile() {
			    try {		    
			    	PlayerArenaConfig.save(PlayerArenaFile);
			    } catch (IOException iOException) {}
			  }
		 
		 public void loadPlayer(Player p) {
			    if (PlayerArenaConfig.contains(p.getUniqueId().toString()) && !kills.containsKey(p.getUniqueId().toString())) {
			      ConfigurationSection s = PlayerArenaConfig.getConfigurationSection(p.getUniqueId().toString());
			      	long killsi = s.getLong("kills");
			      	kills.put(p.getUniqueId().toString(), killsi);
			      	int deathsi = s.getInt("deaths");
			      	deaths.put(p.getUniqueId().toString(), deathsi);
			    } else {
			    	createPlayerArena(p);
			    }
			  }
		 
		 public void loadPlayer(OfflinePlayer p) {
			    if (PlayerArenaConfig.contains(p.getUniqueId().toString()) && !kills.containsKey(p.getUniqueId().toString())) {
			      ConfigurationSection s = PlayerArenaConfig.getConfigurationSection(p.getUniqueId().toString());
			      	long killsi = s.getLong("kills");
			      	kills.put(p.getUniqueId().toString(), killsi);
			      	int deathsi = s.getInt("deaths");
			      	deaths.put(p.getUniqueId().toString(), deathsi);
			    } else {
			    	createPlayerArena(p);
			    }
			  }
		 
		 public long getKills(Player p) {
			    if (playerExist(p)) {	
			    		return kills.get(p.getUniqueId().toString());
			    }else {System.out.println("Player doesn't exist");
			    return 0;}
			  }
		 public int getDeaths(Player p) {
			    if (playerExist(p)) {	
			    		return deaths.get(p.getUniqueId().toString());
			    }else {System.out.println("Player doesn't exist");
			    return 0;}
			  }
		 
		 public long getKills(OfflinePlayer p) {
			    if (playerExist(p)) {	
			    		return kills.get(p.getUniqueId().toString());
			    }else {System.out.println("Player doesn't exist");
			    return 0;}
			  }
		 public int getDeaths(OfflinePlayer p) {
			    if (playerExist(p)) {	
			    		return deaths.get(p.getUniqueId().toString());
			    }else {System.out.println("Player doesn't exist");
			    return 0;}
			  }
		 
		 
		 public void setKills(Player p, long newValue) {
			    if (playerExist(p)) {
			    	kills.replace(p.getUniqueId().toString(), newValue);
			    }else {System.out.println("Player doesn't exist");}
			  }
		 public void setDeaths(Player p, int newValue) {
			    if (playerExist(p)) {
			    	deaths.replace(p.getUniqueId().toString(), newValue);
			    }else {System.out.println("Player doesn't exist");}
			  }
		 
		 /**public void unloadPlayer(Player p) {
			 	onDisable();
			    if (playerExist(p)) {
			    	kills.remove(p.getUniqueId());
			    	deaths.remove(p.getUniqueId());
			    }
			  }*/
		 
		 public static PlayerArena getPlayerArena() { return playerArena;  }
		 
		 public boolean playerExist(Player p) { return kills.containsKey(p.getUniqueId().toString()) && deaths.containsKey(p.getUniqueId().toString()); }
		 public boolean playerExist(OfflinePlayer p) { return kills.containsKey(p.getUniqueId().toString()) && deaths.containsKey(p.getUniqueId().toString()); }


	}
