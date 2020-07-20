package fr.eazyender.donjon.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.eazyender.donjon.files.PlayerGroupSave;
import fr.eazyender.donjon.utils.PlayerGroup;

public class CommandGroup implements CommandExecutor {

	public static Map<UUID, List<UUID>> requests = new HashMap<UUID, List<UUID>>();
	private static final String srv_msg = "";
	
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if (cmd.getName().equalsIgnoreCase("group")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if(args.length >= 1) {
                	
                	if(PlayerGroup.aGroupContainPlayer(player.getUniqueId())) {
                		
                		PlayerGroup group = PlayerGroup.getGroupOfAPlayer(player);
                		if(args[0] == "leave") {
    									List<UUID> newGroup = group.getGroup();
    									newGroup.remove(player.getUniqueId());
    									PlayerGroup.getGroupOfAPlayer(player).setGroup(newGroup);
    									player.sendMessage(srv_msg  + "Vous avez quitté le groupe");
    									
    					}
                    	else if(args[0] == "list") {
                    			
                    			String members = "";
                    			for (int i = 0; i < group.getGroup().size(); i++) {
    								if(i == group.getGroup().size()-1) {
    									members = members + Bukkit.getPlayer(group.getGroup().get(i)).getName();
    								}else {
    									members = members + Bukkit.getPlayer(group.getGroup().get(i)).getName() + ", ";
    								}
    							}
                    			player.sendMessage(srv_msg + "Votre groupe est composé de : (Leader)" + group.getHost().getName() + 
                    					", " + members);
                    		
                		}else {
                			player.sendMessage(srv_msg + "Usage : /group <leave/list>");
                		}
                		
                		
                	}else {
                		
                		PlayerGroup group = PlayerGroupSave.getPlayerGroup().getGroup(player);
                		
                		if(args[0] == "invit") {
                			
                			if(args.length >= 2) {
                				
                				String name = args[1];
                				Player target = Bukkit.getPlayer(name);
                				if(target != null) {
                					if(PlayerGroupSave.getPlayerGroup().getGroup(target).getGroup().size() <= 0 &&
                							!PlayerGroup.aGroupContainPlayer(target.getUniqueId())) {
                						
                						if(requests.containsKey(target.getUniqueId())) {
                							List<UUID> asks = requests.get(target.getUniqueId());
                							asks.add(player.getUniqueId());
                							requests.put(target.getUniqueId(), asks);
                						}else {
                							List<UUID> asks = new ArrayList<UUID>();
                							asks.add(player.getUniqueId());
                							requests.put(target.getUniqueId(), asks);
                						}
                						player.sendMessage(srv_msg + "Vous avez invité " + target.getName());
                						target.sendMessage(srv_msg + player.getName() + " vous a invité, pour accepter faites : /yes <player>");
                						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
										target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                						
                					}else player.sendMessage(srv_msg + "Le joueur appartient déja à un groupe");
                				}else player.sendMessage(srv_msg + "Le joueur n'est pas valide");
                				
                			}else player.sendMessage(srv_msg + "Usage : /group invit <Player>");

                			
                		}
                		else if(args[0] == "kick") {
                			if(args.length >= 2) {
                				String name = args[1];
                				boolean contain = false;
                				for (int i = 0; i < group.getGroup().size(); i++) {
									Player member = Bukkit.getPlayer(group.getGroup().get(i));
									if(member.getName() == name) {
										List<UUID> newGroup = group.getGroup();
										newGroup.remove(group.getGroup().get(i));
										PlayerGroupSave.getPlayerGroup().getGroup(player).setGroup(newGroup);
										player.sendMessage(srv_msg  + "Vous avez exclue le joueur " + name);
										
										contain = true;
									}
								}
                				if(!contain) player.sendMessage(srv_msg + "Le membre ne fait pas parti de votre groupe ou alors n'existe pas");
                			}else {
                				player.sendMessage(srv_msg + "Usage : /group kick <player>");
                			}
                		}
                		else if(args[0] == "list") {
                			
                			String members = "";
                			for (int i = 0; i < group.getGroup().size(); i++) {
								if(i == group.getGroup().size()-1) {
									members = members + Bukkit.getPlayer(group.getGroup().get(i)).getName();
								}else {
									members = members + Bukkit.getPlayer(group.getGroup().get(i)).getName() + ", ";
								}
							}
                			player.sendMessage(srv_msg + "Votre groupe est composé de : (Leader)" + group.getHost().getName() + 
                					", " + members);
                			
                		}else {
                			player.sendMessage(srv_msg + "Usage : /group <invit/kick/list>");
                		}
                		
                	}
                	
                }else {
                	if(PlayerGroup.aGroupContainPlayer(player.getUniqueId()))
                		player.sendMessage(srv_msg + "Usage : /group <leave/list>");
                	else 
                		player.sendMessage(srv_msg + "Usage : /group <invit/kick/list>");
                }

                return true;
            }
        }

        return false;
    }

}
