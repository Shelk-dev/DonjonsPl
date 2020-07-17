package fr.eazyender.donjon.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;
import net.minecraft.server.v1_14_R1.EntityPlayer;

public class SpellEarthChocWave extends ISpell{
	
	static int basicCooldown = 5 * 1000;
	static int basicCost = 65;
	private double walkspeed = 0;
	
	public SpellEarthChocWave(int cooldown) {
		super(basicCooldown);
	}

   public void launch(LivingEntity entity) {
	   if(entity instanceof Player) {
	   if(ManaEvents.canUseSpell((Player)entity, basicCost)) {
       if (super.launch(entity, SpellEarthChocWave.class)) {
                	
    	   entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 1);
    	   launchSpell(entity);
    	   ManaEvents.useSpell((Player)entity, basicCost);
       } else {
    	   
       }
	   }
	   }else {
		   if (super.launch(entity, SpellEarthChocWave.class)) {
           	
	    	   entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 10, 1);
	    	   launchSpell(entity);
	       } else {
	    	   
	       }  
	   }
   }
   
   private void launchSpell(LivingEntity entity) {
				
	   Location loc = entity.getLocation();
	   
			Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(115, 78, 72), 3.0F);
			entity.getWorld().spawnParticle(Particle.LAVA, entity.getLocation().getX(),  entity.getLocation().getY(),  entity.getLocation().getZ(), 10, 0.5, 0, 0.5);
		    
		    walkspeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
		    entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
		    
		    new BukkitRunnable() {
	    		
				@Override
				public void run() {
				
					entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(walkspeed);
					
				}
		    	
		    }.runTaskLater(DonjonMain.instance, 20*2);
		    
		    for (int i = 0; i < 9; i++) {
		    	int w = i;
		    	new BukkitRunnable() {
		    		
					@Override
					public void run() {
					
						 for (int j = 0; j < 360; j=j+5) {
							 for (int k = 1; k <= w; k++) {
								 entity.getWorld().spawnParticle(Particle.REDSTONE, loc.getX() + (Math.cos(j) * w), loc.getY() + (k*0.2), loc.getZ() + (Math.sin(j) * w) , 0, 10D, 0D, 0D, dustOptions);
								 entity.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc.getX() + (Math.cos(j + 2.5) * w), loc.getY() + (k*0.2), loc.getZ() + (Math.sin(j + 2.5) * w) , 0, 0D, 0D, 0D);
							}
							 
						  }
						 
						Vector movement = new Vector(0,0.7,0);
						 for (int j = 0; j < entity.getWorld().getEntities().size(); j++) {
							if(entity.getWorld().getEntities().get(j) instanceof LivingEntity) {
								LivingEntity entityT = (LivingEntity)entity.getWorld().getEntities().get(j);
								if(!(entityT.equals(entity))) {
								if(entityT.getLocation().distance(loc) <= (w + 0.5) && entityT.getLocation().distance(loc) >= (w - 0.5)) {
									entityT.setVelocity(movement);
									entityT.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20*2, 1, true));
									entityT.damage(3, entity);
								}
								}
							}
						}
						 
						 entity.getWorld().playSound(loc, Sound.BLOCK_CORAL_BLOCK_BREAK, 1, 1);
						
					}
			    	
			    }.runTaskLater(DonjonMain.instance, 10*i);
			}
	    
	  }

}



