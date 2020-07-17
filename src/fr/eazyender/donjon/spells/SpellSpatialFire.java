package fr.eazyender.donjon.spells;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.eazyender.donjon.DonjonMain;

public class SpellSpatialFire extends ISpell{
	
	static int basicCooldown = 2 * 1000;
	static int basicCost = 100;
	int timer = 0, maxTimer = 4*5;
	int timer2 = 0, maxTimer2 = 4*5;
	
	public SpellSpatialFire(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
	   if(ManaEvents.canUseSpell(player, basicCost)) {
       if (super.launch(player, SpellSpatialFire.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 10, 1);
    	   launchSpell(player);
    	   ManaEvents.useSpell(player, basicCost);
       } else {
    	   
       }
	   }
   }
   
   private void launchSpell(Player player) {
	   
	   	Location target = player.getTargetBlock(null, 40).getLocation();
					  Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(67,0,0), 5F);
					  
					  new BukkitRunnable() {
						  @Override
							public void run() {
							  
							  if(timer < maxTimer) {
							  
								if(timer <= maxTimer / 3) {
									
									for (int i = 0; i < 360; i=i+10) {
										  player.getWorld().spawnParticle(Particle.REDSTONE, target.getX() + Math.cos(i) * 3, target.getY() + 1, target.getZ() + Math.sin(i)  * 3, 0, 0D, 0.5D, 0D, dustOptions);
									}
									
								}else if(timer <= maxTimer / 3 * 2) {
									for (int i = 0; i < 360; i=i+10) {
										  player.getWorld().spawnParticle(Particle.REDSTONE, target.getX() + Math.cos(i) * 3, target.getY() + 1, target.getZ() + Math.sin(i)  * 3, 0, 0D, 0.5D, 0D, dustOptions);
										  player.getWorld().spawnParticle(Particle.REDSTONE, target.getX() + Math.cos(i) * 6, target.getY() + 1, target.getZ() + Math.sin(i)  * 6, 0, 0D, 0.5D, 0D, dustOptions);
									}
								}else{
									for (int i = 0; i < 360; i=i+10) {
										  player.getWorld().spawnParticle(Particle.REDSTONE, target.getX() + Math.cos(i) * 3, target.getY() + 1, target.getZ() + Math.sin(i)  * 3 , 0, 0D, 0.5D, 0D, dustOptions);
										  player.getWorld().spawnParticle(Particle.REDSTONE, target.getX() + Math.cos(i) * 6, target.getY() + 1, target.getZ() + Math.sin(i)  * 6 , 0, 0D, 0.5D, 0D, dustOptions);
										  player.getWorld().spawnParticle(Particle.REDSTONE, target.getX() + Math.cos(i) * 9, target.getY() + 1, target.getZ() + Math.sin(i)  * 9 , 0, 0D, 0.5D, 0D, dustOptions);
									}
								}
					  
							  timer++;
					  
							  }else {
								  
								 
								  if(timer2 < maxTimer2) {
									  for (int i = 0; i < 360; i=i+10) {
										  player.getWorld().spawnParticle(Particle.REDSTONE, target.getX() + Math.cos(i) * 3, target.getY() + 8, target.getZ() + Math.sin(i)  * 3 , 25, 0D, 5D, 0D, dustOptions);
										  player.getWorld().spawnParticle(Particle.SMOKE_LARGE, target.getX() + Math.cos(i) * 6, target.getY() + 1, target.getZ() + Math.sin(i)  * 6 , 0, 0D, 0.5D, 0D);
										  player.getWorld().spawnParticle(Particle.SMOKE_LARGE, target.getX() + Math.cos(i) * 9, target.getY() + 1, target.getZ() + Math.sin(i)  * 9 , 0, 0D, 0.5D, 0D);
										  
											  player.getWorld().spawnParticle(Particle.SMOKE_LARGE, target.getX() + Math.cos(i) * 4, target.getY() + 5, target.getZ() + Math.sin(i)  * 4 , 0, 0D, 0D, 0D);
											  player.getWorld().spawnParticle(Particle.SMOKE_LARGE, target.getX() + Math.cos(i) * 4, target.getY() + 10, target.getZ() + Math.sin(i)  * 4 , 0, 0D, 0D, 0D);
											  player.getWorld().spawnParticle(Particle.SMOKE_LARGE, target.getX() + Math.cos(i) * 4, target.getY() + 15, target.getZ() + Math.sin(i)  * 4 , 0, 0D, 0D, 0D);
									  }
									  
									  player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CAMPFIRE_CRACKLE, 10, 1);
									  
									  for (int j = 0; j < target.getWorld().getEntities().size(); j++) {
											if(!(target.getWorld().getEntities().get(j).equals(player))  && target.getWorld().getEntities().get(j) instanceof LivingEntity) {
												if(target.distance(target.getWorld().getEntities().get(j).getLocation()) < 3) {
													LivingEntity entity = (LivingEntity) target.getWorld().getEntities().get(j);
													entity.damage(5, player);
												}
											}
										}
									  
								  timer2++;
								  }else {
									  this.cancel();
								  }
							  }
					  
						  }
					  }.runTaskTimer(DonjonMain.instance, 0, 5);
	    
	  }

}


