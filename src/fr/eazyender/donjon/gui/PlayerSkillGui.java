package fr.eazyender.donjon.gui;

import java.util.ArrayList;
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
import org.bukkit.inventory.meta.SkullMeta;

import fr.eazyender.donjon.donjon.LevelUtils;
import fr.eazyender.donjon.files.PlayerLevelStats;

public class PlayerSkillGui implements Listener{
	
	public static void createGui(Player player){
		
		Inventory inv = Bukkit.createInventory(null, 9 , "§f§lStatistiques : §8" + player.getDisplayName());
		
		List<String> str_skills_points = new ArrayList<String>();
		str_skills_points.add("§fPoints actuelles :" + PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player));
		str_skills_points.add("§fRang actuel : " + LevelUtils.getRankName(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)));
		str_skills_points.add("§fNiveau actuel : " + PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player));
		str_skills_points.add("§fProgression : " + generateBar((int)((double)(PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player))/(double)(LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1))*100))+(int)((double)(PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player))/(double)(LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1))*100)+"%");
		str_skills_points.add("§f;" + PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player)+"/"+LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1));
		ItemStack skill_points = getCustomItemWithLore(Material.PLAYER_HEAD,"§f§lVos stats",false,1,str_skills_points);
		SkullMeta sm = (SkullMeta)skill_points.getItemMeta();
		sm.setOwner(player.getName());
		skill_points.setItemMeta(sm);
		inv.setItem(0, skill_points);
		
		List<String> str_atq_points = new ArrayList<String>();
		str_atq_points.add("§fPermet d'améliorer vos dommages");
		str_atq_points.add("§fParfait pour ceux souhaitant devenir Guerrier");
		str_atq_points.add("§fLevel :" + PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player,1));
		ItemStack atq_points = getCustomItemWithLore(Material.STICK,"§f§lStatistiques ATQ",false,1,str_atq_points);
		inv.setItem(3, atq_points);
		
		List<String> str_def_points = new ArrayList<String>();
		str_def_points.add("§fDevenez plus résistant");
		str_def_points.add("§fCe qu'il vous faut si vous voulez être Tank ");
		str_def_points.add("§fLevel :" + PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player,2));
		ItemStack def_points = getCustomItemWithLore(Material.STICK,"§f§lStatistiques DEF",false,1,str_def_points);
		inv.setItem(4, def_points);
		
		List<String> str_magic_points = new ArrayList<String>();
		str_magic_points.add("§fManiez plus correctement la magie");
		str_magic_points.add("§fExactement ce que vous avez besoin si vous voulez être Mage");
		str_magic_points.add("§fLevel :" + PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player,3));
		ItemStack magic_points = getCustomItemWithLore(Material.STICK,"§f§lStatistiques PM",false,1,str_magic_points);
		inv.setItem(5, magic_points);
		
		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		if(current != null && event.getView().getTitle().equals("§f§lStatistiques : §8" + player.getDisplayName())) {
			
			List<String> str_atq_points = new ArrayList<String>();
			str_atq_points.add("§fPermet d'améliorer vos dommages");
			str_atq_points.add("§fParfait pour ceux souhaitant devenir Guerrier");
			str_atq_points.add("§fLevel :" + PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player,1));
			ItemStack atq_points = getCustomItemWithLore(Material.STICK,"§f§lStatistiques ATQ",false,1,str_atq_points);
			List<String> str_def_points = new ArrayList<String>();
			str_def_points.add("§fDevenez plus résistant");
			str_def_points.add("§fCe qu'il vous faut si vous voulez être Tank ");
			str_def_points.add("§fLevel :" + PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player,2));
			ItemStack def_points = getCustomItemWithLore(Material.STICK,"§f§lStatistiques DEF",false,1,str_def_points);
			List<String> str_magic_points = new ArrayList<String>();
			str_magic_points.add("§fManiez plus correctement la magie");
			str_magic_points.add("§fExactement ce que vous avez besoin si vous voulez être Mage");
			str_magic_points.add("§fLevel :" + PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player,3));
			ItemStack magic_points = getCustomItemWithLore(Material.STICK,"§f§lStatistiques PM",false,1,str_magic_points);
			
			if(current.equals(atq_points)) {
				createSkillGui(player, 1);	
			}else if(current.equals(def_points)) {
				createSkillGui(player, 2);
			}else if(current.equals(magic_points)) {
				createSkillGui(player, 3);
			}
			
			event.setCancelled(true);
		}else if(current != null && event.getView().getTitle().equals("§f§lStats["+1+"] : §8" + player.getDisplayName())) {
			//ATQ
			ItemStack cancel = getCustomItemWithLore(Material.BARRIER,"§4§lRetour",false,1,null);
			List<String> str_add = new ArrayList<String>();
			str_add.add("§fVous pouvez mettre un point afin de passer le pallier suivant");
			str_add.add("§fVous ne pourrez pas annuler !");
			str_add.add("§eVous avez §6" + PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) + "§epoints à dépenser !");
			ItemStack add = getCustomItemWithLore(Material.STICK, "§2§lAGRANDIR", false, 1, str_add);	
			
			if(current.equals(cancel)) {
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				createGui(player);
			}else if(current.equals(add)) {
				if(PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) > 0) {
				PlayerLevelStats.getPlayerLevelStats().setSkillPoints(player, PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player)-1);
				PlayerLevelStats.getPlayerLevelStats().setStatsPoints(player, 1, (int) (PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player, 1)+1));
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				createSkillGui(player, 1);
				InventoryGui.updateInventory(player);
				}	
			}
			
			event.setCancelled(true);	
		}else if(current != null && event.getView().getTitle().equals("§f§lStats["+2+"] : §8" + player.getDisplayName())) {
			//ATQ
			ItemStack cancel = getCustomItemWithLore(Material.BARRIER,"§4§lRetour",false,1,null);		
			List<String> str_add = new ArrayList<String>();
			str_add.add("§fVous pouvez mettre un point afin de passer le pallier suivant");
			str_add.add("§fVous ne pourrez pas annuler !");
			str_add.add("§eVous avez §6" + PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) + "§epoints à dépenser !");
			ItemStack add = getCustomItemWithLore(Material.STICK, "§2§lAGRANDIR", false, 1, str_add);	
			
			if(current.equals(cancel)) {
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				createGui(player);
			}else if(current.equals(add)) {
				if(PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) > 0) {
				PlayerLevelStats.getPlayerLevelStats().setSkillPoints(player, PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player)-1);
				PlayerLevelStats.getPlayerLevelStats().setStatsPoints(player, 2, (int) (PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player, 2)+1));
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				createSkillGui(player, 2);
				InventoryGui.updateInventory(player);
				}	
			}
			
			event.setCancelled(true);	
		}else if(current != null && event.getView().getTitle().equals("§f§lStats["+3+"] : §8" + player.getDisplayName())) {
			//ATQ
			ItemStack cancel = getCustomItemWithLore(Material.BARRIER,"§4§lRetour",false,1,null);
			List<String> str_add = new ArrayList<String>();
			str_add.add("§fVous pouvez mettre un point afin de passer le pallier suivant");
			str_add.add("§fVous ne pourrez pas annuler !");
			str_add.add("§eVous avez §6" + PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) + "§epoints à dépenser !");
			ItemStack add = getCustomItemWithLore(Material.STICK, "§2§lAGRANDIR", false, 1, str_add);	

			if(current.equals(cancel)) {
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				createGui(player);
			}else if(current.equals(add)) {
				if(PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) > 0) {
				PlayerLevelStats.getPlayerLevelStats().setSkillPoints(player, PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player)-1);
				PlayerLevelStats.getPlayerLevelStats().setStatsPoints(player, 3, (int) (PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player, 3)+1));
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
				createSkillGui(player, 3);
				InventoryGui.updateInventory(player);
				}	
			}
			
			event.setCancelled(true);	
		}
	}
	
	public static void createSkillGui(Player player,int id) {
	
		Inventory inv = Bukkit.createInventory(null, 54 , "§f§lStats["+id+"] : §8" + player.getDisplayName());
		
		long stats = PlayerLevelStats.getPlayerLevelStats().getAStatsPoints(player, id);
		
		for (int i = 0; i < inv.getSize()-9; i++) {
			ItemStack item = null;
			boolean isAStape = false;
			for (int j = 0; j < LevelUtils.pallierSkill.length; j++) {
				if(LevelUtils.pallierSkill[j] == i) {
					
					//Is an important stape, a award
					if(stats >= i) {
						item = LevelUtils.getAwardSkill(id, j);
						ItemMeta im = item.getItemMeta();
						List<String> lore = im.getLore();
						if(lore != null) {
						lore.clear();
						}else {lore = new ArrayList<String>();}
						lore.add("§fVous avez déja obtenu la récompense de ce pallier :");
						lore.add("§fRécompense : " + LevelUtils.getAwardSkill(id, j).getItemMeta().getDisplayName());
						im.setLore(lore);
						im.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
						im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						item.setItemMeta(im);
						isAStape = true;
					}else {
						item = LevelUtils.getAwardSkill(id, j);
						ItemMeta im = item.getItemMeta();
						if(im.getLore() != null) {
							im.getLore().clear();
						}else {
							im.setLore(new ArrayList<String>());
						}
						im.getLore().add("§fEn atteignant ce pallier vous obtiendrez : ");
						im.getLore().add("§fRécompense : " + LevelUtils.getAwardSkill(id, j).getItemMeta().getDisplayName());
						item.setItemMeta(im);
						isAStape = true;	
					}
					
				}
			}
			
			if(!isAStape) {
				if(stats >= i) {
					List<String> str = new ArrayList<String>();
					str.add("§fVous avez déja atteint ce pallier");
					item = getCustomItemWithLore(Material.GREEN_STAINED_GLASS_PANE, "§2§lPallier : " + i, false, 1, str);
				}else {
					List<String> str = new ArrayList<String>();
					str.add("§fVous n'avez pas encore atteint ce pallier");
					item = getCustomItemWithLore(Material.RED_STAINED_GLASS_PANE, "§c§lPallier : " + i, false, 1, str);
				}
			}
			
				
			inv.setItem(i, item);
		}
		
		ItemStack cancel = getCustomItemWithLore(Material.BARRIER,"§4§lRetour",false,1,null);
		inv.setItem(inv.getSize()-8, cancel);
		
		List<String> str_add = new ArrayList<String>();
		str_add.add("§fVous pouvez mettre un point afin de passer le pallier suivant");
		str_add.add("§fVous ne pourrez pas annuler !");
		str_add.add("§eVous avez §6" + PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) + "§epoints à dépenser !");
		ItemStack add = getCustomItemWithLore(Material.STICK, "§2§lAGRANDIR", false, 1, str_add);	
		inv.setItem(inv.getSize()-5, add);
		
		inv.setItem(inv.getSize()-1, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		inv.setItem(inv.getSize()-2, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		inv.setItem(inv.getSize()-3, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		inv.setItem(inv.getSize()-4, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		inv.setItem(inv.getSize()-6, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		inv.setItem(inv.getSize()-7, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		inv.setItem(inv.getSize()-9, getCustomItemWithLore(Material.BLACK_STAINED_GLASS_PANE, "§8§l", false, 1, null));
		
		player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
		player.openInventory(inv);
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
