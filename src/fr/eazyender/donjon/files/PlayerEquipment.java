package fr.eazyender.donjon.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.eazyender.donjon.DonjonMain;

public class PlayerEquipment {
	
	public static PlayerEquipment playerEquipment;
	
	 private static Map<UUID, List<Integer>> weapons = new HashMap<UUID, List<Integer>>();
	 private static Map<UUID, List<Integer>> spells = new HashMap<UUID, List<Integer>>();
	 private static Map<UUID, List<String>> potions = new HashMap<UUID, List<String>>();
	 private static Map<UUID, List<String>> ressources = new HashMap<UUID, List<String>>();
	
	  private static File EquipmentFile;
	  private static FileConfiguration EquipmentConfig;
	
	  public PlayerEquipment() {
		  playerEquipment = this;
		    registerFile();
		    for (Player ps : Bukkit.getOnlinePlayers()) {
		      loadPlayer(ps);
		    }
	  }
	  
	  public void onDisable() {
		  for (Player ps : Bukkit.getOnlinePlayers()) {
		  ConfigurationSection s = EquipmentConfig.getConfigurationSection(ps.getUniqueId().toString());
		  s.set("weapons", weapons.get(ps.getUniqueId()));
		  s.set("potions", potions.get(ps.getUniqueId()));
		  s.set("spells", spells.get(ps.getUniqueId()));
		  s.set("ressources", ressources.get(ps.getUniqueId()));
		
		  }
		    saveFile();
		  }
	  
	  public static void createPlayerEquipment(Player p) {
		    weapons.put(p.getUniqueId(), new ArrayList<Integer>());
		    spells.put(p.getUniqueId(), new ArrayList<Integer>());
		    potions.put(p.getUniqueId(), new ArrayList<String>());
		    
		    ConfigurationSection s = EquipmentConfig.createSection(p.getUniqueId().toString());  
		    s.set("weapons", new ArrayList<Integer>());
		    s.set("potions", new ArrayList<Double>());
			s.set("spells", new ArrayList<Integer>());
			s.set("ressources", new ArrayList<String>());
		    
		    saveFile();
		  }
	  
	  
	 private void registerFile() {
		 EquipmentFile = new File(DonjonMain.instance.getDataFolder(), "PlayerEquipment.yml");
		 EquipmentConfig = YamlConfiguration.loadConfiguration(EquipmentFile);
		    saveFile();
		  }
	 
	 private static void saveFile() {
		    try {		    
		    	EquipmentConfig.save(EquipmentFile);
		    } catch (IOException iOException) {}
		  }
	 
	 public void loadPlayer(Player p) {
		    if (EquipmentConfig.contains(p.getUniqueId().toString())) {
		      ConfigurationSection s = EquipmentConfig.getConfigurationSection(p.getUniqueId().toString());
		      	List<Integer> weapons_list = s.getIntegerList("weapons");
		      	weapons.put(p.getUniqueId(), weapons_list);
		      	List<String> potions_list = s.getStringList("potions");
			    potions.put(p.getUniqueId(), potions_list);
		      	List<Integer> spells_list = s.getIntegerList("spells");
			    spells.put(p.getUniqueId(), spells_list);
			    List<String> ressources_list = s.getStringList("ressources");
			    ressources.put(p.getUniqueId(), ressources_list);
		    } else {
		    	createPlayerEquipment(p);
		    }
		  }
	 
	 
	 public List<String> getRessources(Player p) {
		    if (playerExist(p)) {	
		    	return ressources.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");}
		    return null;
		  }
	 public List<Integer> getWeapons(Player p) {
		    if (playerExist(p)) {	
		    	return weapons.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");}
		    return null;
		  }
	 public List<Integer> getSpells(Player p) {
		    if (playerExist(p)) {	
		    	return spells.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");}
		    return null;
		  }
	 public List<String> getPotions(Player p) {
		    if (playerExist(p)) {	
		    	return potions.get(p.getUniqueId());
		    }else {System.out.println("Player doesn't exist");}
		    return null;
		  }
	 
	 
	 public void setWeapons(Player p, List<Integer> newIT) {
		    if (playerExist(p)) {
		    	weapons.replace(p.getUniqueId(), newIT);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 public void setRessources(Player p, List<String> newIT) {
		    if (playerExist(p)) {
		    	ressources.replace(p.getUniqueId(), newIT);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 
	 public void setSpells(Player p, List<Integer> newSP) {
		    if (playerExist(p)) {
		    	spells.replace(p.getUniqueId(), newSP);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 
	 public void setPotions(Player p, List<String> newP) {
		    if (playerExist(p)) {
		    	potions.replace(p.getUniqueId(), newP);
		    }else {System.out.println("Player doesn't exist");}
		  }
	 
	
	 
	 public void unloadPlayer(Player p) {
		 	onDisable();
		    if (playerExist(p)) {
		    	weapons.remove(p.getUniqueId());
		    	spells.remove(p.getUniqueId());
		    }
		  }
	 
	 public static PlayerEquipment getPlayerEquipment() { return playerEquipment;  }
	 
	 public boolean playerExist(Player p) { return weapons.containsKey(p.getUniqueId()) && spells.containsKey(p.getUniqueId()); }


}


