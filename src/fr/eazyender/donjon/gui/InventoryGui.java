package fr.eazyender.donjon.gui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.eazyender.donjon.donjon.LevelUtils;
import fr.eazyender.donjon.donjon.LootUtils;
import fr.eazyender.donjon.files.PlayerLevelStats;
import fr.eazyender.donjon.spells.SpellUtils;

public class InventoryGui implements Listener{
	
	public static void updateInventory(Player player){
		
		
		if(!player.getGameMode().equals(GameMode.CREATIVE)) {
		
			List<String> str_skill = new ArrayList<String>();
			str_skill.add("§fAméliorez vos statistiques afin");
			str_skill.add("§fcombattre plus confortablement les");
			str_skill.add("§fmonstres.");
			if(PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player)>0)
			str_skill.add("§eVous avez §6" + PlayerLevelStats.getPlayerLevelStats().getSkillPoints(player) + "§epoints à dépenser !");
			ItemStack skill = getCustomItemWithLore(Material.PLAYER_HEAD,"§f§lStatistiques",false,1,str_skill);
			SkullMeta sm = (SkullMeta)skill.getItemMeta();
			sm.setOwner(player.getName());
			skill.setItemMeta(sm);
			player.getInventory().setItem(34, skill);
			
			List<String> str_weapon = new ArrayList<String>();
			str_weapon.add("§fEquippez vous d'une arme robuste");
			str_weapon.add("§fqui correspond à vos besoins et à ");
			str_weapon.add("§fvotre rôle.");
			ItemStack weapon = getCustomItemWithLore(Material.STICK,"§f§lArmes",false,1,str_weapon);
			if(WeaponGui.weapon_choose.containsKey(player)) {
				if(WeaponGui.weapon_choose.get(player) > 0) {
					weapon = LootUtils.getWeaponById(WeaponGui.weapon_choose.get(player));
					ItemMeta im = weapon.getItemMeta();
					im.setLore(str_weapon);
					weapon.setItemMeta(im);
				}
			}
			player.getInventory().setItem(9, weapon);
			
			if(SpellGui.spells_choose.containsKey(player)) {
				if(!SpellGui.spells_choose.get(player).isEmpty()) {
				for (int i = 0; i < SpellGui.spells_choose.get(player).size(); i++) {
					ItemStack spell = SpellUtils.getItemSpellById(SpellGui.spells_choose.get(player).get(i));
					ItemMeta im = spell.getItemMeta();
					List<String> lore = new ArrayList<String>();
					lore = im.getLore();
					lore.add("§cVous aurez ce sort dans le donjon.");
					im.setLore(lore);
					spell.setItemMeta(im);
					if(spell != null)
					player.getInventory().setItem(10+i, spell);
				}
				if(SpellGui.spells_choose.get(player).size() < 3) {
					for (int i = 0; i < 3-SpellGui.spells_choose.get(player).size(); i++) {
						player.getInventory().clear(13-i);
					}
				}
				}else {
					player.getInventory().clear(10);
					player.getInventory().clear(11);
					player.getInventory().clear(12);
				}
			}
			
			List<String> str_leveling = new ArrayList<String>();
			str_leveling.add("§fMontez en grade et en prestige");
			str_leveling.add("§fau cours de votre ascension.");
			str_leveling.add("§fRang actuel : " + LevelUtils.getRankName(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)));
			str_leveling.add("§fNiveau actuel : " + PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player));
			str_leveling.add("§fProgression : " + generateBar((int)((double)(PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player))/(double)(LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1))*100))+(int)((double)(PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player))/(double)(LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1))*100)+"%");
			str_leveling.add("§f;" + PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player)+"/"+LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1));
			ItemStack leveling_donjon = getCustomItemWithLore(Material.FEATHER,LevelUtils.getRankName(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)),false,1,str_leveling);
			player.getInventory().setItem(16, leveling_donjon);
			
			List<String> str_leveling_PVP = new ArrayList<String>();
			str_leveling_PVP.add("§fAffrontez de réels ennemis dans une arène");
			str_leveling_PVP.add("§fsans merci afin de montrer votre puissance.");
			//str_leveling_PVP.add("§fRang actuel : " + LevelUtils.getRankName(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)));
			//str_leveling_PVP.add("§fProgression : " + generateBar((int)((double)(PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player))/(double)(LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1))*100))+(int)((double)(PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player))/(double)(LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1))*100)+"%");
			//str_leveling_PVP.add("§f;" + PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player)+"/"+LevelUtils.getXpNecessary(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)+1));
			//ItemStack leveling_donjon_PVP = getCustomItemWithLore(Material.FEATHER,LevelUtils.getRankName(PlayerLevelStats.getPlayerLevelStats().getLevelDonjon(player)),false,1,str_leveling);
			ItemStack leveling_donjon_PVP = getCustomItemWithLore(Material.STICK,"§f§lRang : Bronze",false,1,str_leveling_PVP);
			player.getInventory().setItem(17, leveling_donjon_PVP);
			
			List<String> str_potions = new ArrayList<String>();
			str_potions.add("§fVaut mieux garder des potions");
			str_potions.add("§fau cas où...");
			ItemStack potions = getCustomItemWithLore(Material.STICK,"§f§lPotions",false,1,str_potions);
			player.getInventory().setItem(18, potions);
			
			List<String> str_parchemins = new ArrayList<String>();
			str_parchemins.add("§fEquippez vous d'un parchemin permettant");
			str_parchemins.add("§fde lancer un unique sort souvent puissant");
			ItemStack parchemins = getCustomItemWithLore(Material.STICK,"§f§lParchemins",false,1,str_parchemins);
			player.getInventory().setItem(19, parchemins);
			
			List<String> str_amulettes = new ArrayList<String>();
			str_amulettes.add("§fPossédez une amulette vous donnant");
			str_amulettes.add("§fun atout dans le donjon");
			ItemStack amulettes = getCustomItemWithLore(Material.STICK,"§f§lAmulettes",false,1,str_amulettes);
			player.getInventory().setItem(26, amulettes);
			
			List<String> str_spell = new ArrayList<String>();
			str_spell.add("§fCombattre sans sorts ?");
			str_spell.add("§fFaut être fou pour s'en passer");
			ItemStack spells = getCustomItemWithLore(Material.STICK,"§f§lSorts",false,1,str_spell);
			player.getInventory().setItem(33, spells);
			
			List<String> str_rsc = new ArrayList<String>();
			str_rsc.add("§fObservez ce que vous avez loot");
			str_rsc.add("§fdans le donjon.");
			ItemStack ressources = getCustomItemWithLore(Material.STICK,"§f§lRessources",false,1,str_rsc);
			player.getInventory().setItem(35, ressources);
			
			List<String> str_friend_no = new ArrayList<String>();
			str_friend_no.add("§fVous pouvez inviter un ami");
			str_friend_no.add("§fdans cet emplacement afin qu'il");
			str_friend_no.add("§fvous rejoins pendant les donjons");
			ItemStack friend_no = getCustomItemWithLore(Material.STICK,"§f§lInexistant",false,1,str_friend_no);
			player.getInventory().setItem(27, friend_no);
			player.getInventory().setItem(28, friend_no);
			player.getInventory().setItem(29, friend_no);
			player.getInventory().setItem(30, friend_no);
		
		}
		
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack current = event.getCurrentItem();
		
		
		
		if(current != null && event.getClickedInventory().getHolder() instanceof Player && !player.getGameMode().equals(GameMode.CREATIVE)) {
			
			List<String> str_potions = new ArrayList<String>();
			str_potions.add("§fVaut mieux garder des potions");
			str_potions.add("§fau cas où...");
			ItemStack potions = getCustomItemWithLore(Material.STICK,"§f§lPotions",false,1,str_potions);
			List<String> str_rsc = new ArrayList<String>();
			str_rsc.add("§fObservez ce que vous avez loot");
			str_rsc.add("§fdans le donjon.");
			ItemStack ressources = getCustomItemWithLore(Material.STICK,"§f§lRessources",false,1,str_rsc);
			List<String> str_spell = new ArrayList<String>();
			str_spell.add("§fCombattre sans sorts ?");
			str_spell.add("§fFaut être fou pour s'en passer");
			ItemStack spells = getCustomItemWithLore(Material.STICK,"§f§lSorts",false,1,str_spell);
			List<String> str_weapon = new ArrayList<String>();
			str_weapon.add("§fEquippez vous d'une arme robuste");
			str_weapon.add("§fqui correspond à vos besoins et à ");
			str_weapon.add("§fvotre rôle.");
			ItemStack weapon = getCustomItemWithLore(Material.STICK,"§f§lArmes",false,1,str_weapon);
			if(WeaponGui.weapon_choose.containsKey(player)) {
				if(WeaponGui.weapon_choose.get(player) > 0) {
					weapon = LootUtils.getWeaponById(WeaponGui.weapon_choose.get(player));
					ItemMeta im = weapon.getItemMeta();
					im.setLore(str_weapon);
					weapon.setItemMeta(im);
				}
			}
			
			if(current.getType().equals(Material.PLAYER_HEAD)) {
				
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				PlayerSkillGui.createGui(player);
				
			}else if(current.equals(ressources)) {
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				RessourcesGui.createGui(player);
			}else if(current.equals(spells)) {
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				SpellGui.createGui(player);
			}else if(current.equals(weapon)) {
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				WeaponGui.createGui(player);
			}else if(current.equals(potions)) {
				player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 1, 1);
				PotionGui.createGui(player);
			}
			
			event.setCancelled(true);
			
		}
		
	}
	
	private static String generateBar(long percentage) {
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
