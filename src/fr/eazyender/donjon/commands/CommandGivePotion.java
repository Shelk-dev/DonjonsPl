package fr.eazyender.donjon.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.eazyender.donjon.files.PlayerEquipment;
import fr.eazyender.donjon.potion.PotionUtils;

public class CommandGivePotion implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("gpotion")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				if(args.length <= 0) {
					player.sendMessage("/gpotion <potion>");
				}
				if(args.length >= 1) {
					
					player.getInventory().addItem(PotionUtils.getItemPotionById(args[0]));
					
					List<String> potions = PlayerEquipment.getPlayerEquipment().getPotions(player);
					
					if(!potions.isEmpty()) {
						boolean contain = false;
					for (int i = 0; i < potions.size(); i++) {
						String str = potions.get(i);
						String[] parts = str.split("\\:");
						int unite = Integer.parseInt(parts[0]);
						int decimal = Integer.parseInt(parts[1]);
						
						//IS POTION?
						if(unite == Integer.parseInt(args[0])) {
							decimal++;
							//RECONSTRUCT
							String dbl = unite +":"+ decimal;
							potions.set(i,dbl);
							contain = true;
						}
						
					}
					if(!contain)potions.add(args[0]+":1");
					}else {potions.add(args[0]+":1");}
					
					PlayerEquipment.getPlayerEquipment().setPotions(player, potions);
					
				}
				
				return true;
			}
		}
		
		return false;
		
	}
	

}
