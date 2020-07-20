package fr.eazyender.donjon.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.eazyender.donjon.files.PlayerArena;
import fr.eazyender.donjon.files.PlayerEconomy;
import fr.eazyender.donjon.files.PlayerEquipment;
import fr.eazyender.donjon.files.PlayerGroupSave;
import fr.eazyender.donjon.files.PlayerLevelStats;

public class PlayerQuit implements Listener {
	
	@EventHandler
	  public void onPlayerQuit(PlayerQuitEvent e) { 
		
		/* Dechargement du joueur */
		PlayerLevelStats.getPlayerLevelStats().unloadPlayer(e.getPlayer());
		PlayerEquipment.getPlayerEquipment().unloadPlayer(e.getPlayer());
		PlayerEconomy.getEconomy().unloadPlayer(e.getPlayer());
		PlayerGroupSave.getPlayerGroup().unloadPlayer(e.getPlayer());
		
	}
}
