package fr.eazyender.donjon.gui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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
import org.bukkit.inventory.meta.SkullMeta;

import fr.eazyender.donjon.arena.ArenaEvents;
import fr.eazyender.donjon.arena.ArenaUtils;
import fr.eazyender.donjon.files.PlayerArena;
import fr.eazyender.donjon.files.PlayerLevelStats;

public class ArenaGui implements Listener{
	
	public static void createGui(Player player){
		
		Inventory inv = Bukkit.createInventory(null, 9 , "§f§lArene : §8" + player.getDisplayName());
		
		List<String> str_arena = new ArrayList<String>();
		str_arena.add("§fAffrontez de §7réels joueurs");
		str_arena.add("§f afin de réaliser de gros combats à");
		str_arena.add("§f l'aide des §7sorts.");
		str_arena.add("§fJoueurs en combat : " + ArenaEvents.currentlyPlayer.size());
		str_arena.add("§fArène actuel : " + " §cRuines §2verdatre");
		ItemStack arena = getCustomItemWithLore(Material.STICK,"§f§lArene",false,1,str_arena);
		inv.setItem(4, arena);
		
		List<String> str_top = new ArrayList<String>();
		str_top.add("§fConsultez le §7top§f des joueurs");
		str_top.add("§fPVP ainsi que votre §7classement");
		ItemStack top = getCustomItemWithLore(Material.STICK,"§f§lClassement",false,1,str_top);
		inv.setItem(6, top);
		
		ItemStack cancel = getCustomItemWithLore(Material.BARRIER,"§c§lAnnuler",false,1,null);
		inv.setItem(0, cancel);

		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
	}
	
