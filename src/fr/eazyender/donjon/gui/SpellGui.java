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
import fr.eazyender.donjon.spells.SpellUtils;

public class SpellGui implements Listener{
	
	public static HashMap<Player, List<Integer>> spells_choose = new HashMap<Player, List<Integer>>();

	public static void createGui(Player player){
		
		Inventory inv = Bukkit.createInventory(null, 36 , "§f§lSpell : §8" + player.getDisplayName());
		if(!spells_choose.containsKey(player)) spells_choose.put(player, new ArrayList<Integer>());
		
		List<Integer> spells = PlayerEquipment.getPlayerEquipment().getSpells(player);
		for (int i = 0; i < spells.size(); i++) {
			if(spells_choose.get(player).isEmpty()){
				ItemStack spell = SpellUtils.getItemSpellById(spells.get(i));
				inv.setItem(i, spell);
			}else {
				if(spells_choose.get(player).contains(spells.get(i))) {
					int order = spells_choose.get(player).indexOf(spells.get(i));
					ItemStack spell = SpellUtils.getItemSpellById(spells.get(i));
					ItemMeta itemMeta = spell.getItemMeta();
					itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 200, true);itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					spell.setItemMeta(itemMeta);
					spell.setAmount(order+1);
					inv.setItem(i, spell);
				}else {
					ItemStack spell = SpellUtils.getItemSpellById(spells.get(i));
					inv.setItem(i, spell);	
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
		
		if(current != null && event.getView().getTitle().equals("§f§lSpell : §8" + player.getDisplayName())) {
			if(current.getType().equals(Material.FEATHER)) {
				
				ItemStack currentWithoutEnchant = current.clone();
				currentWithoutEnchant.setAmount(1);
				ItemMeta im = currentWithoutEnchant.getItemMeta();
				im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.removeEnchant(Enchantment.DAMAGE_ALL);
				currentWithoutEnchant.setItemMeta(im);
				
				if(!spells_choose.get(player).contains(SpellUtils.getIdSpellByItem(currentWithoutEnchant))) {
					List<Integer> order = spells_choose.get(player);
					if(order.size() < 3) {
					order.add(SpellUtils.getIdSpellByItem(currentWithoutEnchant));
					
					spells_choose.replace(player, order);
					createGui(player);
					}
				}
				InventoryGui.updateInventory(player);
				
			}else if(current.getType().equals(Material.BARRIER)) {
				spells_choose.replace(player, new ArrayList<Integer>());
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
