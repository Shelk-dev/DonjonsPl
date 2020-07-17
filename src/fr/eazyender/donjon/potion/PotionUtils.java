package fr.eazyender.donjon.potion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PotionUtils {
	
	/** POTION ENTITY FRIENDLY : 
	 * -NOTHING*/
	public static int potionsNumber = 2;
	
	public static String getIdPotionByItem(ItemStack item) {
		
		String id = "0";
		List<ItemStack> all = new ArrayList<ItemStack>();
		
		List<String> str = new ArrayList<String>();
		str.add("§fPetite potion vous régénérant un peu de vie");
		str.add("§4§lType§r§7 : " + "§4§lSoin");
		str.add("§4§lTemps de recharge§r§7 : " +( PotionSmallHeal.basicCooldown/1000));
		ItemStack item1 = getCustomItemWithLore(Material.FEATHER, "§f§lPetite potion de §4§lVie", false, 1, str);
		all.add(item1);
		List<String> str1 = new ArrayList<String>();
		str1.add("§fPetite potion vous régénérant un peu de mana");
		str1.add("§9§lType§r§7 : " + "§9§lMana");
		str1.add("§9§lTemps de recharge§r§7 : " +( PotionSmallMana.basicCooldown/1000));
		ItemStack item2 = getCustomItemWithLore(Material.FEATHER, "§f§lPetite potion de §9§lMana", false, 1, str1);
		all.add(item2);
		
		
		for (int i = 0; i < all.size(); i++) {
			if(item.equals(all.get(i))) {
				id = String.valueOf(i+1);
			}
		}
		
		return id;
	}
	
	public static ItemStack getItemPotionById(String id) {
		
		ItemStack item = null;
		
		id = id.split("\\:")[0];
		int value = Integer.parseInt(id);
		
		switch(value) {
		case 1: 
			List<String> str = new ArrayList<String>();
			str.add("§fPetite potion vous régénérant un peu de vie");
			str.add("§4§lType§r§7 : " + "§4§lSoin");
			str.add("§4§lTemps de recharge§r§7 : " +( PotionSmallHeal.basicCooldown/1000));
			item = getCustomItemWithLore(Material.FEATHER, "§f§lPetite potion de §4§lVie", false, 1, str);
			break;
		case 2:
			List<String> str1 = new ArrayList<String>();
			str1.add("§fPetite potion vous régénérant un peu de mana");
			str1.add("§9§lType§r§7 : " + "§9§lMana");
			str1.add("§9§lTemps de recharge§r§7 : " +( PotionSmallMana.basicCooldown/1000));
			item = getCustomItemWithLore(Material.FEATHER, "§f§lPetite potion de §9§lMana", false, 1, str1);
		}
		
		return item;
		
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
