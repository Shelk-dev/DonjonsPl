package fr.eazyender.donjon.spells;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;

public class SpellShield extends ISpell{
	
	static int basicCooldown = 10 * 1000;
	int timer = 0, maxTimer = 4*5;
	double walkspeed = 0;
	static int basicCost = 75;
	
	public SpellShield(int cooldown) {
		super(basicCooldown);
	}

   public void launch(LivingEntity entity) {
	   if(entity instanceof Player) {
	   if(ManaEvents.canUseSpell((Player)entity, basicCost)) {
       if (super.launch(entity, SpellShield.class)) {
                	
    	   entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 1);
    	   launchSpell(entity, Color.ORANGE);
    	   ManaEvents.useSpell((Player)entity, basicCost);
    	   
       } else {
    	   
       }
	   }}else {
		       if (super.launch(entity, SpellShield.class)) {
		                	
		    	   entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 1);
		    	   launchSpell(entity, Color.ORANGE);   	   
		       } else {
		    	   
		       }  
	   }
   }
   
   private void launchSpell(LivingEntity entity, Color color) {
	   
					  Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1.0F);
					  if(!(entity instanceof Player)) {
					  walkspeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
					  }else {
						  walkspeed =  ((Player)(entity)).getWalkSpeed();
					  }
					  
					  new BukkitRunnable() {
						  @Override
							public void run() {
							  
							  if(timer < maxTimer) {
							  
							  Vector v1 = entity.getLocation().toVector();
							  v1.add(new Vector(0, 1, 0));
					  int nbrcircle = 10;
					  for (double i = 0; i <= Math.PI; i += Math.PI / nbrcircle) {
						   double radius = Math.sin(i) * 1.5;
						   double y = Math.cos(i) * 1.5;
						   for (double a = 0; a < Math.PI * 2; a+= Math.PI / nbrcircle) {
						      double x = Math.cos(a) * radius;
						      double z = Math.sin(a) * radius;
						      v1.add(new Vector(x, y, z));
						      entity.getWorld().spawnParticle(Particle.REDSTONE, v1.getX(), v1.getY(), v1.getZ() , 0, 1D, 0D, 0D, dustOptions);
						      v1.subtract(new Vector(x, y, z));
						      
						   }
						   
						}
					  
					  if(!(entity instanceof Player)) {
					  entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
					  }else {
						  ((Player)(entity)).setWalkSpeed(0);
					  }
					  entity.setInvulnerable(true);
					  timer++;
					  
							  }else {
								  
								  Vector v1 = entity.getLocation().toVector();
								  v1.add(new Vector(0, 1, 0));
								  int nbrcircle = 10;
								  for (double i = 0; i <= Math.PI; i += Math.PI / nbrcircle) {
									   double radius = Math.sin(i) * 1.5;
									   double y = Math.cos(i) * 1.5;
									   for (double a = 0; a < Math.PI * 2; a+= Math.PI / nbrcircle) {
									      double x = Math.cos(a) * radius;
									      double z = Math.sin(a) * radius;
									      v1.add(new Vector(x, y, z));
									      entity.getWorld().spawnParticle(Particle.DRIP_LAVA, v1.getX(), v1.getY(), v1.getZ() , 0, 0D, 0D, 0D);
									      v1.subtract(new Vector(x, y, z));
									      
									   }
									   
									}  
								  
								  entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 10, 1);
							  
								  this.cancel();
								  if(!(entity instanceof Player)) {
								  entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(walkspeed);
								  }else {
								  ((Player)(entity)).setWalkSpeed((float)walkspeed);  
								  }
								  entity.setInvulnerable(false);
							  }
					  
						  }
					  }.runTaskTimer(DonjonMain.instance, 0, 5);
	    
	  }

}

