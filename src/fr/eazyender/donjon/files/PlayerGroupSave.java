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

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.utils.PlayerGroup;

public class PlayerGroupSave {

    public static PlayerGroupSave playerGrp;

    private static Map<UUID, PlayerGroup> groups = new HashMap<UUID, PlayerGroup>();

    private static File playerGrpFile;
    private static FileConfiguration playerGrpConfig;

    public PlayerGroupSave() {
    	playerGrp = this;
        registerFile();
        for (Player ps : Bukkit.getOnlinePlayers()) {
            loadPlayer(ps);
        }
    }

    public void onDisable() {
        for (Player ps : Bukkit.getOnlinePlayers()) {
            ConfigurationSection s = playerGrpConfig.getConfigurationSection(ps.getUniqueId().toString());
            s.set("group", groups.get(ps.getUniqueId()));

        }
        saveFile();
    }

    public static void createPlayerGroup(Player p) {
        groups.put(p.getUniqueId(), new PlayerGroup(p));

        ConfigurationSection s = playerGrpConfig.createSection(p.getUniqueId().toString());
        s.set("group", groups.get(p.getUniqueId()));

        saveFile();
    }


    private void registerFile() {
        playerGrpFile = new File(DonjonMain.instance.getDataFolder(), "PlayerGroups.yml");
        playerGrpConfig = YamlConfiguration.loadConfiguration(playerGrpFile);
        saveFile();
    }

    private static void saveFile() {
        try {
        	playerGrpConfig.save(playerGrpFile);
        } catch (IOException iOException) {}
    }

    public void loadPlayer(Player p) {
        if (playerGrpConfig.contains(p.getUniqueId().toString())) {
            ConfigurationSection s = playerGrpConfig.getConfigurationSection(p.getUniqueId().toString());
            PlayerGroup group = (PlayerGroup)s.get("group");
            groups.put(p.getUniqueId(), group);
        } else {
        	createPlayerGroup(p);
        }
    }
    
    public List<PlayerGroup> getAllGroup() {
    	List<PlayerGroup> newgroups = new ArrayList<PlayerGroup>();
    	  for (Player ps : Bukkit.getOnlinePlayers()) {
    		  newgroups.add(getGroup(ps));
    	  }
          return newgroups;
    }

    public PlayerGroup getGroup(Player p) {
        if (playerExist(p)) {
            return groups.get(p.getUniqueId());
        }else {System.out.println("Player doesn't exist");
            return null;}
    }
    public void setGroup(Player p, PlayerGroup newValue) {
        if (playerExist(p)) {
            groups.replace(p.getUniqueId(), newValue);
        }else {System.out.println("Player doesn't exist");}
    }
    public void unloadPlayer(Player p) {
        onDisable();
        if (playerExist(p)) {
            groups.remove(p.getUniqueId());
        }
    }

    public static PlayerGroupSave getPlayerGroup() { return playerGrp;  }

    public boolean playerExist(Player p) { return groups.containsKey(p.getUniqueId()); }


}
