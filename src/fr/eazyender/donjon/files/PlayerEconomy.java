package fr.eazyender.donjon.files;

import fr.eazyender.donjon.DonjonMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerEconomy {

    public static PlayerEconomy playerEconomy;

    private static Map<UUID, Long> money = new HashMap<UUID, Long>();

    private static File economyFile;
    private static FileConfiguration economyConfig;

    public PlayerEconomy() {
        playerEconomy = this;
        registerFile();
        for (Player ps : Bukkit.getOnlinePlayers()) {
            loadPlayer(ps);
        }
    }

    public void onDisable() {
        for (Player ps : Bukkit.getOnlinePlayers()) {
            ConfigurationSection s = economyConfig.getConfigurationSection(ps.getUniqueId().toString());
            s.set("money", money.get(ps.getUniqueId()));

        }
        saveFile();
    }

    public static void createPlayerEconomy(Player p) {
        money.put(p.getUniqueId(), (long) 0);

        ConfigurationSection s = economyConfig.createSection(p.getUniqueId().toString());
        s.set("money", 0);

        saveFile();
    }


    private void registerFile() {
        economyFile = new File(DonjonMain.instance.getDataFolder(), "PlayerEconomy.yml");
        economyConfig = YamlConfiguration.loadConfiguration(economyFile);
        saveFile();
    }

    private static void saveFile() {
        try {
            economyConfig.save(economyFile);
        } catch (IOException iOException) {}
    }

    public void loadPlayer(Player p) {
        if (economyConfig.contains(p.getUniqueId().toString())) {
            ConfigurationSection s = economyConfig.getConfigurationSection(p.getUniqueId().toString());
            long moneyl = s.getLong("money");
            money.put(p.getUniqueId(), moneyl);
        } else {
            createPlayerEconomy(p);
        }
    }

    public long getMoney(Player p) {
        if (playerExist(p)) {
            return money.get(p.getUniqueId());
        }else {System.out.println("Player doesn't exist");
            return 0;}
    }
    public void setMoney(Player p, long newValue) {
        if (playerExist(p)) {
            money.replace(p.getUniqueId(), newValue);
        }else {System.out.println("Player doesn't exist");}
    }
    public void unloadPlayer(Player p) {
        onDisable();
        if (playerExist(p)) {
            money.remove(p.getUniqueId());
        }
    }

    public static PlayerEconomy getEconomy() { return playerEconomy;  }

    public boolean playerExist(Player p) { return money.containsKey(p.getUniqueId()); }


}


