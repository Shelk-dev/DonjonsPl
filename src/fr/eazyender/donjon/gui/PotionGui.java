package fr.eazyender.donjon.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.eazyender.donjon.files.PlayerEquipment;
import fr.eazyender.donjon.potion.PotionUtils;

public class PotionGui implements Listener{
	
	public static HashMap<Player, List<String>> potions_choose = new HashMap<Player, List<String>>();

	public static void createGui(Player player){
		
		Inventory inv = Bukkit.createInventory(null, 36 , "§f§lPotions : §8" + player.getDisplayName());
		if(!potions_choose.containsKey(player)) potions_choose.put(player, new ArrayList<String>());
		
		List<String> potions = PlayerEquipment.getPlayerEquipment().getPotions(player);
		List<String> newP = new ArrayList<String>();
		for (int i = 0; i < potions.size(); i++) {
			String str = potions.get(i);
			if(Integer.parseInt(str.split("\\:")[1]) > 64) {
				int decimal = Integer.parseInt(str.split("\\:")[1]);
				for (int j = 0; j < (int)(decimal / 64); j++) {
					newP.add(str.split("\\:")[0]+":"+64);
				}
				newP.add(str.split("\\:")[0]+":"+(decimal%64));
			}else {newP.add(str);}
		}
		potions = newP;
		
		if(!potions.isEmpty())
		for (int i = 0; i < potions.size(); i++) {
			String str = String.valueOf(potions.get(i));
			String[] parts = str.split("\\:");
			int decimal = Integer.parseInt(parts[1]);
			if(potions_choose.get(player).isEmpty()){
				ItemStack potion = PotionUtils.getItemPotionById(str);
				potion.setAmount(decimal);
				inv.setItem(i, potion);
			}else {
				if(potions_choose.get(player).contains(potions.get(i))) {
					ItemStack potion = PotionUtils.getItemPotionById(str);
					ItemMeta itemMeta = potion.getItemMeta();
					itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 200, true);itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					potion.setItemMeta(itemMeta);
					potion.setAmount(decimal);
					inv.setItem(i, potion);
			}else {
				ItemStack potion = PotionUtils.getItemPotionById(str);
				potion.setAmount(decimal);
				inv.setItem(i, potion);
			}
		}
		}
		
		
		for (int i = inv.getSize()-9; i < inv.getSize(); i++) {
			if(i != inv.getSize()-5) {
			inv.setItem(i, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
			}
			else {
			inv.setItem(i, getCustomItemWithLore(Material.BARRIER, "§4§lCLEAR LA SELECTION", false, 1, null));	
			}
		}
		
		player.openInventory(inv);
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(current != null && event.getView().getTitle().equals("§f§lPotions : §8" + player.getDisplayName())) {
			if(current.getType().equals(Material.FEATHER)) {
				
				ItemStack currentWithoutEnchant = current.clone();
				currentWithoutEnchant.setAmount(1);
				ItemMeta im = currentWithoutEnchant.getItemMeta();
				im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.removeEnchant(Enchantment.DAMAGE_ALL);
				currentWithoutEnchant.setItemMeta(im);
				
				boolean contain = false;
				for (int i = 0; i < potions_choose.get(player).size(); i++) {
					if(!contain) {
					String str =  potions_choose.get(player).get(i);
					contain = str == PotionUtils.getIdPotionByItem(currentWithoutEnchant);
					}
				}
				
				if(!contain) {
					List<String> order = potions_choose.get(player);
					if(order.size() < 2) {
						String potion = PotionUtils.getIdPotionByItem(currentWithoutEnchant) + ":" + current.getAmount();
					order.add(potion);
					
					potions_choose.replace(player, order);
					createGui(player);
					}
				}
				InventoryGui.updateInventory(player);
				
			}else if(current.getType().equals(Material.BARRIER)) {
				potions_choose.replace(player, new ArrayList<String>());
				createGui(player);
				InventoryGui.updateInventory(player);
			}
			event.setCancelled(true);
		}
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
