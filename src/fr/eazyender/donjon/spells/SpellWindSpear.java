package fr.eazyender.donjon.spells;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;

public class SpellWindSpear extends ISpell{
	
	static int basicCooldown = 10 * 1000;
	static int basicCost = 65;
	
	public SpellWindSpear(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
	   if(ManaEvents.canUseSpell(player, basicCost)) {
       if (super.launch(player, SpellWindSpear.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 10, 1);
    	   launchSpell(player,player.getEyeLocation(), player.getTargetBlock(null, 40).getLocation());
    	   ManaEvents.useSpell(player, basicCost);
    	   
       } else {
    	   
       }
	   }
   }
   
   private void launchSpell(Player player, Location target, Location target2) {
	    double distance = target.distance(target2);
	    Vector v = target.toVector();
	    Vector v2 = target2.toVector();
	    Vector vector = v2.clone().subtract(v).normalize().multiply(0.25D);
	    double length = 0.0D;
	    for (int i = 0; length < distance;) {
	    	int z = i;
	    	new BukkitRunnable() {
				@Override
				public void run() {
					final int w = z;
					Vector v1 = target.toVector();
					for (int j = 0; j < w; j++) {
						v1.add(vector);
					}
					boolean collide = false;
					Location l = new Location(target.getWorld(),v1.getX(), v1.getBlockY(), v1.getBlockZ());
					for (int j = 0; j < target.getWorld().getEntities().size(); j++) {
						if(!(target.getWorld().getEntities().get(j).equals(player))  && target.getWorld().getEntities().get(j) instanceof LivingEntity) {
							if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 2) {
								collide = true;
							}
						}
					}
					target.getWorld().playSound(v1.toLocation(target.getWorld()), Sound.ENTITY_PLAYER_ATTACK_WEAK, 1, 1);
					if(!collide) {
						
							target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, v1.getX(), v1.getY(), v1.getZ(), 0, 0D, 0D, 0D);
						
					  
					}else {
						
						  target.getWorld().spawnParticle(Particle.SMOKE_LARGE, v1.getX(), v1.getY(), v1.getZ() , 0, 0D, 0D, 0D);
						  
						  for (int j = 0; j < target.getWorld().getEntities().size(); j++) {
								if(!(target.getWorld().getEntities().get(j).equals(player)) && target.getWorld().getEntities().get(j) instanceof LivingEntity) {
									if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 2) {
										LivingEntity entity = (LivingEntity)target.getWorld().getEntities().get(j);
										entity.damage(8, player);
									}
								}
							}
						  
						
					}
					  			
				}
			}.runTaskLater(DonjonMain.instance, (int)(Math.log(i+3)*10));   	
	        
	    	i++;
	        length += 0.25D;
	    }
	    
	  }

}


