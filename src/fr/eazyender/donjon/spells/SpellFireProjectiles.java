package fr.eazyender.donjon.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.eazyender.donjon.DonjonMain;

public class SpellFireProjectiles extends ISpell{
	
	List<BukkitRunnable> brun = new ArrayList<BukkitRunnable>();
	List<BukkitRunnable> brun2 = new ArrayList<BukkitRunnable>();
	List<BukkitRunnable> brun3 = new ArrayList<BukkitRunnable>();
	List<BukkitRunnable> brun4 = new ArrayList<BukkitRunnable>();
	static int basicCooldown = 2 * 1000;
	int timer = 0, maxTimer = 4*2;
	static int basicCost = 75;
	
	public SpellFireProjectiles(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
	   if(ManaEvents.canUseSpell(player, basicCost)) {
       if (super.launch(player, SpellFireProjectiles.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 1);
    	   launchSpell(player);
    	   ManaEvents.useSpell(player, basicCost);
    	   
       } else {
    	   
       }
	   }
   }
   
   private void launchSpell(Player player) {
	   
	   	Location target = player.getTargetBlock(null, 40).getLocation();
					  Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 0.75F);
					  
					  new BukkitRunnable() {
						  @Override
							public void run() {
							  
							  if(timer < maxTimer) {
							  
								if(timer <= maxTimer / 2) {
									
									for (int i = 0; i < 360; i=i+10) {
										  player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().getX() + Math.cos(i) * 1, player.getLocation().getY(), player.getLocation().getZ() + Math.sin(i)  * 1, 0, 0D, 0.5D, 0D, dustOptions);
									}
									
								}else{
									for (int i = 0; i < 360; i=i+10) {
										  player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().getX() + Math.cos(i) * 1, player.getLocation().getY(), player.getLocation().getZ() + Math.sin(i)  * 1 , 0, 0D, 0.5D, 0D, dustOptions);
										  player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().getX() + Math.cos(i) * 2, player.getLocation().getY(), player.getLocation().getZ() + Math.sin(i)  * 2 , 0, 0D, 0.5D, 0D, dustOptions);
									}
								}
					  
