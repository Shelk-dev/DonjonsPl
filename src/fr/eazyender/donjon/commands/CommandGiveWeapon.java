package fr.eazyender.donjon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.eazyender.donjon.donjon.LootUtils;
import fr.eazyender.donjon.files.PlayerEquipment;

public class CommandGiveWeapon implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("gweapon")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				if(args.length <= 0) {
					player.sendMessage("/gweapon <weapon>");
				}
				if(args.length >= 1) {
					
					player.getInventory().addItem(LootUtils.getWeaponById(Integer.parseInt(args[0])));
					if(!PlayerEquipment.getPlayerEquipment().getWeapons(player).contains(Integer.parseInt(args[0])))
					PlayerEquipment.getPlayerEquipment().getWeapons(player).add(Integer.parseInt(args[0]));
					
				}
				
				return true;
			}
		}
		
		return false;
		
	}

}
