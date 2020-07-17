package fr.eazyender.donjon.donjon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.eazyender.donjon.files.PlayerEquipment;

public class LootUtils {
	
	public static ItemStack getLootById(int id) {
		ItemStack item = null;
		List<String> lore = new ArrayList<String>();
		switch(id) {
		case 1: item = getDrop(Material.STICK, "§fEssence de la §2foret", false, 1, "§fEtrange substance mouvante remplis d'§2énergie", "Commun");
			break;
		case 2: item = getDrop(Material.STICK, "§fCrystal de §6Terre", false, 1, "§fCrystal faisant de la lumière", "Commun");
			break;
		case 3: item = getDrop(Material.STICK, "§fFruit §4Rouge", false, 1, "§fFruit pouvant servir aux potions", "Non Commun");
			break;
		case 4: item = getDrop(Material.STICK, "§fEssence d'§7air", false, 1, "§fEtrange substance mouvante remplis d'§7énergie", "Non Commun");
			break;
		}
		
		return item;
	}
	
	public static int getIdByLoot(ItemStack item) {
		int id = 0;
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.add(getDrop(Material.STICK, "§fEssence de la §2foret", false, 1, "§fEtrange substance mouvante remplis d'§2énergie", "Commun"));
		items.add(getDrop(Material.STICK, "§fCrystal de §6Terre", false, 1, "§fCrystal faisant de la lumière", "Commun"));
		items.add(getDrop(Material.STICK, "§fFruit §4Rouge", false, 1, "§fFruit pouvant servir aux potions", "Non Commun"));
		items.add(getDrop(Material.STICK, "§fEssence d'§7air", false, 1, "§fEtrange substance mouvante remplis d'§7énergie", "Non Commun"));
		
		for (int i = 0; i < items.size(); i++) {
			if(items.get(i).equals(item)) {
				id = i+1;
			}
		}
		
		return id;
	}
	
	public static ItemStack getWeaponById(int id) {
		ItemStack item = null;
		switch(id) {
		case 1: item = getWeaponDrop(Material.STONE_SWORD, "§fEpee en §cGranite", false, 1, "§fArme faite à partir d'un certain type de roche", "E");
			break;
		case 2: item = getWeaponDrop(Material.LEGACY_WOOD_SWORD, "§fBaton magique en §6bois simple", false, 1, "§fUn simple baton infusé de magie", "F");
			break;
		}
		
		return item;
	}
	
	public static int getIDWeaponByItem(ItemStack item) {
		if(item.equals(getWeaponDrop(Material.STONE_SWORD, "§fEpee en §cGranite", false, 1, "§fArme faite à partir d'un certain type de roche", "E"))) {
			return 1;
		}else if(item.equals(getWeaponDrop(Material.WOODEN_SWORD, "§fBaton magique en §6bois simple", false, 1, "§fUn simple baton infusé de magie", "F"))) {
			return 2;
		}
		return 0;
	}
	
	public static List<ItemStack> getWeaponLootOfMob(String name, int difficulty) {
		
		List<ItemStack> loots = new ArrayList<ItemStack>();
		
		switch(name) {
		case "GRANITE_KNIGHT_SQUELETON":
			if(RandomNumber(1,1000) <= 2) {loots.add(LootUtils.getWeaponById(1));}
			break;	
		case "GRANITE_KING_SQUELETON":
			if(RandomNumber(1,100) <= 10) {loots.add(LootUtils.getWeaponById(1));}
			break;
		}
		
		return loots;
	}
	
	public static List<ItemStack> getLootOfMob(String name, int difficulty) {
		
		List<ItemStack> loots = new ArrayList<ItemStack>();
		
		switch(name) {
		case "BUSH_ZOMBIE":
			if(RandomNumber(1,100) <= 50) {loots.add(LootUtils.getLootById(1));}
			if(RandomNumber(1,100) <= 10) {loots.add(LootUtils.getLootById(3));}
			break;
		case "BUSH_SQUELETON":
			if(RandomNumber(1,100) <= 50) {loots.add(LootUtils.getLootById(1));}
			if(RandomNumber(1,100) <= 10) {loots.add(LootUtils.getLootById(3));}
			break;
		case "GRANITE_KNIGHT_SQUELETON":
			if(RandomNumber(1,100) <= 50) {loots.add(LootUtils.getLootById(2));}
			break;	
		case "GRANITE_ARCHER_SQUELETON":
			if(RandomNumber(1,100) <= 50) {loots.add(LootUtils.getLootById(2));}
			break;
		case "GRANITE_KING_SQUELETON":
			if(RandomNumber(1,100) <= 100) {loots.add(LootUtils.getLootById(2));}
			break;
		case "BUSH_PHANTOM":
			if(RandomNumber(1,100) <= 50) {loots.add(LootUtils.getLootById(1));}
			if(RandomNumber(1,100) <= 15) {loots.add(LootUtils.getLootById(4));}
			break;
		}
		
		return loots;
	}
	
	public static int getLootAmplifier(int difficulty) {
		
		return difficulty;
	}
	
	
	public static ItemStack getDrop(Material material, String customName, boolean EnchantEffect, int nbr, String description, String rarety) {
		
		ItemStack item = new ItemStack(material, nbr);
		ItemMeta itemMeta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(description);
		lore.add("§fRareté : " + rarety);
		itemMeta.setLore(lore);
		if(customName != null) {itemMeta.setDisplayName(customName);}
		if(EnchantEffect) {itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 200, true);itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);}
		item.setItemMeta(itemMeta);
		
		
		return item;
		
	}
	
	public static ItemStack getWeaponDrop(Material material, String customName, boolean EnchantEffect, int nbr, String description, String rarety) {
		
		ItemStack item = new ItemStack(material, nbr);
		ItemMeta itemMeta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore.add(description);
		lore.add("§fRank : " + rarety);
		itemMeta.setLore(lore);
		if(customName != null) {itemMeta.setDisplayName(customName);}
		if(EnchantEffect) {itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 200, true);itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);}
		itemMeta.setUnbreakable(true);
		item.setItemMeta(itemMeta);
		
		
		return item;
		
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
	
	public static void addItemsToRessources(Player player, List<ItemStack> items) {
		
		List<String> ids = PlayerEquipment.getPlayerEquipment().getRessources(player);
		
		for (int j = 0; j < items.size(); j++) {
			boolean contain = false;
			for (int i = 0; i < ids.size(); i++) {
				
				String build = getIdByLoot(items.get(j)) + ":";
				if(ids.get(i).contains(build)) {
					
					int decimal = Integer.parseInt(ids.get(i).split("\\:")[1]);
					decimal = decimal + items.get(j).getAmount();
					build = build + decimal;
					ids.set(i, build);
					contain = true;
				}
					
			}
			if(!contain) {
				ids.add(getIdByLoot(items.get(j)) + ":" + items.get(j).getAmount());
			}
		}
		
		PlayerEquipment.getPlayerEquipment().setRessources(player, ids);
		
	}

}
