package fr.eazyender.donjon.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.donjon.DonjonEvents;
import fr.eazyender.donjon.donjon.DonjonGenerator;
import fr.eazyender.donjon.donjon.IDonjon;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandDonjon implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("donjon")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				if(args.length <= 1) {
					player.sendMessage("/donjon <biome> <size>");
				}
				if(args.length >= 2) {
					
					IDonjon donjon = DonjonGenerator.genDonjon(player, Integer.parseInt(args[0]), Integer.parseInt(args[1]), (short)1);
					Location donjon_loc = donjon.getDonjon().get(0).getDoors().get(0);
					donjon_loc.setWorld(player.getWorld());
					
					new BukkitRunnable() {
						
						@Override
						public void run() {
							
							if(!DonjonEvents.timer.containsKey(player)) {
								DonjonEvents.timer.put(player, (long) 0);
							}
							DonjonEvents.timer.replace(player, DonjonEvents.timer.get(player)+1);
							String seconde = "" + DonjonEvents.timer.get(player) % 60;
							String minute = "" + (long)(DonjonEvents.timer.get(player) / 60);
							player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f§lTemps : §f" + minute + ":" + seconde));
							
							if(DonjonEvents.positionPlayer.get(player) >= (donjon.getSize()-1) && donjon.getDonjon().get(DonjonEvents.positionPlayer.get(player)).getNumberOfMobs() <= 0) {
								player.sendTitle("§2§lCLEAR !", "§fRank §lX", 5, 20*3, 5);
								player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f§lTemps passé: §f" + minute + ":" + seconde));
								this.cancel();
							}
						}
					}.runTaskTimer(DonjonMain.instance, 0, 20);
					
					if(!DonjonEvents.current_room.containsKey(player)) {
						BossBar bossBar = Bukkit.createBossBar("Salle actuelle : 1 / " + donjon.getSize(), BarColor.WHITE, BarStyle.SOLID);
						bossBar.addPlayer(player);
						bossBar.setVisible(true);
						
						DonjonEvents.current_room.putIfAbsent(player, bossBar);
						}
					
					if(!DonjonEvents.current_entity.containsKey(player)) {
					BossBar bossBar = Bukkit.createBossBar("Pas de monstres !", BarColor.GREEN, BarStyle.SOLID);
					bossBar.addPlayer(player);
					bossBar.setVisible(true);
					
					DonjonEvents.current_entity.putIfAbsent(player, bossBar);
					}
					player.teleport(donjon_loc);
					
				}
				
				return true;
			}
		}
		
		return false;
		
	}

}
