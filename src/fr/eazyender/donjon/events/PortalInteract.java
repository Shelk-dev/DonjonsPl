package fr.eazyender.donjon.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.eazyender.donjon.gui.ArenaGui;
import fr.eazyender.donjon.gui.DonjonGui;

public class PortalInteract implements Listener{
	
	@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
		
		Player player = e.getPlayer();
		
		if(player.getWorld().getName().contains("lobby")) {
			Location portalToDj = new Location(player.getWorld(), 3.43, 128.00, -16.65);
			Location portalToArena = new Location(player.getWorld(), -42.5, 127.00, 21.5);
			if(player.getLocation().distance(portalToDj) < 3) {
				DonjonGui.createGui(player);
			}
			else if(player.getLocation().distance(portalToArena) < 3) {
				ArenaGui.createGui(player);
			}
		}
		
	}

}
