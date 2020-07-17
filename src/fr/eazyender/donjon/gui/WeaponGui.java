package fr.eazyender.donjon.gui;

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

import fr.eazyender.donjon.donjon.LootUtils;
import fr.eazyender.donjon.files.PlayerEquipment;

public class WeaponGui implements Listener{
	
	public static HashMap<Player, Integer> weapon_choose = new HashMap<Player, Integer>();

	public static void createGui(Player player){
		
		Inventory inv = Bukkit.createInventory(null, 36 , "§f§lArmes : §8" + player.getDisplayName());
		if(!weapon_choose.containsKey(player)) weapon_choose.put(player, 0);
		
		List<Integer> weapons = PlayerEquipment.getPlayerEquipment().getWeapons(player);
		for (int i = 0; i < weapons.size(); i++) {
				if(weapon_choose.get(player) == weapons.get(i)) {
					ItemStack weapon = LootUtils.getWeaponById(weapons.get(i));
					ItemMeta itemMeta = weapon.getItemMeta();
					itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 200, true);itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
					weapon.setItemMeta(itemMeta);
					inv.setItem(i, weapon);
				}else {
					ItemStack spell = LootUtils.getWeaponById(weapons.get(i));
					inv.setItem(i, spell);	
			}
		}
		
		
		for (int i = inv.getSize()-9; i < inv.getSize(); i++) {
			inv.setItem(i, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		}
		
		player.openInventory(inv);
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(current != null && event.getView().getTitle().equals("§f§lArmes : §8" + player.getDisplayName())) {
			if(current.getType().equals(Material.WOODEN_SWORD)
					|| current.getType().equals(Material.STONE_SWORD)
					|| current.getType().equals(Material.IRON_SWORD)
					|| current.getType().equals(Material.GOLDEN_SWORD)
					|| current.getType().equals(Material.DIAMOND_SWORD)) {
				
				ItemStack currentWithoutEnchant = current.clone();
				currentWithoutEnchant.setAmount(1);
				ItemMeta im = currentWithoutEnchant.getItemMeta();
				im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
				im.removeEnchant(Enchantment.DAMAGE_ALL);
				currentWithoutEnchant.setItemMeta(im);
				
				if(!weapon_choose.get(player).equals(LootUtils.getIDWeaponByItem(currentWithoutEnchant))) {
					weapon_choose.replace(player, LootUtils.getIDWeaponByItem(currentWithoutEnchant));
					createGui(player);
				}
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

