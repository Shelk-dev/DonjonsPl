package fr.eazyender.donjon.arena;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.eazyender.donjon.files.PlayerLevelStats;

public class ArenaUtils {
	
	public static void actualizeLevel(Player player) {
		
		long xp = PlayerLevelStats.getPlayerLevelStats().getXpRank(player);
		int level = PlayerLevelStats.getPlayerLevelStats().getLevelRank(player);
		
		if(xp >= xpRequired(level+1)) {
			PlayerLevelStats.getPlayerLevelStats().setLevelRank(player, level+1);
			PlayerLevelStats.getPlayerLevelStats().setXpRank(player, xp - xpRequired(level+1));
			
			player.sendMessage("§7[§6§lArena§r§7]§f : " + "Vous avez gagné 1 level !");
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			
			xp = xp - xpRequired(level+1);
			level = level+1;
			if(xp > xpRequired(level+1)) {
				actualizeLevel(player);
			}
		}
		
	}
	
	public static Location getRandomSpawnLocation() {
		
		int rdm = RandomNumber(1,5);
		switch(rdm) {
		case 1: return new Location(null, 3.38, 149.00, 175.42, 0, 0);
		case 2: return new Location(null, 24.99, 149.00, 219.56, 90, 0);
		case 3: return new Location(null, 8.81, 149.00, 250.87, 145, 0);
		case 4: return new Location(null, -44.34, 149.00, 251.43, 180, 0);
		case 5: return new Location(null, -53.17, 149.00, 193.86, -45, 0);
		default: return new Location(null, 3.38, 149.00, 175.42, 0, 0);
		}
		
	}
	
	public static long xpRequired(int level) {
		
		long xp = 99999999;
		
		switch(level) {
		case 1: xp = 50;
			break;
		case 2: xp = 100;
			break;
		case 3: xp = 150;
			break;
		case 4: xp = 225;
			break;
		case 5:
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		}
		
		return xp;
	}
	
	public static String getNameOfRank(int level) {
		switch(level) {
		case 1: return "§7Porcelaine";
		case 2: return "§6Cuivre";
		case 3: return "§fFer";
		case 4: return "§6Bronze";
		case 5: return "§7Argent";
		case 6: return "§eOr";
		case 7: return "§6§lPlatine";
		case 8: return "§3§lDiamant";
		case 9: return "§a§lEmeraude";
		case 10: return "§1§lMythril";
		case 11: return "§9§lSaphir";
		case 12: return "§8§lObsidienne";
		case 13: return "§e§lOrichalque";
		case 14: return "§c§lAdamantite";
		case 15: return "§d§lImmortel";
		default: return "§fNovice";
		}
	}
	
	public static int getPositionOfTop(Player player, Map<String, Long> top) {
		int position = 0;
		
		List<String> keys = new ArrayList<>(top.keySet());
		for (int i = 0; i < keys.size(); i++) {
			if(keys.get(i).equals(player.getUniqueId().toString())) {
				position = i+1;
			}
		}
		
		return position;
	}
	
	public static int getPositionOfTop(OfflinePlayer player, Map<String, Long> top) {
		int position = 0;
		
		List<String> keys = new ArrayList<>(top.keySet());
		for (int i = 0; i < keys.size(); i++) {
			if(keys.get(i).equals(player.getUniqueId().toString())) {
				position = i+1;
			}
		}
		
		return position;
	}
	
	public static ItemStack getLogoOfRank(int level) {
		
		return null;
	}
	
	private static int RandomNumber(int Min , int Max)
    {
		if(Min == Max) {return Max;}
		Min = Min-1;
    	Random rand = new Random();
    	int randomNbr = rand.nextInt(Max - Min) + Min;
    	
    	if(randomNbr > Max){randomNbr = Max;}
    	if(randomNbr <= Min){randomNbr = Max;}
    return randomNbr;}

}
