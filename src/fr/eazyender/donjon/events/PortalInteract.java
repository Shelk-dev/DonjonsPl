package fr.eazyender.donjon.events;

import fr.eazyender.donjon.utils.PlayerGroup;
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
				if(!PlayerGroup.aGroupContainPlayer(player.getUniqueId())) {
					DonjonGui.createGui(player);
				}else{
					player.teleport(new Location(player.getWorld(),-7.55, 127.00, -16.82, -90, 0));
					player.sendMessage("Vous n'êtes pas le chef du groupe, vous ne pouvez pas lancer un donjon");
				}
			}
			else if(player.getLocation().distance(portalToArena) < 3) {
				ArenaGui.createGui(player);
			}
		}
		
	}

}
