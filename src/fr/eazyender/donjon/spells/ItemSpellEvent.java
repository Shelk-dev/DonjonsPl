package fr.eazyender.donjon.spells;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ItemSpellEvent implements Listener{
	
	@EventHandler()
	public void onPlayerUse(PlayerInteractEvent event){
	    Player p = event.getPlayer();
	 
	    for (int i = 1; i <= SpellUtils.spellNumber; i++) {
	    	if(p.getItemInHand().equals(SpellUtils.getItemSpellById(i))){
		        switch(i) {
		        case 1: SpellFirebolt spell = new SpellFirebolt(1000*5); spell.launch(p);
		        	break;
		        case 2: SpellShield spell1 = new SpellShield(1000*3); spell1.launch(p);
		        	break;
		        case 3: SpellDash spell2 = new SpellDash(1000*2); spell2.launch(p);
	        		break;
		        case 4: SpellEarthChocWave spell3 = new SpellEarthChocWave(1000*2); spell3.launch(p);
        			break;
		        case 5: SpellWindSlash spell4 = new SpellWindSlash(1000*2); spell4.launch(p);
    				break;
		        case 6: SpellSpatialFire spell6 = new SpellSpatialFire(1000*2); spell6.launch(p);
					break;
		        case 7: SpellIceJail spell7 = new SpellIceJail(1000*2); spell7.launch(p);
					break;
		        case 8: SpellHealProjectile spell8 = new SpellHealProjectile(1000*2); spell8.launch(p);
					break;
		        case 9: SpellFireProjectiles spell9 = new SpellFireProjectiles(1000*2); spell9.launch(p);
					break;
		        case 10: SpellEmbrasement spell10 = new SpellEmbrasement(1000*2); spell10.launch(p);
					break;
		        case 11: SpellWaterWave spell11 = new SpellWaterWave(1000*2); spell11.launch(p);
					break;
		        case 12: SpellPoisonousSacrifice spell12 = new SpellPoisonousSacrifice(1000*2); spell12.launch(p);
		        	break;
		        case 13: SpellWindSpear spell13 = new SpellWindSpear(1000*2); spell13.launch(p);
	        		break;
		        }
		    }
		}
	}
	
	@EventHandler()
	public void onPlayerHold(PlayerItemHeldEvent event){
	    Player p = event.getPlayer();
	 
	    for (int i = 1; i <= SpellUtils.spellNumber; i++) {
	    	if(p.getInventory().getItem(event.getNewSlot()) != null){
	    	if(p.getInventory().getItem(event.getNewSlot()).equals(SpellUtils.getItemSpellById(i))){
		        switch(i) {
		        case 1: SpellFirebolt spell = new SpellFirebolt(1000*2); spell.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 2: SpellShield spell2 = new SpellShield(1000*2); spell2.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 3: SpellDash spell3 = new SpellDash(1000*2); spell3.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 4: SpellEarthChocWave spell4 = new SpellEarthChocWave(1000*2); spell4.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 5: SpellWindSlash spell5 = new SpellWindSlash(1000*2); spell5.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 6: SpellSpatialFire spell6 = new SpellSpatialFire(1000*2); spell6.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 7: SpellIceJail spell7 = new SpellIceJail(1000*2); spell7.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 8: SpellHealProjectile spell8 = new SpellHealProjectile(1000*2); spell8.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 9: SpellFireProjectiles spell9 = new SpellFireProjectiles(1000*2); spell9.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 10: SpellEmbrasement spell10 = new SpellEmbrasement(1000*2); spell10.launch(p);
		        p.getInventory().setHeldItemSlot(0);
					break;
		        case 11: SpellWaterWave spell11 = new SpellWaterWave(1000*2); spell11.launch(p);
		        p.getInventory().setHeldItemSlot(0);
					break;
		        case 12: SpellPoisonousSacrifice spell12 = new SpellPoisonousSacrifice(1000*2); spell12.launch(p);
		        p.getInventory().setHeldItemSlot(0);
					break;
		        case 13: SpellWindSpear spell13 = new SpellWindSpear(1000*2); spell13.launch(p);
		        p.getInventory().setHeldItemSlot(0);
					break;
		        }
		    }
	    }
		}
	}

}
