package fr.eazyender.donjon.spells;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;

public class SpellPoisonousSacrifice extends ISpell{
	
	static int basicCooldown = 10 * 1000;
	int timer = 0, maxTimer = 10*2;
	double walkspeed = 0;
	static int basicCost = 10;
	
	public SpellPoisonousSacrifice(int cooldown) {
		super(basicCooldown);
	}

   public void launch(LivingEntity entity) {
       if (super.launch(entity, SpellPoisonousSacrifice.class)) {
                	
    	   entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_EXPERIENCE_BOTTLE_THROW, 4, 1);
    	   launchSpell(entity);
    	   entity.damage(basicCost);
    	   
       } else {
    	   
       }
   }
   
   private void launchSpell(LivingEntity entity) {
	   
					  walkspeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
					  
					  new BukkitRunnable() {
						  @Override
							public void run() {
							  Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(0,120,0), 1.5F);
							  if(timer < maxTimer) {
							  
							  Vector v1 = entity.getLocation().toVector();
							  v1.add(new Vector(0, 1, 0));
					 int nbrcircle = 10 + (timer / 2);
					  for (double i = 0; i <= Math.PI; i += Math.PI / nbrcircle) {
						   double radius = Math.sin(i) * (timer / 4);
						   double y = Math.cos(i) *  (timer / 4);
						   for (double a = 0; a < Math.PI * 2; a+= Math.PI / nbrcircle) {
						      double x = Math.cos(a) * radius;
						      double z = Math.sin(a) * radius;
						      v1.add(new Vector(x, y, z));
						      entity.getWorld().spawnParticle(Particle.REDSTONE, v1.getX(), v1.getY(), v1.getZ() , 0, 0D, 0D, 0D, dustOptions);
						      v1.subtract(new Vector(x, y, z));
						      
						   }
						   
						}
					    
					    for (int j = 0; j < entity.getWorld().getEntities().size(); j++) {
							if(!(entity.getWorld().getEntities().get(j).equals(entity)) && entity.getWorld().getEntities().get(j) instanceof LivingEntity) {
								if(entity.getLocation().distance(entity.getWorld().getEntities().get(j).getLocation()) < (timer / 4)) {
									LivingEntity entityT = (LivingEntity)entity.getWorld().getEntities().get(j);
								    
									entityT.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 120, 1));
								}
							}
						}
					  
					   entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
					  timer++;
					  
							  }else {
								  
  
								  
							  
								  this.cancel();
								  entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(walkspeed);
							  }
					  
						  }
					  }.runTaskTimer(DonjonMain.instance, 0, 2);
	    
	  }

}
