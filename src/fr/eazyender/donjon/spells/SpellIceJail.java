package fr.eazyender.donjon.spells;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;

public class SpellIceJail extends ISpell{
	
	List<BukkitRunnable> brun = new ArrayList<BukkitRunnable>();
	static int basicCooldown = 10 * 1000;
	static int basicCost = 50;
	double walkspeed = 0;
	int timer = 0, maxTimer = 2*5;
	boolean entitylock = false;
	
	public SpellIceJail(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
	   if(ManaEvents.canUseSpell(player, basicCost)) {
       if (super.launch(player, SpellIceJail.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL, 1, 1);
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
	    	brun.add(new BukkitRunnable() {
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
							if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 1.5) {
								collide = true;
							}
						}
					}
					if(!collide) {
					  Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(132,165,255), 1.0F);
					  
					  target.getWorld().spawnParticle(Particle.REDSTONE, v1.getX(), v1.getY(), v1.getZ() , 0, 0D, 0D, 0D, dustOptions);
				
					}else {
						
						  for (int j = 0; j < target.getWorld().getEntities().size(); j++) {
								if(!(target.getWorld().getEntities().get(j).equals(player)) && target.getWorld().getEntities().get(j) instanceof LivingEntity) {
									if(!entitylock)
									if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 1.5) {
										LivingEntity entity = (LivingEntity)target.getWorld().getEntities().get(j);
										if(!(entity instanceof Player)) {
										 walkspeed = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
										 entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
										}else {
										walkspeed =  ((Player)(entity)).getWalkSpeed();
										 ((Player)(entity)).setWalkSpeed(0);
										}
										  player.getWorld().playSound(entity.getLocation(), Sound.BLOCK_GLASS_HIT, 5, 1);
										 new BukkitRunnable() {
											  @Override
												public void run() {
												  if(timer < maxTimer) {
													  if(!(entity instanceof Player)) {
													  entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0);
													  }else {
														  ((Player)(entity)).setWalkSpeed(0);
													  }
													  drawCube(entity);
													  timer++;
												  }else {
													  player.getWorld().playSound(player.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
													  drawCubeWater(entity);
													  if(!(entity instanceof Player)) {
													  entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(walkspeed);
												  		}else {
														 ((Player)(entity)).setWalkSpeed((float) walkspeed);
													  }
													  this.cancel();
												  }
											  }
										 }.runTaskTimer(DonjonMain.instance, 0, 10);
										 entitylock = true;
									}
								}
							}
						  
						  
						  for (int j = 0; j < brun.size(); j++) {
								brun.get(j).cancel();
							}
						  
						
					}
					  			
				}
			});   	
	        
	    	i++;
	        length += 0.25D;
	    }
	    for (int j = 0; j < brun.size(); j++) {
			brun.get(j).runTaskLater(DonjonMain.instance, (int)(Math.log(j)*10));
		}
	    
	  }
   
   public static void drawCube(LivingEntity entity) {
	   Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(132,165,255), 1.25F);
	   	 for (double x = -1; x <= 1; x=x + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(x, 0, z);
				  entity.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D, dustOptions);
				  }
			}
		  }
		  for (double z = -1; z <= 1; z=z + 0.1) {
			  for (double x = -1; x <= 1; x++) {
				  if(x != 0) {
				  Location loc = entity.getLocation().add(x, 0, z);
				  entity.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D, dustOptions);
				  }
			}
		  }
		  
		  for (double x = -1; x <= 1; x=x + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(x, 2.5, z);
				  entity.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D, dustOptions);
				  }
			}
		  }
		  for (double z = -1; z <= 1; z=z + 0.1) {
			  for (double x = -1; x <= 1; x++) {
				  if(x != 0) {
				  Location loc = entity.getLocation().add(x, 2.5, z);
				  entity.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D, dustOptions);
				  }
			}
		  }
		  
		  for (double y = -1; y <= 1; y=y + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(1, y+1, z);
				  entity.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D, dustOptions);
				  }
			}
		  }
		  for (double y = 0; y <= 2.5; y=y + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(-1, y, z);
				  entity.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D, dustOptions);
				  }
			}
		  }
   }
   
   public static void drawCubeWater(LivingEntity entity) {
	   	 for (double x = -1; x <= 1; x=x + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(x, 0, z);
				  entity.getWorld().spawnParticle(Particle.DRIP_WATER, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D);
				  }
			}
		  }
		  for (double z = -1; z <= 1; z=z + 0.1) {
			  for (double x = -1; x <= 1; x++) {
				  if(x != 0) {
				  Location loc = entity.getLocation().add(x, 0, z);
				  entity.getWorld().spawnParticle(Particle.DRIP_WATER, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D);
				  }
			}
		  }
		  
		  for (double x = -1; x <= 1; x=x + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(x, 2.5, z);
				  entity.getWorld().spawnParticle(Particle.DRIP_WATER, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D);
				  }
			}
		  }
		  for (double z = -1; z <= 1; z=z + 0.1) {
			  for (double x = -1; x <= 1; x++) {
				  if(x != 0) {
				  Location loc = entity.getLocation().add(x, 2.5, z);
				  entity.getWorld().spawnParticle(Particle.DRIP_WATER, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D);
				  }
			}
		  }
		  
		  for (double y = -1; y <= 1; y=y + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(1, y+1, z);
				  entity.getWorld().spawnParticle(Particle.DRIP_WATER, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D);
				  }
			}
		  }
		  for (double y = 0; y <= 2.5; y=y + 0.1) {
			  for (double z = -1; z <= 1; z++) {
				  if(z != 0) {
				  Location loc = entity.getLocation().add(-1, y, z);
				  entity.getWorld().spawnParticle(Particle.DRIP_WATER, loc.getX(), loc.getY(), loc.getZ() , 0, 0D, 0D, 0D);
				  }
			}
		  }
   }

}


