package fr.eazyender.donjon.donjon;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.eazyender.donjon.files.PlayerLevelStats;
import fr.eazyender.donjon.gui.InventoryGui;

public class LevelUtils {
	
	public static int[] pallierSkill = {5,10};
	
	public static void updateXp(Player player) {
		
		int level = PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player);
		long xp = PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player);
		if(xp > getXpNecessary(level+1)) {
			PlayerLevelStats.getPlayerLevelStats().setLevelDonjon(player, level+1);
			PlayerLevelStats.getPlayerLevelStats().setXpDonjon(player, 0);
			
			player.sendMessage("§8[§4Donjon§8] : §f" + "Vous êtes monté d'un niveau ! (niveau:" + (level+1) +")");
			PlayerLevelStats.getPlayerLevelStats().setSkillPoints(player, PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player)+1);
		}
		InventoryGui.updateInventory(player);
		
	}
	
	public static void updateSkill(Player player, int id) {
		
		for (int i = 0; i < pallierSkill.length; i++) {
			if(PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player, id) >= pallierSkill[i]) {
				switch(id) {
				case 1: 
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				}
			}
		}
		
	}
	
	public static ItemStack getAwardSkill(int id, int stape) {
		ItemStack item = getCustomItemWithLore(Material.GRAY_CONCRETE, "§fPas de récompense", false, 1, null);
		switch(id) {
		//ATQ
		case 1:
			switch(stape) {
			case 1:
				break;
			case 2:
				break;
			}
			break;
		//DEF
		case 2:
			switch(stape) {
			case 1:
				break;
			case 2:
				break;
			}
			break;
		//MAG
		case 3:
			switch(stape) {
			case 1:
				break;
			case 2:
				break;
			}
			break;
		}
		return item;
	}
	
	public static String getRankName(int level) {
		String rank = "§8Voyageur";
		if(level < 1) {rank = "§8Voyageur";}
		else if(level == 1) {rank = "§8Novice";}
		else if(level <= 5) {rank = "§8Adepte";}
		else if(level <= 10) {rank = "§8Aventurier";}
		return rank;
	}
	
	public static long getXpNecessary(int level) {
		
		long xp = 0;
		switch(level) {
		case 1: xp = 25;
			break;
		case 2: xp = 35;
			break;
		case 3: xp = 50;
			break;
		case 4: xp = 70;
			break;
		case 5: xp = 100;
			break;
		case 6: xp = 125;
			break;
		case 7: xp = 150;
			break;
		case 8: xp = 175;
			break;
		case 9: xp = 200;
			break;
		case 10: xp = 225;
			break;
		}
		
		return xp;
		
	}
	
	public static ItemStack getCustomItemWithLore(Material material, String customName, boolean EnchantEffect, int nbr, List<String> lore) {
		
		ItemStack item = new ItemStack(material, nbr);
		ItemMeta itemMeta = item.getItemMeta();
		if(lore != null) {
		itemMeta.setLore(lore);
		}
		if(customName != null) {itemMeta.setDisplayName(customName);}
		if(EnchantEffect) {itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 200, true);itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);}
		item.setItemMeta(itemMeta);
		
		
		return item;
		
	}

}
