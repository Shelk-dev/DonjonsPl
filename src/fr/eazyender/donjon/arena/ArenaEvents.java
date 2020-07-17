package fr.eazyender.donjon.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.donjon.LootUtils;
import fr.eazyender.donjon.files.PlayerArena;
import fr.eazyender.donjon.files.PlayerLevelStats;
import fr.eazyender.donjon.gui.ArenaGui;
import fr.eazyender.donjon.gui.DonjonGui;
import fr.eazyender.donjon.gui.PotionGui;
import fr.eazyender.donjon.gui.SpellGui;
import fr.eazyender.donjon.gui.WeaponGui;
import fr.eazyender.donjon.potion.PotionUtils;
import fr.eazyender.donjon.spells.SpellUtils;

public class ArenaEvents implements Listener {
	
	public static List<Player> currentlyPlayer = new ArrayList<Player>();
	
	public static Map<Player, Integer> kills = new HashMap<Player, Integer>();
	public static Map<Player, Long> score = new HashMap<Player, Long>();
	
	public static void joinArena(Player player) {
		
		for (int i = 0; i < currentlyPlayer.size(); i++) {
			Player current = currentlyPlayer.get(i);
			current.sendMessage("§7[§6§lArena§r§7]§f : §7" + player.getName() + "§f a rejoins l'arène ! ");
		}
		
		currentlyPlayer.add(player);
		if(!kills.containsKey(player))kills.put(player, 0);
		if(!score.containsKey(player))score.put(player, (long) 0);
		
		player.setInvulnerable(true);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				player.setInvulnerable(false);
			}
		}.runTaskLater(DonjonMain.instance, 35);
		
		Location loc = ArenaUtils.getRandomSpawnLocation();
		loc.setWorld(player.getWorld());
		
		if(WeaponGui.weapon_choose.containsKey(player)){
			player.getInventory().setItem(0, LootUtils.getWeaponById(WeaponGui.weapon_choose.get(player)));
			}else {
				
			}
			
			if(SpellGui.spells_choose.containsKey(player)) {
			 for (int i = 0; i < SpellGui.spells_choose.get(player).size(); i++) {
				if(SpellGui.spells_choose.get(player).get(i) != 0) {
					player.getInventory().setItem(i+1, SpellUtils.getItemSpellById(SpellGui.spells_choose.get(player).get(i)));
				}
			}
			}
			
			if(PotionGui.potions_choose.containsKey(player)) {
				 for (int i = 0; i < PotionGui.potions_choose.get(player).size(); i++) {
					if(PotionGui.potions_choose.get(player).get(i) != "0:0") {
						ItemStack potion = PotionUtils.getItemPotionById(PotionGui.potions_choose.get(player).get(i));
						potion.setAmount(Integer.parseInt(PotionGui.potions_choose.get(player).get(i).split("\\:")[1]));
						player.getInventory().setItem(i+4, potion);
					}
				}
				}
		
		player.teleport(loc);
		
		
		
	}
	
	@EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
		
		if(e.getEntity().getWorld().getName().contains("lobby")) {	
			if(e.getEntity() instanceof Player) {
				Player player = (Player)e.getEntity();
				if(currentlyPlayer.contains(player)) {
					
					if(player.getLastDamageCause().getCause() == DamageCause.ENTITY_ATTACK) {				
						Player killer = player.getKiller();
						killer.sendMessage("§7[§6§lArena§r§7]§f : " + "Vous avez tué §7" + player.getName());
						killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						PlayerArena.getPlayerArena().setKills(killer, PlayerArena.getPlayerArena().getKills(killer)+1);	
						player.sendMessage("§7[§6§lArena§r§7]§f : " + "Vous avez été tué par §7" + killer.getName());
						kills.replace(killer, kills.get(killer)+1);
						
						for (int i = 0; i < currentlyPlayer.size(); i++) {
							Player current = currentlyPlayer.get(i);
							if(current != killer && current != player) {
								current.sendMessage("§7[§6§lArena§r§7]§f : §7" + killer.getName() + "§f a tué §7 " + player.getName());
							}
						}
						
					}else {
						player.sendMessage("§7[§6§lArena§r§7]§f : " + "Vous avez été tué par §7" + player.getLastDamageCause().getCause());
					}
					
					PlayerArena.getPlayerArena().setDeaths(player, PlayerArena.getPlayerArena().getDeaths(player)+1);
					endArena(player, false);
					
				}
			}
		}
		
	}
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		
		if(player.getWorld().getName().contains("lobby")) {
			Location portal = new Location(player.getWorld(), -16.50, 149.00, 165.5);
			Location portal2 = new Location(player.getWorld(), -16.50, 149.00, 263.5);
			
			if(player.getLocation().distance(portal) < 3 || player.getLocation().distance(portal2) < 3)
			if(currentlyPlayer.contains(player)) {
				ArenaGui.stopArena(player);
			}
		}
		
	}
	
	@EventHandler
	  public void onPlayerQuit(PlayerQuitEvent e) { 
		endArena(e.getPlayer(), true);
	}
	
	public static void endArena(Player player, boolean disconnect) {
		
		currentlyPlayer.remove(player);
		player.teleport(player.getWorld().getSpawnLocation());
		
		if(!disconnect) {
			
			long newScore =  kills.get(player) * 10;
			score.replace(player, newScore);
			
			if(score.get(player) > 0)
				player.sendMessage("§7[§6§lArena§r§7]§f : " + "Vous avez gagné §7" + score.get(player) + " §fxp en ayant fait §7"
					+ kills.get(player) + " §fkills");
			else
				player.sendMessage("§7[§6§lArena§r§7]§f : " + "Vous n'avez rien gagné ):");
			
			PlayerLevelStats.getPlayerLevelStats().setXpRank(player, PlayerLevelStats.getPlayerLevelStats().getXpRank(player)+score.get(player));
			ArenaUtils.actualizeLevel(player);
			
		}
		
		kills.remove(player);
		score.remove(player);
		
		for (int i = 0; i < 9; i++) {
			player.getInventory().clear(i);
		}
		
	}

}
