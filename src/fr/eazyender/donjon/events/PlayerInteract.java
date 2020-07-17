package fr.eazyender.donjon.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.eazyender.donjon.arena.ArenaEvents;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent event) {
		if(event.getEntity() instanceof Player) {
		Player p = (Player) event.getEntity();
		if(p.getWorld().getName().contains("lobby")) {
			if(!ArenaEvents.currentlyPlayer.contains(p)) {
				event.setCancelled(true);
			}
		}
		}
	}

}
