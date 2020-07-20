package fr.eazyender.donjon.commands;

import fr.eazyender.donjon.files.PlayerEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMoney implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (cmd.getName().equalsIgnoreCase("money")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                player.sendMessage("[]" + "Vous avez " + PlayerEconomy.getEconomy().getMoney(player) + "$");

                return true;
            }
        }

        return false;
    }

    }
