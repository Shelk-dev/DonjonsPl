package fr.eazyender.donjon.donjon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eazyender.donjon.files.PlayerGroupSave;
import fr.eazyender.donjon.utils.PlayerGroup;
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
	public static Map<PlayerGroup, Integer> maxPositionPlayer = new HashMap<PlayerGroup, Integer>();
	public static Map<Player, Integer> entry = new HashMap<Player, Integer>();
	
	public static Map<Player, BossBar> current_room = new HashMap<Player, BossBar>();
	public static Map<Player, BossBar> current_entity = new HashMap<Player, BossBar>();
	public static Map<PlayerGroup, List<ItemStack>> drops_ressource = new HashMap<PlayerGroup,  List<ItemStack>>();
	public static Map<PlayerGroup, List<ItemStack>> drops_weapons = new HashMap<PlayerGroup,  List<ItemStack>>();
	public static Map<PlayerGroup, List<ItemStack>> drops_spells = new HashMap<PlayerGroup,  List<ItemStack>>();
	public static Map<PlayerGroup, Long> score = new HashMap<PlayerGroup, Long>();
	public static Map<PlayerGroup, Long> timer = new HashMap<PlayerGroup, Long>();
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		PlayerGroup group = PlayerGroupSave.getPlayerGroup().getGroup(player);
		if(PlayerGroup.aGroupContainPlayer(player.getUniqueId()))
		{
			group = PlayerGroup.getGroupOfAPlayer(player);
		}
		if(DonjonGenerator.donjons.containsKey(group)) {
			
			IDonjon donjon = DonjonGenerator.donjons.get(group);
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
							
							if(maxPositionPlayer.get(group) < positionPlayer.get(player)+1) {
								maxPositionPlayer.replace(group, positionPlayer.get(player)+1);
								if(donjon.getDonjon().get(positionPlayer.get(player)+1).getEntity_loc() != null) {
								donjon.getDonjon().get(positionPlayer.get(player)+1).setNumberOfMobs(donjon.getDonjon().get(positionPlayer.get(player)+1).getEntity_loc().size());
								RoomUtils.genEntity(donjon.getDonjon().get(positionPlayer.get(player)+1), player.getWorld());
								
								BossBar bar = current_entity.get(player);
								bar.setColor(BarColor.RED);
						        bar.setProgress(1);
						        bar.setTitle("§fMonstres restants : " + donjon.getDonjon().get(positionPlayer.get(player)+1).getNumberOfMobs() + "/" +  donjon.getDonjon().get(positionPlayer.get(player)+1).getNumberOfMobs());
								}
							}else if(donjon.getDonjon().get(positionPlayer.get(player)+1).getNumberOfMobs() > 0){

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
			Player player = (Player)entity;
			PlayerGroup group = PlayerGroupSave.getPlayerGroup().getGroup(player);
			if(PlayerGroup.aGroupContainPlayer(player.getUniqueId()))
			{
				group = PlayerGroup.getGroupOfAPlayer(player);
			}
			if(DonjonGenerator.donjons.containsKey(group)) {
				
				IDonjon donjon = DonjonGenerator.donjons.get(group);
				donjon.getDonjon().get(positionPlayer.get(player)).setNumberOfMobs(donjon.getDonjon().get(positionPlayer.get(player)).getNumberOfMobs()-1);
					
					
					if(!drops_ressource.containsKey(group)) {
						drops_ressource.put(group, new ArrayList<ItemStack>());
					}
					if(!drops_weapons.containsKey(group)) {
						drops_weapons.put(group, new ArrayList<ItemStack>());
					}
					List<ItemStack> actualdrops = drops_ressource.get(group);
					List<ItemStack> mobdrops = LootUtils.getLootOfMob(entity.getCustomName(), donjon.getDifficulty());
					for (int j = 0; j < mobdrops.size(); j++) {
						List<Player> players = group.getPlayers();
						for (int i = 0; i < players.size(); i++) {
							players.get(i).sendMessage("§8[§4Donjon/§cSecondaire§8] : §f" + "Vous avez loot : " + mobdrops.get(j).getItemMeta().getDisplayName());
						}
					}
					actualdrops.addAll(mobdrops);
					drops_ressource.replace(group, actualdrops);
					
					List<ItemStack> actualdrops1 = drops_weapons.get(group);
					List<ItemStack> mobdrops1 = LootUtils.getWeaponLootOfMob(entity.getCustomName(), donjon.getDifficulty());
					for (int j = 0; j < mobdrops1.size(); j++) {
						List<Player> players = group.getPlayers();
						for (int i = 0; i < players.size(); i++) {
							players.get(i).sendMessage("§8[§4Donjon/§cSecondaire§8] : §f" + "Vous avez loot : " + mobdrops1.get(j).getItemMeta().getDisplayName());
						}
					}
					actualdrops1.addAll(mobdrops1);
					drops_weapons.replace(group, actualdrops1);

				for (int i = 0; i < group.getPlayersInARoom(positionPlayer.get(player)).size(); i++) {
					List<Player> players = group.getPlayersInARoom(positionPlayer.get(player));
					BossBar bar = current_entity.get(players.get(i));
					if (((double) donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs() / (double) donjon.getDonjon().get(positionPlayer.get(players.get(i))).getEntity_loc().size()) > 0.5) {
						bar.setColor(BarColor.RED);
					} else {
						bar.setColor(BarColor.YELLOW);
					}
					bar.setProgress((double) donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs() / (double) donjon.getDonjon().get(positionPlayer.get(players.get(i))).getEntity_loc().size());
					bar.setTitle("§fMonstres restants : " + donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs() + "/" + donjon.getDonjon().get(positionPlayer.get(players.get(i))).getEntity_loc().size());

					if (donjon.getDonjon().get(positionPlayer.get(players.get(i))).getNumberOfMobs() <= 0) {
						bar.setTitle("Pas de monstres !");
						bar.setColor(BarColor.GREEN);
						bar.setProgress(1.0);
						players.get(i).playSound(players.get(i).getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
					} else {
						players.get(i).playSound(players.get(i).getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
					}
				}
			        
			        if(donjon.getDonjon().get(positionPlayer.get(player)).getNumberOfMobs() <= 0 && maxPositionPlayer.get(group) == donjon.getSize()-1) {
			        	
			        	endDonjon(group, true);
			        	
			        }

			}
			
		}else if(entity instanceof Player && entity.getWorld().getName().contains("donjon")) {

			Player player = (Player)entity;
			PlayerGroup group = PlayerGroupSave.getPlayerGroup().getGroup(player);
			if(PlayerGroup.aGroupContainPlayer(player.getUniqueId()))
			{
				group = PlayerGroup.getGroupOfAPlayer(player);
			}
			
			if(DonjonGenerator.donjons.containsKey(group)) {
			IDonjon donjon = DonjonGenerator.donjons.get(group);
			if(donjon.getDifficulty() >= 3) {
				endDonjon(group, false);
			}else{

			}
			}
			
		}
	}
	
	@EventHandler
	  public void onPlayerQuit(PlayerQuitEvent e) {

		Player player = e.getPlayer();
		PlayerGroup group = PlayerGroupSave.getPlayerGroup().getGroup(player);
		if(PlayerGroup.aGroupContainPlayer(player.getUniqueId()))
		{
			group = PlayerGroup.getGroupOfAPlayer(player);
		}
		if(DonjonGenerator.donjons.containsKey(group))
		endDonjon(group, false);
		
	}
	
	private static void endDonjon(PlayerGroup group, boolean isEnd) {

		List<Player> players = group.getPlayers();
		for (Player player : players) {
			current_entity.get(player).removeAll();;
			current_entity.remove(player);
			current_room.get(player).removeAll();
			current_room.remove(player);
			travelPlayer.remove(player);
			positionPlayer.remove(player);
			entry.remove(player);
		}

		maxPositionPlayer.remove(group);
		
		
		if(isEnd) {
			for (Player player : players) {
		LootUtils.addItemsToRessources(player, drops_ressource.get(player));
		List<Integer> drops_weapon_final = new ArrayList<Integer>();
		for (int i = 0; i < drops_weapons.get(player).size(); i++) {
			drops_weapon_final.add(LootUtils.getIDWeaponByItem(drops_weapons.get(player).get(i)));
		}
		PlayerEquipment.getPlayerEquipment().getWeapons(player).addAll(drops_weapon_final);

		}

			int mobs = 0;
			for (int i = 0; i < DonjonGenerator.donjons.get(group).getDonjon().size(); i++) {
				if (DonjonGenerator.donjons.get(group).getDonjon().get(i).getEntity_loc() != null) {
					for (int j = 0; j < DonjonGenerator.donjons.get(group).getDonjon().get(i).getEntity_loc().size(); j++) {
						mobs++;
					}
				}
			}
		
		int difficulty = DonjonGenerator.donjons.get(group).getDifficulty();
		Long time = timer.get(group);
		
		int score = 0;
		int endtime = 60;
		if(Math.log(time/endtime)*15 > 0) {
		score = (int) ((mobs - (Math.log(time/endtime)*15)) / group.getPlayers().size());
		}else {
			score = (int) ((mobs) / group.getPlayers().size());
		}

			for (Player player : group.getPlayers()) {

				if (score < 0) {
					player.sendMessage("§8[§4Donjon§8] : §f" + "Vous avez mis trop de temps, vous n'avez pas gagné d'expérience.");
					score = 0;
				} else {
					player.sendMessage("§8[§4Donjon§8] : §f" + "Vous avez gagné " + score + "xp.");
				}


				PlayerLevelStats.getPlayerLevelStats().setXpDonjon(player, PlayerLevelStats.getPlayerLevelStats().getXpDonjon(player) + score);
				LevelUtils.updateXp(player);
			}
		}

		for (Player player : group.getPlayers()) {
			for (int i = 0; i < 9; i++) {
				player.getInventory().clear(i);
			}
		}
		
		drops_weapons.remove(group);
		drops_ressource.remove(group);
		timer.remove(group);
		DonjonGenerator.delDonjon(group);
		
		
	}


}
