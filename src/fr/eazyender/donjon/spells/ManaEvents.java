package fr.eazyender.donjon.spells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.donjon.LootUtils;

public class ManaEvents {
	
	public static HashMap<Player, Integer> mana = new HashMap<Player, Integer>();
	public static int maxMana = 100;
	
	public static void ManaMain() {
		
		new BukkitRunnable() {

			@Override
			public void run() {
				maxMana = 100;
				List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
				for (int i = 0; i < players.size(); i++) {
					Player player = players.get(i);
					
					if(!mana.containsKey(player))mana.put(player, maxMana);
					if(player.getLevel() != mana.get(player))player.setLevel(mana.get(player));
					
					if(player.getInventory().getItem(0)!=null)if(player.getInventory().getItem(0).equals(LootUtils.getWeaponById(2)))maxMana = maxMana+25;
					
					if(mana.get(player) < maxMana-5) {
						mana.replace(player, mana.get(player)+5);
						}else if(mana.get(player) < maxMana){
							mana.replace(player, maxMana);
						}
					player.setExp((float)mana.get(player) / (float)maxMana);
					
				}
				
			}
			
		}.runTaskTimer(DonjonMain.instance, 0, 20);
		
	}
	
	public static void actualizeMana(Player player) {
		maxMana = 100;
		if(!mana.containsKey(player))mana.put(player, maxMana);
		if(player.getInventory().getItem(0).equals(LootUtils.getWeaponById(2)))maxMana = maxMana+25;
		player.setExp((float)mana.get(player) / (float)maxMana);
	}
	
	public static boolean canUseSpell(Player player,int cost) {
		
		boolean canUse = false;
		
		if(mana.get(player) > cost) {
			canUse = true;
		}
		
		return canUse;
	}
	
	public static void useSpell(Player player, int cost) {
		mana.replace(player, mana.get(player)-cost);
		actualizeMana(player);
	}

}
