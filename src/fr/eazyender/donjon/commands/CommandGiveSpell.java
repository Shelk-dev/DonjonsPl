package fr.eazyender.donjon.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.eazyender.donjon.files.PlayerEquipment;
import fr.eazyender.donjon.spells.SpellUtils;

public class CommandGiveSpell implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("gspell")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				if(args.length <= 0) {
					player.sendMessage("/gspell <spell>");
				}
				if(args.length >= 1) {
					
					player.getInventory().addItem(SpellUtils.getItemSpellById(Integer.parseInt(args[0])));
					if(!PlayerEquipment.getPlayerEquipment().getSpells(player).contains(Integer.parseInt(args[0])))
					PlayerEquipment.getPlayerEquipment().getSpells(player).add(Integer.parseInt(args[0]));
					
				}
				
				return true;
			}
		}
		
		return false;
		
	}
	

}
