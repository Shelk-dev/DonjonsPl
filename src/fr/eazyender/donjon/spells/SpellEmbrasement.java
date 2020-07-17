package fr.eazyender.donjon.spells;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;

public class SpellEmbrasement extends ISpell{
	
	static int basicCooldown = 10 * 1000;
	int timer = 0, maxTimer = 10*2;
	float walkspeed = 0;
	static int basicCost = 75;
	
	public SpellEmbrasement(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
	   if(ManaEvents.canUseSpell(player, basicCost)) {
       if (super.launch(player, SpellEmbrasement.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 4, 1);
    	   launchSpell(player);
    	   ManaEvents.useSpell(player, basicCost);
    	   
       } else {
    	   
       }
	   }
   }
   
   private void launchSpell(Player player) {
	   
					  walkspeed = player.getWalkSpeed();
					  
					  new BukkitRunnable() {
						  @Override
							public void run() {
							  Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(120,0,0), 1.0F + ((float)timer/10F));
							  if(timer < maxTimer) {
							  
							  Vector v1 = player.getLocation().toVector();
							  v1.add(new Vector(0, 1, 0));
					 int nbrcircle = 10 + timer;
					  for (double i = 0; i <= Math.PI; i += Math.PI / nbrcircle) {
						   double radius = Math.sin(i) * (timer / 2);
						   double y = Math.cos(i) *  (timer / 2);
						   for (double a = 0; a < Math.PI * 2; a+= Math.PI / nbrcircle) {
						      double x = Math.cos(a) * radius;
						      double z = Math.sin(a) * radius;
						      v1.add(new Vector(x, y, z));
						      player.getWorld().spawnParticle(Particle.REDSTONE, v1.getX(), v1.getY(), v1.getZ() , 0, 0D, 0D, 0D, dustOptions);
						      v1.subtract(new Vector(x, y, z));
						      
						   }
						   
						}
					    
					    for (int j = 0; j < player.getWorld().getEntities().size(); j++) {
							if(!(player.getWorld().getEntities().get(j).equals(player)) && player.getWorld().getEntities().get(j) instanceof LivingEntity) {
								if(player.getLocation().distance(player.getWorld().getEntities().get(j).getLocation()) < (timer / 2)) {
									LivingEntity entity = (LivingEntity)player.getWorld().getEntities().get(j);
									
									double distance = player.getLocation().distance(entity.getLocation());
								    Vector v = player.getLocation().toVector();
								    Vector v2 = entity.getLocation().toVector();
								    Vector vector = v2.clone().subtract(v).normalize().multiply(2.5);
								    
								    entity.teleport(entity.getLocation().add(0, 0.25, 0));
								    entity.setVelocity(vector);
									entity.setFireTicks(45);
								}
							}
						}
					  
					  player.setWalkSpeed(0);
					  timer++;
					  
							  }else {
								  
  
								  
							  
								  this.cancel();
								  player.setWalkSpeed(walkspeed);
							  }
					  
						  }
					  }.runTaskTimer(DonjonMain.instance, 0, 2);
	    
	  }

}


