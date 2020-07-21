package fr.eazyender.donjon;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.arena.ArenaEvents;
import fr.eazyender.donjon.commands.CommandAccept;
import fr.eazyender.donjon.commands.CommandDonjon;
import fr.eazyender.donjon.commands.CommandGivePotion;
import fr.eazyender.donjon.commands.CommandGiveSpell;
import fr.eazyender.donjon.commands.CommandGiveWeapon;
import fr.eazyender.donjon.commands.CommandGroup;
import fr.eazyender.donjon.commands.CommandMoney;
import fr.eazyender.donjon.donjon.DonjonEvents;
import fr.eazyender.donjon.donjon.DonjonGenerator;
import fr.eazyender.donjon.donjon.RoomsInit;
import fr.eazyender.donjon.events.PlayerInteract;
import fr.eazyender.donjon.events.PlayerJoin;
import fr.eazyender.donjon.events.PlayerQuit;
import fr.eazyender.donjon.events.PortalInteract;
import fr.eazyender.donjon.files.PlayerArena;
import fr.eazyender.donjon.files.PlayerEconomy;
import fr.eazyender.donjon.files.PlayerEquipment;
import fr.eazyender.donjon.files.PlayerGroupSave;
import fr.eazyender.donjon.files.PlayerLevelStats;
import fr.eazyender.donjon.gui.ArenaGui;
import fr.eazyender.donjon.gui.DonjonGui;
import fr.eazyender.donjon.gui.InventoryGui;
import fr.eazyender.donjon.gui.PlayerSkillGui;
import fr.eazyender.donjon.gui.PotionGui;
import fr.eazyender.donjon.gui.RessourcesGui;
import fr.eazyender.donjon.gui.SpellGui;
import fr.eazyender.donjon.gui.WeaponGui;
import fr.eazyender.donjon.potion.ItemPotionEvent;
import fr.eazyender.donjon.spells.ItemSpellEvent;
import fr.eazyender.donjon.spells.ManaEvents;
import net.minecraft.server.v1_14_R1.ChatComponentText;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter;

public class DonjonMain extends JavaPlugin{
	
	public static DonjonMain instance;
	 private boolean tc = false;
	
	@Override
	public void onEnable() 
	{
		instance = this;
		
		RoomsInit.initRooms();
		PluginManager pm = getServer().getPluginManager();
		
		getCommand("donjon").setExecutor(new CommandDonjon());
		getCommand("group").setExecutor(new CommandGroup());
		getCommand("accept").setExecutor(new CommandAccept());
		getCommand("money").setExecutor(new CommandMoney());
		getCommand("gspell").setExecutor(new CommandGiveSpell());
		getCommand("gpotion").setExecutor(new CommandGivePotion());
		getCommand("gweapon").setExecutor(new CommandGiveWeapon());
		
		
		ManaEvents.ManaMain();
		
		/** File reading and saving */
		PlayerLevelStats file_pls = new PlayerLevelStats();
		PlayerEquipment file_eq = new PlayerEquipment();
		PlayerArena file_arena = new PlayerArena();
		PlayerEconomy file_economy = new PlayerEconomy();
		PlayerGroupSave file_groups = new PlayerGroupSave();

		/**UI*/
		pm.registerEvents(new DonjonGui()	, this);
		pm.registerEvents(new InventoryGui()	, this);
		pm.registerEvents(new PlayerSkillGui()	, this);
		pm.registerEvents(new RessourcesGui()	, this);
		pm.registerEvents(new SpellGui()	, this);
		pm.registerEvents(new WeaponGui()	, this);
		pm.registerEvents(new PotionGui()	, this);
		pm.registerEvents(new ArenaGui()	, this);
		
		pm.registerEvents(new DonjonEvents(), this);
		pm.registerEvents(new ArenaEvents(), this);
		pm.registerEvents(new PortalInteract(), this);
		pm.registerEvents(new ItemSpellEvent(), this);
		pm.registerEvents(new ItemPotionEvent(), this);
		
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new PlayerInteract(), this);
		
		Bukkit.createWorld(new WorldCreator("donjon_1"));
		
		loopTabList();
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for(Player p : Bukkit.getOnlinePlayers()) p.setFoodLevel(20);
				
			}
		}.runTaskTimer(this, 0, 20);
		
	}


	@Override
	public void onDisable() 
	{
		PlayerEquipment.getPlayerEquipment().onDisable();
		PlayerLevelStats.getPlayerLevelStats().onDisable();
		PlayerEconomy.getEconomy().onDisable();
		PlayerGroupSave.getPlayerGroup().onDisable();
	}
	
	private void loopTabList()
	{
		
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		new BukkitRunnable() {

			@Override
			public void run() {
				try {
					Field a = packet.getClass().getDeclaredField("header");
					a.setAccessible(true);
					Field b = packet.getClass().getDeclaredField("footer");
					b.setAccessible(true);
					
					
					if(Bukkit.getOnlinePlayers().size() == 0) return;
					for(Player ps : Bukkit.getOnlinePlayers()) {
					Object header1 = new ChatComponentText("§4§lDonjon \n§7Dawn Network\n§7-------------------"); 
					Object header2 = new ChatComponentText("§4§lDonjon \n§7By Eazy_Ender\n§7-------------------");
					Object footer = new ChatComponentText("§r§7-------------------\n"
							+ "§r§4§lInformations\n"
							//+ "§eArgent : §6" + PlayerEconomy.getPlayerEconomy().getMoney(ps) + "\n"
							+ "§7Donjons §flancés : §c" + DonjonGenerator.donjons.size() + "\n"
							+ "§r§7-------------------\n"
							+ "§r§7Joueurs§f en ligne : §c " + Bukkit.getServer().getOnlinePlayers().size()
							+ "\n§r§fVersion : §cAlpha 1.0");
					if(tc) {
						a.set(packet, header1);
						tc = false;
					}else {
						a.set(packet, header2);
						tc = true;
					}
					b.set(packet, footer);
					
						((CraftPlayer) ps).getHandle().playerConnection.sendPacket(packet);
					}
					
				}catch(NoSuchFieldException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
			}
					
			
		}.runTaskTimer(this, 0, 20);
		
	}
}
