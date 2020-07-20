package fr.eazyender.donjon.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import fr.eazyender.donjon.files.PlayerGroupSave;

public class PlayerGroup {
	
	Player host;
	List<UUID> group = new ArrayList<UUID>();
	
	public PlayerGroup(Player host) {
		
	}
	
	public PlayerGroup(Player host,List<UUID> group) {
		
	}

	public Player getHost() {
		return host;
	}

	public void setHost(Player host) {
		this.host = host;
	}

	public List<UUID> getGroup() {
		return group;
	}

	public void setGroup(List<UUID> group) {
		this.group = group;
	}
	
	public static boolean aGroupContainPlayer(UUID id) {
		for (int i = 0; i < PlayerGroupSave.getPlayerGroup().getAllGroup().size(); i++) {
			if(PlayerGroupSave.getPlayerGroup().getAllGroup().get(i).getGroup().contains(id)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static PlayerGroup getGroupOfAPlayer(Player player) {
		for (int i = 0; i < PlayerGroupSave.getPlayerGroup().getAllGroup().size(); i++) {
			if(PlayerGroupSave.getPlayerGroup().getAllGroup().get(i).getGroup().contains(player.getUniqueId())) {
				return PlayerGroupSave.getPlayerGroup().getAllGroup().get(i);
			}
		}
		return null;
	}
	
	public boolean containPlayer(UUID id) {
		return group.contains(id);
	}
	
	

}