	public static void createTop(Player player) {
		
		Inventory inv = Bukkit.createInventory(null, 54 , "§f§lTop Arene");
		 DecimalFormat f = new DecimalFormat("##.00");
		
		Map<String, Long> top = PlayerArena.getTop();
		List<String> keys = new ArrayList<>(top.keySet());
		
		for (int i = 0; i < inv.getSize()-9; i++) {

			if(i < keys.size()) {
			Player ranker = Bukkit.getPlayer(UUID.fromString(keys.get(i)));
			if(ranker != null) {
			List<String> str_p = new ArrayList<String>();
			str_p.add("§fRang : " + ArenaUtils.getNameOfRank(PlayerLevelStats.getPlayerLevelStats().getLevelRank(ranker)));
			String ratio = f.format((double)PlayerArena.getPlayerArena().getKills(ranker)/(double)PlayerArena.getPlayerArena().getDeaths(ranker));
			str_p.add("§fRatio : §7§l" + ratio + "§f(§7K§f:§7" + 
					PlayerArena.getPlayerArena().getKills(ranker) + "§f;§7M§f:§7" +
					PlayerArena.getPlayerArena().getDeaths(ranker) + "§f)");
			str_p.add("§fPosition : " + ArenaUtils.getPositionOfTop(ranker, top));
			ItemStack item_p = getCustomItemWithLore(Material.PLAYER_HEAD,"§f§l" + ranker.getName(),false,1,str_p);
			SkullMeta sm = (SkullMeta)item_p.getItemMeta();
			sm.setOwner(ranker.getName());
			item_p.setItemMeta(sm);
			inv.setItem(i, item_p);
			}else {
				OfflinePlayer rankerO = Bukkit.getOfflinePlayer(UUID.fromString(keys.get(i)));
				if(rankerO != null) {
					List<String> str_p = new ArrayList<String>();
					str_p.add("§fRang : " + ArenaUtils.getNameOfRank(PlayerLevelStats.getPlayerLevelStats().getLevelRank(rankerO)));
					String ratio = f.format((double)PlayerArena.getPlayerArena().getKills(rankerO)/(double)PlayerArena.getPlayerArena().getDeaths(rankerO));
					str_p.add("§fRatio : §7§l" + ratio + "§f(§7K§f:§7" + 
							PlayerArena.getPlayerArena().getKills(rankerO) + "§f;§7M§f:§7" +
							PlayerArena.getPlayerArena().getDeaths(rankerO) + "§f)");
					str_p.add("§fPosition : " + ArenaUtils.getPositionOfTop(rankerO, top));
					ItemStack item_p = getCustomItemWithLore(Material.PLAYER_HEAD,"§f§l" + rankerO.getName(),false,1,str_p);
					SkullMeta sm = (SkullMeta)item_p.getItemMeta();
					sm.setOwner(rankerO.getName());
					item_p.setItemMeta(sm);
					inv.setItem(i, item_p);
				}
			}
			
		}
		}
		
		for (int i = inv.getSize()-9; i < inv.getSize(); i++) {
			if(i != inv.getSize()-5) {
			inv.setItem(i, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
			}
			else {
			//POSITION JOUEUR
				
				List<String> str_p = new ArrayList<String>();
				str_p.add("§fVotre Rang : " + ArenaUtils.getNameOfRank(PlayerLevelStats.getPlayerLevelStats().getLevelRank(player)));
				String ratio = f.format((double)PlayerArena.getPlayerArena().getKills(player)/(double)PlayerArena.getPlayerArena().getDeaths(player));
				str_p.add("§fVotre Ratio : §7§l" + ratio + "§f(§7K§f:§7" + 
						PlayerArena.getPlayerArena().getKills(player) + "§f;§7M§f:§7" +
						PlayerArena.getPlayerArena().getDeaths(player) + "§f)");
				str_p.add("§fVotre Position : " + ArenaUtils.getPositionOfTop(player, top));
				ItemStack item_p = getCustomItemWithLore(Material.PLAYER_HEAD,"§f§l" + player.getName(),false,1,str_p);
				SkullMeta sm = (SkullMeta)item_p.getItemMeta();
				sm.setOwner(player.getName());
				item_p.setItemMeta(sm);
				inv.setItem(i, item_p);
				
			}
		}

		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
		
	}
	
	public static void stopArena(Player player) {
		
		Inventory inv = Bukkit.createInventory(null, 9 , "§f§lPartir de l'arene");
		
		ItemStack leave = getCustomItemWithLore(Material.BARRIER,"§c§lPartir de l'arène",false,1,null);
		for (int i = 0; i < 9; i++) {
			inv.setItem(i, leave);
		}

		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(current != null) {
			
			if(event.getView().getTitle().equals("§f§lArene : §8" + player.getDisplayName())) {
			
			List<String> str_arena = new ArrayList<String>();
			str_arena.add("§fAffrontez de §7réels joueurs");
			str_arena.add("§f afin de réaliser de gros combats à");
			str_arena.add("§f l'aide des §7sorts.");
			str_arena.add("§fJoueurs en combat : " + ArenaEvents.currentlyPlayer.size());
			str_arena.add("§fArène actuel : " + " §cRuines §2verdatre");
			ItemStack arena = getCustomItemWithLore(Material.STICK,"§f§lArene",false,1,str_arena);
			List<String> str_top = new ArrayList<String>();
			str_top.add("§fConsultez le §7top§f des joueurs");
			str_top.add("§fPVP ainsi que votre §7classement");
			ItemStack top = getCustomItemWithLore(Material.STICK,"§f§lClassement",false,1,str_top);
			
			if(current.getType().equals(Material.BARRIER)) {
				
				player.closeInventory();
				player.teleport(new Location(player.getWorld(), -42.5, 127.00, 4.5, -180, 0));
				
			}else if(current.equals(arena)) {
				
				ArenaEvents.joinArena(player);
				
			}else if(current.equals(top)) {
				
				createTop(player);
				
			}
				event.setCancelled(true);
			}else if(event.getView().getTitle().equals("§f§lPartir de l'arene")) {
				
				ArenaEvents.endArena(player, false);
				
				event.setCancelled(true);
			}else if(event.getView().getTitle().equals("§f§lTop Arene")) {
			
				event.setCancelled(true);
			}
			
		}
		
	}
	
	private static String generateBar(int percentage) {
		String str = "";
		if(percentage >= 0 && percentage <= 100) {
			if(percentage >= 0 && percentage < 10) {str = "§e----------";}
			else if(percentage >= 10 && percentage < 20) {str = "§a-§e---------";}
			else if(percentage >= 20 && percentage < 30) {str = "§a--§e-------";}
			else if(percentage >= 30 && percentage < 40) {str = "§a---§e-------";}
			else if(percentage >= 40 && percentage < 50) {str = "§a----§e------";}
			else if(percentage >= 50 && percentage < 60) {str = "§a-----§e-----";}
			else if(percentage >= 60 && percentage < 70) {str = "§a------§e----";}
			else if(percentage >= 70 && percentage < 80) {str = "§a-------§e---";}
			else if(percentage >= 80 && percentage < 90) {str = "§a--------§e--";}
			else if(percentage >= 90 && percentage < 100) {str = "§a---------§e-";}
			else if(percentage == 100) {str = "§a----------";}
			return str;
		}else {
			return str;
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
