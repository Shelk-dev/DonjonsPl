package fr.eazyender.donjon.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.eazyender.donjon.files.PlayerLevelStats;
import fr.eazyender.donjon.potion.IPotionRecipe;
import fr.eazyender.donjon.potion.PotionUtils;
import fr.eazyender.donjon.potion.RecipePotions;

public class CraftPotionsGui implements Listener{
	
	public static void createMainGui(Player player){
		
		Inventory inv = Bukkit.createInventory(null, 54 , "§f§lAlchimie : §8" + player.getDisplayName());
		
		for (int i = 0; i < RecipePotions.getRecipesUnlock(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)).size(); i++) {
			
			IPotionRecipe recipe = RecipePotions.getRecipesUnlock(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)).get(i);
			
			ItemStack logo = recipe.getCraft().clone();
			ItemMeta im = logo.getItemMeta();
			List<String> lore = new ArrayList<String>();
			lore.add("§fRang : " + RecipePotions.getDifficultyOfRecipe(recipe));
			lore.add("Temps nécessaire : " + recipe.getTime());
			
			logo.setItemMeta(im);
			
		}
		
		for (int i = inv.getSize()-9; i < inv.getSize(); i++) {
			if(i != inv.getSize()-5) {
			inv.setItem(i, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
			}
		}

		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
	}
	
	public static void createRecipeGui(Player player, IPotionRecipe recipe) {
		
		Inventory inv = Bukkit.createInventory(null, 27 , "§f§lAlchimie["+ PotionUtils.getIdPotionByItem(recipe.getCraft()) +"]:§8" + player.getDisplayName());
		
		for (int i = 0; i < 9; i++) {
			if(i != 4) {
			inv.setItem(i, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
			}
			else {
			inv.setItem(i, getCustomItemWithLore(Material.STICK, "§2§lCRAFT", false, 1, null));	
			}
		}
		
		for (int i = inv.getSize()-9; i < inv.getSize(); i++) {
			if(i != inv.getSize()-5) {
			inv.setItem(i, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
			}
			else {
			inv.setItem(i, getCustomItemWithLore(Material.BARRIER, "§2§lANNULER", false, 1, null));	
			}
		}

		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
		
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
