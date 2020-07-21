package fr.eazyender.donjon.commands;

import fr.eazyender.donjon.files.PlayerEconomy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMoney implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if (cmd.getName().equalsIgnoreCase("money")) {
			if (sender instanceof Player) {
				PlayerEconomy eco = PlayerEconomy.getEconomy();
				Player player = (Player) sender;
				if (args.length == 0) {
					player.sendMessage("[]" + "Vous avez " + eco.getMoney(player) + "$");

				} else {
					// Pay command
					if (args[0].equalsIgnoreCase("pay")) {

						if (args.length == 3) {
							if (Bukkit.getPlayer(args[1]) != null) {

								try {

									int amount = Integer.parseInt(args[2]);
									if (eco.getMoney(player) >= amount) {
										eco.setMoney(player, eco.getMoney(player) - amount);
										eco.setMoney(Bukkit.getPlayer(args[1]),
												eco.getMoney(Bukkit.getPlayer(args[1])) + amount);
										player.sendMessage(
												"§aVous avez donné §e" + amount + "$&a à §e" + args[1] + "§a.");
										Bukkit.getPlayer(args[1]).sendMessage(
												"§e" + player.getName() + " §a vous a donné §e" + amount + "$&a.");
									} else {
										player.sendMessage("§cVous n'avez pas assez d'argent.");
									}

								} catch (NumberFormatException exception) {
									player.sendMessage("§cMontant invalide.");
								}

							} else {
								player.sendMessage("§cJoueur non connecté ou inexistant.");
							}
							return false;
						}
						player.sendMessage("§aCommande : /money pay <joueur> <montant>");

					}
					// Give command (admin)
					// Permission : money.give

					if (args[0].equalsIgnoreCase("give")) {
						if (player.hasPermission("money.give")) {
							if (args.length == 3) {
								if (Bukkit.getPlayer(args[1]) != null) {

									try {

										int amount = Integer.parseInt(args[2]);
										eco.setMoney(Bukkit.getPlayer(args[1]),
												eco.getMoney(Bukkit.getPlayer(args[1])) + amount);
										player.sendMessage(
												"§aVous avez donné §e" + amount + "$&a à §e" + args[1] + "§a.");
										Bukkit.getPlayer(args[1]).sendMessage(
												"§e" + player.getName() + " §a vous a donné §e" + amount + "$&a.");

									} catch (NumberFormatException exception) {
										player.sendMessage("§cMontant invalide.");
									}

								} else {
									player.sendMessage("§cJoueur non connecté ou inexistant.");
								}

								return false;
							}
							player.sendMessage("§aCommande : /money give <joueur> <montant>");

						} else player.sendMessage("§cVous n'avez pas la permission de faire cette commande.");
					}
					
					// Remove command (admin)
					// Permission : money.remove
					
					if (args[0].equalsIgnoreCase("remove")) {
						if (player.hasPermission("money.remove")) {
							if (args.length == 3) {
								if (Bukkit.getPlayer(args[1]) != null) {

									try {

										int amount = Integer.parseInt(args[2]);
										eco.setMoney(Bukkit.getPlayer(args[1]),
												eco.getMoney(Bukkit.getPlayer(args[1])) - amount);
										if (eco.getMoney(Bukkit.getPlayer(args[1])) < 0) eco.setMoney(Bukkit.getPlayer(args[1]), 0);
										player.sendMessage(
												"§aVous avez retiré §e" + amount + "$&a à §e" + args[1] + "§a.");
										Bukkit.getPlayer(args[1]).sendMessage(
												"§e" + player.getName() + " §a vous a retiré §e" + amount + "$&a.");

									} catch (NumberFormatException exception) {
										player.sendMessage("§cMontant invalide.");
									}

								} else {
									player.sendMessage("§cJoueur non connecté ou inexistant.");
								}

								return false;
							}
							player.sendMessage("§aCommande : /money remove <joueur> <montant>");

						} else player.sendMessage("§cVous n'avez pas la permission de faire cette commande.");
					}
					
					

				}
				return true;
			}
		}

		return false;
	}

}
