package fr.eazyender.donjon.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;

public class SpellWaterWave extends ISpell{
	
	static int basicCooldown = 5 * 1000;
	static int basicCost = 60;
	
	public SpellWaterWave(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
	   if(ManaEvents.canUseSpell(player, basicCost)) {
       if (super.launch(player, SpellWindSlash.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 4, 1);
    	   launchSpell(player,player.getLocation(), player.getTargetBlock(null, 40).getLocation());
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
	    
	    Vector pV = v2.clone().subtract(v).normalize().multiply(-2);
	    player.setVelocity(pV);
	    
	    vector.setY(0);
	    double length = 0.0D;
	    for (int i = 0; length < distance;) {
	    	int z = i;
	    	new BukkitRunnable() {
				@Override
				public void run() {
					Vector playerV = v;
					final int w = z;
					Vector v1 = target.toVector();
					for (int j = 0; j < w; j++) {
						v1.add(vector);
					}
					boolean collide = false;
					Location l = new Location(target.getWorld(),v1.getX(), v1.getBlockY(), v1.getBlockZ());
					for (int j = 0; j < target.getWorld().getEntities().size(); j++) {
						if(!(target.getWorld().getEntities().get(j).equals(player))  && target.getWorld().getEntities().get(j) instanceof LivingEntity) {
							if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 3) {
								collide = true;
							}
						}
					}
					target.getWorld().playSound(v1.toLocation(target.getWorld()), Sound.ENTITY_PLAYER_ATTACK_WEAK, 1, 1);
					if(!collide) {
		
							Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(132,165,255), 3F);
							for (double j = 0; j < 7; j=j+1) {
								target.getWorld().spawnParticle(Particle.REDSTONE, v1.getX(), v1.getY() + j, v1.getZ(), 10, 2+Math.random(), 0D, 2+Math.random(), dustOptions);
							}
						
					  
					}else {
						
						  target.getWorld().spawnParticle(Particle.WATER_BUBBLE, v1.getX(), v1.getY(), v1.getZ() , 5, 2D, 2D, 2D);
						  
						  for (int j = 0; j < target.getWorld().getEntities().size(); j++) {
								if(!(target.getWorld().getEntities().get(j).equals(player)) && target.getWorld().getEntities().get(j) instanceof LivingEntity) {
									if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 3) {
										LivingEntity entity = (LivingEntity)player.getWorld().getEntities().get(j);
										
										double distance = player.getLocation().distance(entity.getLocation());
									    Vector v = playerV;
									    Vector v2 = entity.getLocation().toVector();
									    Vector vector = v2.clone().subtract(v).normalize().multiply(2.5);
									    
									    entity.teleport(entity.getLocation().add(0, 0.25, 0));
									    entity.setVelocity(vector);
									}
								}
							}
						  
						
					}
					  			
				}
			}.runTaskLater(DonjonMain.instance, i*2);   	
	        
	    	i++;
	        length += 0.25D;
	    }
	    
	  }

}
