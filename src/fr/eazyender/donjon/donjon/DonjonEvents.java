package fr.eazyender.donjon.donjon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.files.PlayerEquipment;
import fr.eazyender.donjon.files.PlayerLevelStats;

public class DonjonEvents implements Listener {
	
	public static Map<Player, Boolean> travelPlayer = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> positionPlayer = new HashMap<Player, Integer>();
	public static Map<Player, Integer> maxPositionPlayer = new HashMap<Player, Integer>();
	public static Map<Player, Integer> entry = new HashMap<Player, Integer>();
	
	public static Map<Player, BossBar> current_room = new HashMap<Player, BossBar>();
	public static Map<Player, BossBar> current_entity = new HashMap<Player, BossBar>();
	public static Map<Player, List<ItemStack>> drops_ressource = new HashMap<Player,  List<ItemStack>>();
	public static Map<Player, List<ItemStack>> drops_weapons = new HashMap<Player,  List<ItemStack>>();
	public static Map<Player, List<ItemStack>> drops_spells = new HashMap<Player,  List<ItemStack>>();
	public static Map<Player, Long> score = new HashMap<Player, Long>();
	public static Map<Player, Long> timer = new HashMap<Player, Long>();
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		if(DonjonGenerator.donjons.containsKey(player)) {
			
			IDonjon donjon = DonjonGenerator.donjons.get(player);
				for (int j = 0; j <  donjon.getDonjon().get(positionPlayer.get(player)).getDoors().size(); j++) {
					if(!entry.containsKey(player)) 
						entry.put(player, 0);
					Location loc = donjon.getDonjon().get(positionPlayer.get(player)).getDoors().get(j);
					loc.setWorld(player.getWorld());
					if(player.getLocation().distance(loc) < 2) {

						if(travelPlayer.get(player)) {
							
						if(j == 0) {
						//SORT	
							if(positionPlayer.get(player) != 0) {
								Location loc1 = donjon.getDonjon().get(positionPlayer.get(player)-1).getDoors().get(1);
								loc1.setWorld(player.getWorld());
								
							player.teleport(loc1);
							player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1, 1);
							
							positionPlayer.replace(player, positionPlayer.get(player)-1);
							
							entry.replace(player, 1);
							player.setInvulnerable(true);
							new BukkitRunnable() {
								
								@Override
								public void run() {
									player.setInvulnerable(false);
								}
							}.runTaskLater(DonjonMain.instance, 35);
							loc1 = null;
							}
						}else {
						//ENTRE
							Location loc1 = donjon.getDonjon().get(positionPlayer.get(player)+1).getDoors().get(0);
							loc1.setWorld(player.getWorld());
							player.teleport(loc1);
							player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 1, 1);
							
							if(maxPositionPlayer.get(player) < positionPlayer.get(player)+1) {
								maxPositionPlayer.replace(player, positionPlayer.get(player)+1);
								if(donjon.getDonjon().get(positionPlayer.get(player)+1).getEntity_loc() != null) {
								donjon.getDonjon().get(positionPlayer.get(player)+1).setNumberOfMobs(donjon.getDonjon().get(positionPlayer.get(player)+1).getEntity_loc().size());
								RoomUtils.genEntity(donjon.getDonjon().get(positionPlayer.get(player)+1), player.getWorld());
								
								BossBar bar = current_entity.get(player);
								bar.setColor(BarColor.RED);
						        bar.setProgress(1);
						        bar.setTitle("§fMonstres restants : " + donjon.getDonjon().get(positionPlayer.get(player)+1).getNumberOfMobs() + "/" +  donjon.getDonjon().get(positionPlayer.get(player)+1).getNumberOfMobs());
								}
							}
							
							positionPlayer.replace(player, positionPlayer.get(player)+1);
							
							entry.replace(player, 0);
							loc1 = null;
						}
						BossBar bar = current_room.get(player);
				        bar.setProgress((double)(positionPlayer.get(player)+1)/(double)donjon.getSize());
				        bar.setTitle("§fSalle actuelle : " + (positionPlayer.get(player)+1) + "/" +  donjon.getSize());
						travelPlayer.replace(player, false);
						}
					}else if(donjon.getDonjon().get(positionPlayer.get(player)).getNumberOfMobs() <= 0 && player.getLocation().distance(loc) > 2){
						if(j == entry.get(player))
						travelPlayer.replace(player, true);
	
					}
					loc = null;
				}
		}
		
	}
	
	@EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
		LivingEntity entity = e.getEntity();
		if(entity.getWorld().getName().contains("donjon") && !(entity instanceof Player)) {
			List<Player> players = entity.getWorld().getPlayers();
			if(DonjonGenerator.donjons.containsKey(players.get(0))) {
				
				IDonjon donjon = DonjonGenerator.donjons.get(players.get(0));
				donjon.getDonjon().get(positionPlayer.get(players.get(0))).setNumberOfMobs(donjon.getDonjon().get(positionPlayer.get(players.get(0))).getNumberOfMobs()-1);
				for (int i = 0; i < players.size(); i++) {
					
					
					if(!drops_ressource.containsKey(players.get(i))) {
						drops_ressource.put(players.get(i), new ArrayList<ItemStack>());
					}
					if(!drops_weapons.containsKey(players.get(i))) {
						drops_weapons.put(players.get(i), new ArrayList<ItemStack>());
					}
					List<ItemStack> actualdrops = drops_ressource.get(players.get(i));
					List<ItemStack> mobdrops = LootUtils.getLootOfMob(entity.getCustomName(), donjon.getDifficulty());
					for (int j = 0; j < mobdrops.size(); j++) {
						players.get(i).sendMessage("§8[§4Donjon/§cSecondaire§8] : §f" + "Vous avez loot : " + mobdrops.get(j).getItemMeta().getDisplayName());
					}
					actualdrops.addAll(mobdrops);
					drops_ressource.replace(players.get(i), actualdrops);
					
					List<ItemStack> actualdrops1 = drops_weapons.get(players.get(i));
					List<ItemStack> mobdrops1 = LootUtils.getWeaponLootOfMob(entity.getCustomName(), donjon.getDifficulty());
					for (int j = 0; j < mobdrops1.size(); j++) {
						players.get(i).sendMessage("§8[§4Donjon/§cSecondaire§8] : §f" + "Vous avez loot : " + mobdrops1.get(j).getItemMeta().getDisplayName());
					}
					actualdrops1.addAll(mobdrops1);
					drops_weapons.replace(players.get(i), actualdrops1);
					
					BossBar bar = current_entity.get(players.get(i));
					if(((double)donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs()/(double)donjon.getDonjon().get(positionPlayer.get(players.get(i))).getEntity_loc().size())>0.5) {
					bar.setColor(BarColor.RED);}
					else {bar.setColor(BarColor.YELLOW);}
					bar.setProgress((double)donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs()/(double)donjon.getDonjon().get(positionPlayer.get(players.get(i))).getEntity_loc().size());
			        bar.setTitle("§fMonstres restants : " + donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs() + "/" + donjon.getDonjon().get(positionPlayer.get(players.get(i))).getEntity_loc().size());
			        
			        if(donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs() <= 0) {
			        	bar.setTitle("Pas de monstres !");
			        	bar.setColor(BarColor.GREEN);
			        	bar.setProgress(1.0);
			        	players.get(i).playSound(players.get(i).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
			        }else {
			        	players.get(i).playSound(players.get(i).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			        }
			        
			        if(donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs() <= 0 && maxPositionPlayer.get(players.get(i)) == donjon.getSize()-1) {
			        	
			        	endDonjon(players.get(i), true);
			        	
			        }
				}
			}
			
		}else if(entity instanceof Player && entity.getWorld().getName().contains("donjon")) {
			
			Player player = (Player) entity;
			
			List<Player> players = entity.getWorld().getPlayers();
			
			if(DonjonGenerator.donjons.containsKey(players.get(0))) {
			IDonjon donjon = DonjonGenerator.donjons.get(players.get(0));	
			endDonjon(players.get(0), false);
			}
			
		}
	}
	
	@EventHandler
	  public void onPlayerQuit(PlayerQuitEvent e) { 
		
		if(DonjonGenerator.donjons.containsKey(e.getPlayer()))
		endDonjon(e.getPlayer(), false);
		
	}
	
	private static void endDonjon(Player player, boolean isEnd) {
		
		current_entity.get(player).removeAll();;
		current_entity.remove(player);
		current_room.get(player).removeAll();
		current_room.remove(player);
		travelPlayer.remove(player);
		positionPlayer.remove(player);
		entry.remove(player);
		maxPositionPlayer.remove(player);
		
		
		if(isEnd) {
		LootUtils.addItemsToRessources(player, drops_ressource.get(player));
		List<Integer> drops_weapon_final = new ArrayList<Integer>();
		for (int i = 0; i < drops_weapons.get(player).size(); i++) {
			drops_weapon_final.add(LootUtils.getIDWeaponByItem(drops_weapons.get(player).get(i)));
		}
		PlayerEquipment.getPlayerEquipment().getWeapons(player).addAll(drops_weapon_final);
		
		int mobs = 0;
		for (int i = 0; i < DonjonGenerator.donjons.get(player).getDonjon().size(); i++) {
			if(DonjonGenerator.donjons.get(player).getDonjon().get(i).getEntity_loc() != null) {
			for (int j = 0; j < DonjonGenerator.donjons.get(player).getDonjon().get(i).getEntity_loc().size(); j++) {
				mobs++;
			}
			}
		}
		
		
		int difficulty = DonjonGenerator.donjons.get(player).getDifficulty();
		Long time = timer.get(player);
		
		int score = 0;
		int endtime = 60;
		if(Math.log(time/endtime)*15 > 0) {
		score = (int) ((mobs - (Math.log(time/endtime)*15)) / player.getWorld().getPlayers().size());
		}else {
			score = (int) ((mobs) / player.getWorld().getPlayers().size());
		}
		
		if(score < 0) {player.sendMessage("§8[§4Donjon§8] : §f" + "Vous avez mis trop de temps, vous n'avez pas gagné d'expérience.");score = 0;}
		else {player.sendMessage("§8[§4Donjon§8] : §f" + "Vous avez gagné " + score +"xp.");}
		
		
		PlayerLevelStats.getPlayerLevelStats().setXpDonjon(player, PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player)+score);
		LevelUtils.updateXp(player);
		}
		
		for (int i = 0; i < 9; i++) {
			player.getInventory().clear(i);
		}
		
		drops_weapons.remove(player);
		drops_ressource.remove(player);
		timer.remove(player);
		DonjonGenerator.delDonjon(player);
		
		
	}


}
