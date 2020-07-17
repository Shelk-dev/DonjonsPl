package fr.eazyender.donjon.potion;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class PotionSmallHeal extends IPotion{
	
	static int basicCooldown = 5 * 1000;
	
	public PotionSmallHeal(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
       if (super.launch(player, PotionSmallHeal.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 1, 1);
    	  drinkPotion(player);
       } else {
    	   
       }
   }
   
   private void drinkPotion(Player player) {
	   
	   if(player.getHealth()+6 <= player.getMaxHealth())
	   		player.setHealth(player.getHealth()+6);
	   else player.setHealth(player.getMaxHealth());
	   
	   player.getWorld().spawnParticle(Particle.HEART, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 5, 1, 1, 1);
	    
	  }

}
