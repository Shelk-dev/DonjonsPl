package fr.eazyender.donjon.donjon;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;
import fr.eazyender.donjon.spells.ISpell;
import fr.eazyender.donjon.spells.SpellEarthChocWave;
import fr.eazyender.donjon.spells.SpellPoisonousSacrifice;
import fr.eazyender.donjon.spells.SpellShield;

public class EntityEvents {
	
	public static void launchEntityLoop(LivingEntity entity) {
		
		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(entity.isDead()) {
					
					if(ISpell.cooldowns.containsKey(entity))ISpell.cooldowns.remove(entity);
					;this.cancel();}
				
				String customName = entity.getCustomName();
				if(customName == null)customName = "";
				switch(customName) {
				case "GRANITE_KING_SQUELETON": 
						if(!entity.getNearbyEntities(5, 5, 5).isEmpty()) {
							Skeleton skeleton = (Skeleton)entity;
							if(skeleton.getTarget() != null) {
								if(!ISpell.cooldowns.containsKey(entity)) ISpell.cooldowns.put(entity, new HashMap<Class<? extends ISpell>, Long>());
								if(RandomNumber(0,100) < 10) {
									SpellEarthChocWave spell = new SpellEarthChocWave(1000*2); 
									if(!ISpell.cooldowns.get(entity).containsKey(SpellShield.class))
									spell.launch(skeleton);
								}
								if(RandomNumber(0,100) < 4) {
									SpellShield spell = new SpellShield(1000*2); 
									if(!ISpell.cooldowns.get(entity).containsKey(SpellEarthChocWave.class))
									spell.launch(skeleton);
								}
							}
						}
					break;
				case "BUSH_ZOMBIE": 
					if(entity.getHealth() < 10) {
						if(!entity.getNearbyEntities(5, 5, 5).isEmpty()) {
							Zombie zombie = (Zombie)entity;
							if(zombie.getTarget() != null) {
								if(RandomNumber(0,100) < 50) {
									SpellPoisonousSacrifice spell = new SpellPoisonousSacrifice(1000*2); 
									spell.launch(zombie);
								}
							}
						}
					}
					break;
				case "BUSH_SQUELETON": 
					if(entity.getHealth() < 10) {
						if(!entity.getNearbyEntities(5, 5, 5).isEmpty()) {
							Skeleton squeleton = (Skeleton)entity;
							if(squeleton.getTarget() != null) {
								if(RandomNumber(0,100) < 50) {
									SpellPoisonousSacrifice spell = new SpellPoisonousSacrifice(1000*2); 
									spell.launch(squeleton);
								}
							}
						}
					}
					break;
				}
				
			}
			
		}.runTaskTimer(DonjonMain.instance, 0, 20);
		
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
