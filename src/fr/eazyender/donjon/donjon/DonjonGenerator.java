package fr.eazyender.donjon.donjon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.eazyender.donjon.files.PlayerGroupSave;
import fr.eazyender.donjon.utils.PlayerGroup;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.gui.PotionGui;
import fr.eazyender.donjon.gui.SpellGui;
import fr.eazyender.donjon.gui.WeaponGui;
import fr.eazyender.donjon.potion.PotionUtils;
import fr.eazyender.donjon.spells.SpellUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class DonjonGenerator {
	
	public static Map<PlayerGroup, IDonjon> donjons = new HashMap<PlayerGroup, IDonjon>();
	
	public static void launchDonjon(Player host, int biome, int difficulty) {

		PlayerGroup group = PlayerGroupSave.getPlayerGroup().getGroup(host);

		for (Player player : group.getPlayers()){
			player.closeInventory();
			player.sendTitle("§7§lDONJON EN CHARGEMENT !", "§fLa génération peux prendre du temps", 5, 20 * 3, 5);
		}

		int size = 5;
		switch(difficulty) {
		case 1: size = 5;
			break;
		case 2: size = 9;
			break;
		case 3: size = 13;
			break;
		case 4: size = 15;
			break;
		}
		
		IDonjon donjon = DonjonGenerator.genDonjon(host, biome, size, (short)difficulty);
		Location donjon_loc = donjon.getDonjon().get(0).getDoors().get(0);
		donjon_loc.setWorld(createNewDonjon(Bukkit.getWorld("donjon_"+biome), host));
		
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				if(donjons.containsKey(group)) {

					for (Player player : group.getPlayers()) {

						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000000, 1, true));

						if (!DonjonEvents.timer.containsKey(group)) {
							DonjonEvents.timer.put(group, (long) 0);
						}
						DonjonEvents.timer.replace(group, DonjonEvents.timer.get(player) + 1);
						String seconde = "" + DonjonEvents.timer.get(group) % 60;
						String minute = "" + (long) (DonjonEvents.timer.get(group) / 60);
						player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§f§lTemps : §f" + minute + ":" + seconde));
					}
					}else{
					for (Player player : group.getPlayers()) {
						player.removePotionEffect(PotionEffectType.NIGHT_VISION);
					}
						this.cancel();
					}

			}
		}.runTaskTimer(DonjonMain.instance, 0, 20);

		for (Player player : group.getPlayers()) {
			if (!DonjonEvents.current_room.containsKey(player)) {
				BossBar bossBar = Bukkit.createBossBar("Salle actuelle : 1 / " + donjon.getSize(), BarColor.WHITE, BarStyle.SOLID);
				bossBar.addPlayer(player);
				bossBar.setVisible(true);

				DonjonEvents.current_room.putIfAbsent(player, bossBar);
			}

			if (!DonjonEvents.current_entity.containsKey(player)) {
				BossBar bossBar = Bukkit.createBossBar("Pas de monstres !", BarColor.GREEN, BarStyle.SOLID);
				bossBar.addPlayer(player);
				bossBar.setVisible(true);

				DonjonEvents.current_entity.putIfAbsent(player, bossBar);
			}

			if (WeaponGui.weapon_choose.containsKey(player)) {
				player.getInventory().setItem(0, LootUtils.getWeaponById(WeaponGui.weapon_choose.get(player)));
			} else {

			}

			if (SpellGui.spells_choose.containsKey(player)) {
				for (int i = 0; i < SpellGui.spells_choose.get(player).size(); i++) {
					if (SpellGui.spells_choose.get(player).get(i) != 0) {
						player.getInventory().setItem(i + 1, SpellUtils.getItemSpellById(SpellGui.spells_choose.get(player).get(i)));
					}
				}
			}

			if (PotionGui.potions_choose.containsKey(player)) {
				for (int i = 0; i < PotionGui.potions_choose.get(player).size(); i++) {
					if (PotionGui.potions_choose.get(player).get(i) != "0:0") {
						ItemStack potion = PotionUtils.getItemPotionById(PotionGui.potions_choose.get(player).get(i));
						potion.setAmount(Integer.parseInt(PotionGui.potions_choose.get(player).get(i).split("\\:")[1]));
						player.getInventory().setItem(i + 4, potion);
					}
				}
			}

			player.teleport(donjon_loc);
		}
		
	}
	
	private static World createNewDonjon(World world, Player player){
	     File worldDir = world.getWorldFolder();
	     String newName = world.getName() + "_temp" + "_" + player.getName();
	     
	     try {
			FileUtils.copyDirectory(worldDir, new File(worldDir.getParent(), newName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	     File uid = new File(newName, "uid.dat");
	     uid.delete();
	     
	     WorldCreator creator = new WorldCreator(newName);
	     World newWorld = Bukkit.createWorld(creator);
	     newWorld.setGameRule(GameRule.NATURAL_REGENERATION, true);
	     newWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false);
	     return newWorld;
	}
	
	public static void delDonjon(PlayerGroup group){
		
		World world = group.getHost().getWorld();
		String name = world.getName();
		
		for (int i = 0; i < world.getPlayers().size(); i++) {
			world.getPlayers().get(i).teleport(new Location(Bukkit.getWorld("lobby"), -17.74, 127.00, -15.37));
		}
		
		for (int i = 0; i < world.getLoadedChunks().length; i++) {
			
			world.getLoadedChunks()[i].unload();
			
		}
		
		
	     Bukkit.unloadWorld(world, false);
	     world = null;
		 donjons.remove(group);
	     
	   deleteDir(new File(Bukkit.getServer().getWorldContainer().getPath()+ File.separator + name));
	}
	
	 public static void deleteDir(File file) {
	        File[] contents = file.listFiles();
	        if (contents != null) {
	            for (File f : contents) {
	                deleteDir(f);
	            }
	        }
	        file.delete();
	    }
	
	public static IDonjon genDonjon(Player player, int biome, int size, short difficulty) {
		
		List<IRoom> rooms_donjon = new ArrayList<IRoom>();
		for (int i = 0; i < size; i++) {
			rooms_donjon.add(null);
		}
	
		//SPAWN ROOM
		rooms_donjon.set(0, RoomUtils.getSimilarRooms(biome, 0).get(RandomNumber(0,RoomUtils.getNumberOfASimilarRoom(biome, 0)-1)));
		for (int i = 1; i < size; i++) {
			
			//IMPAIR
			if(i % 2 == 1) {
				//CORRIDOR
				rooms_donjon.set(i, RoomUtils.getSimilarRooms(biome, 1).get(RandomNumber(0,RoomUtils.getNumberOfASimilarRoom(biome, 1)-1)));
			}
			//NO BOSS ROOM
			else if(i < size-1) {
				//FIGHT ROOM
				int type = RandomNumber(2,5);
				while(RoomUtils.getNumberOfASimilarRoom(biome, type) <= 0) {
					type = RandomNumber(2,5);
				}
				rooms_donjon.set(i, RoomUtils.getSimilarRooms(biome, type).get(RandomNumber(0,RoomUtils.getNumberOfASimilarRoom(biome, type)-1)));
			}
			
		}
		//BOSS ROOM
		rooms_donjon.set(size-1, RoomUtils.getSimilarRooms(biome, 6).get(RandomNumber(0,RoomUtils.getNumberOfASimilarRoom(biome, 6)-1)));
		
		donjons.put(PlayerGroupSave.getPlayerGroup().getGroup(player), new IDonjon(rooms_donjon, size, biome, difficulty));
		DonjonEvents.positionPlayer.put(player, 0);
		DonjonEvents.maxPositionPlayer.put(PlayerGroupSave.getPlayerGroup().getGroup(player), 0);
		DonjonEvents.travelPlayer.put(player, true);
		return new IDonjon(rooms_donjon, size, biome, difficulty);
	}
	
	private static int RandomNumber(int Min , int Max)
    {
		if(Min == Max) {return Max;}
		Min = Min-1;
    	Random rand = new Random();
    	int randomNbr = rand.nextInt(Max - Min) + Min;
    	
    	if(randomNbr > Max){randomNbr = Max;}
    	if(randomNbr <= Min){randomNbr = Max;}
    return randomNbr;}

}