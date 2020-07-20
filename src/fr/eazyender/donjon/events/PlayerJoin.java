package fr.eazyender.donjon.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.donjon.DonjonEvents;
import fr.eazyender.donjon.files.PlayerArena;
import fr.eazyender.donjon.files.PlayerEconomy;
import fr.eazyender.donjon.files.PlayerEquipment;
import fr.eazyender.donjon.files.PlayerGroupSave;
import fr.eazyender.donjon.files.PlayerLevelStats;
import fr.eazyender.donjon.gui.InventoryGui;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class PlayerJoin implements Listener {
	
	@EventHandler
	  public void onPlayerJoin(PlayerJoinEvent e) { 
		
		/* Chargement du joueur */
		PlayerEconomy.getEconomy().loadPlayer(e.getPlayer());
		PlayerLevelStats.getPlayerLevelStats().loadPlayer(e.getPlayer()); 
		PlayerEquipment.getPlayerEquipment().loadPlayer(e.getPlayer());
		PlayerArena.getPlayerArena().loadPlayer(e.getPlayer());
		PlayerGroupSave.getPlayerGroup().loadPlayer(e.getPlayer());
		
		e.getPlayer().getInventory().clear();
		InventoryGui.updateInventory(e.getPlayer());
		
	}
}