							  timer++;
					  
							  }else {
								  
								 
									  
									  
								  	launchFirebolt(player, player.getLocation().add(RandomNumber(-2,2), RandomNumber(-2,2), RandomNumber(-2,2)), player.getTargetBlock(null, 40).getLocation().add(Math.random(),Math.random(),Math.random()), Color.ORANGE,1);
								  	launchFirebolt(player, player.getLocation().add(RandomNumber(-2,2), RandomNumber(-2,2), RandomNumber(-2,2)), player.getTargetBlock(null, 40).getLocation().add(Math.random(),Math.random(),Math.random()), Color.ORANGE,2);
								  	launchFirebolt(player, player.getLocation().add(RandomNumber(-2,2), RandomNumber(-2,2), RandomNumber(-2,2)), player.getTargetBlock(null, 40).getLocation().add(Math.random(),Math.random(),Math.random()), Color.ORANGE,3);
								  	launchFirebolt(player, player.getLocation().add(RandomNumber(-2,2), RandomNumber(-2,2), RandomNumber(-2,2)), player.getTargetBlock(null, 40).getLocation().add(Math.random(),Math.random(),Math.random()), Color.ORANGE,4);
									  player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 3, 1);
									  
									 
									  
									  this.cancel();
							  }
					  
						  }
					  }.runTaskTimer(DonjonMain.instance, 0, 5);
	    
	  }
   
   public BukkitRunnable firebolt(Vector vector, int z, Location target, Player player, Color color, int nbr) {
	   return new BukkitRunnable() {
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
						if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 2.5) {
							collide = true;
						}
					}
				}
				if(!collide) {
				  Particle.DustOptions dustOptions = new Particle.DustOptions(color, 1F);
					 target.getWorld().spawnParticle(Particle.FLAME, v1.getX(), v1.getY(), v1.getZ(), 0, 0.0D, 0.0D, 0.0D, 0.1D);
				  
				 
				  
				  int nbrcircle = 2;
				  for (double i = 0; i <= Math.PI; i += Math.PI / nbrcircle) {
					   double radius = Math.sin(i) * 0.25;
					   double y = Math.cos(i) * 0.25;
					   for (double a = 0; a < Math.PI * 2; a+= Math.PI / nbrcircle) {
					      double x = Math.cos(a) * radius;
					      double z = Math.sin(a) * radius;
					      v1.add(new Vector(x, y, z));
					      target.getWorld().spawnParticle(Particle.REDSTONE, v1.getX(), v1.getY(), v1.getZ() , 0, 0D, 0D, 0D, dustOptions);
					      v1.subtract(new Vector(x, y, z));
					      
					   }
					   
					}
				}else {
					
					  target.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, v1.getX(), v1.getY(), v1.getZ() , 0, 0D, 0D, 0D);
					  
					 
						  target.getWorld().spawnParticle(Particle.LAVA, v1.getX(), v1.getY(), v1.getZ() , 25, 0.25D, 0.25D, 0.25D);
					  
					  for (int j = 0; j < target.getWorld().getEntities().size(); j++) {
							if(!(target.getWorld().getEntities().get(j).equals(player)) && target.getWorld().getEntities().get(j) instanceof LivingEntity) {
								if(l.distance(target.getWorld().getEntities().get(j).getLocation()) < 2.5) {
									LivingEntity entity = (LivingEntity)target.getWorld().getEntities().get(j);
									entity.damage(8, player);
								}
							}
						}
					  
					  
					  if(nbr == 1) {
					    	for (int j = 0; j < brun.size(); j++) {
					    		brun.get(j).cancel();
					    	}
					    }else if(nbr == 2) {
					    	 for (int j = 0; j < brun2.size(); j++) {
					    		 brun2.get(j).cancel();
					 		}	
					    }else if(nbr == 3) {
					    	 for (int j = 0; j < brun3.size(); j++) {
					    		 brun3.get(j).cancel();
					 		}	
					    }else if(nbr == 4) {
					    	 for (int j = 0; j < brun4.size(); j++) {
					    		 brun4.get(j).cancel();
					 		}	
					    }
					  
					
				}
				  			
			}
		};
   }
   
   private void launchFirebolt(Player player, Location target, Location target2, Color color, int nbr) {
	    double distance = target.distance(target2);
	    Vector v = target.toVector();
	    Vector v2 = target2.toVector();
	    Vector vector = v2.clone().subtract(v).normalize().multiply(0.25D);
	    double length = 0.0D;
	    for (int i = 0; length < distance;) {
	    	int z = i;
	    	 if(nbr == 1) {
	    		 brun.add(firebolt(vector, z, target, player, color, 1));
	 	    }else if(nbr == 2) {
	 	    	brun2.add(firebolt(vector, z, target, player, color, 2));
	 	    }else if(nbr == 3) {
	 	    	brun3.add(firebolt(vector, z, target, player, color, 3));
	 	    }else if(nbr == 4) {
	 	    	brun4.add(firebolt(vector, z, target, player, color, 4));
	 	    } 	
	        
	    	i++;
	        length += 0.25D;
	    }
	    if(nbr == 1) {
	    	for (int j = 0; j < brun.size(); j++) {
	    		brun.get(j).runTaskLater(DonjonMain.instance, (int)(Math.log(j)*10));
	    	}
	    }else if(nbr == 2) {
	    	 for (int j = 0; j < brun2.size(); j++) {
	    		 brun2.get(j).runTaskLater(DonjonMain.instance, (int)(Math.log(j)*10));
	 		}	
	    }else if(nbr == 3) {
	    	 for (int j = 0; j < brun3.size(); j++) {
	    		 brun3.get(j).runTaskLater(DonjonMain.instance, (int)(Math.log(j)*10));
	 		}	
	    }else if(nbr == 4) {
	    	 for (int j = 0; j < brun4.size(); j++) {
	    		 brun4.get(j).runTaskLater(DonjonMain.instance, (int)(Math.log(j)*10));
	 		}	
	    }
	    
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
