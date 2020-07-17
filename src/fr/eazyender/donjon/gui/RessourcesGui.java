package fr.eazyender.donjon.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.eazyender.donjon.donjon.LootUtils;
import fr.eazyender.donjon.files.PlayerEquipment;

public class RessourcesGui implements Listener{
	
	public static void createGui(Player player){
		
		Inventory inv = Bukkit.createInventory(null, 36 , "§f§lRessources : §8" + player.getDisplayName());
		
		List<String> str_items = PlayerEquipment.getPlayerEquipment().getRessources(player);
		List<ItemStack> items = new ArrayList<ItemStack>();
		
		for (int i = 0; i < str_items.size(); i++) {
			String str = str_items.get(i);
			
			String[] split = str.split("\\:");
			String unite = split[0];
			String decimal = split[1];
			
			ItemStack item = LootUtils.getLootById(Integer.parseInt(unite));
			List<ItemStack> newI = new ArrayList<ItemStack>();
			
			if(Integer.parseInt(decimal) > 64) {
				for (int j = 0; j < (int)(Integer.parseInt(decimal) / 64); j++) {
					item.setAmount(64);
					newI.add(item.clone());
				}
				item.setAmount((int)(Integer.parseInt(decimal) % 64));
				newI.add(item.clone());
			}else {
				item.setAmount((int)(Integer.parseInt(decimal) % 64));
				newI.add(item.clone());
			}
			items.addAll(newI);
			
		}
		
		if(items != null) {
		if(!items.isEmpty()) {
		
			
		for (int i = 0; i < items.size(); i++) {
			if(i < inv.getSize()-9) {
				
				inv.setItem(i, items.get(i));
				
			}
			
			}
		}
		}
		
		for (int i = inv.getSize()-9; i < inv.getSize(); i++) {
			if(i != inv.getSize()-5) {
			inv.setItem(i, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
			}
			else {
			inv.setItem(i, getCustomItemWithLore(Material.STICK, "§2§lAGRANDIR", false, 1, null));	
			}
		}
		
		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(current != null && event.getView().getTitle().equals("§f§lRessources : §8" + player.getDisplayName())) {
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
