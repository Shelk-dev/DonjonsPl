package fr.eazyender.donjon.potion;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.eazyender.donjon.spells.ManaEvents;

public class PotionSmallMana extends IPotion{
	
	static int basicCooldown = 5 * 1000;
	
	public PotionSmallMana(int cooldown) {
		super(basicCooldown);
	}

   public void launch(Player player) {
       if (super.launch(player, PotionSmallMana.class)) {
                	
    	  player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WANDERING_TRADER_DRINK_POTION, 1, 1);
    	  drinkPotion(player);
       } else {
    	   
       }
   }
   
   private void drinkPotion(Player player) {
	   
	   if(ManaEvents.mana.get(player)+25 <= ManaEvents.maxMana)
		   ManaEvents.mana.replace(player, ManaEvents.mana.get(player)+25);
	   else  ManaEvents.mana.replace(player, ManaEvents.maxMana);
	   
	   player.getWorld().spawnParticle(Particle.REDSTONE, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 5, 1, 1, 1, new Particle.DustOptions(Color.AQUA, 1.0f));
	    
	  }

}
