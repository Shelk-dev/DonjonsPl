package fr.eazyender.donjon.potion;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import fr.eazyender.donjon.files.PlayerEquipment;

public class ItemPotionEvent implements Listener{
	
	@EventHandler()
	public void onPlayerUse(PlayerInteractEvent event){
	    Player p = event.getPlayer();
	 
	    for (int i = 1; i <= PotionUtils.potionsNumber; i++) {
	    	ItemStack itm = p.getItemInHand().clone();
	    	itm.setAmount(1);
	    	if(itm.equals(PotionUtils.getItemPotionById(""+i))){
		        switch(i) {
		        case 1: 
		        	PotionSmallHeal potion = new PotionSmallHeal(1000*5);
		        	if(IPotion.cooldowns.containsKey(p))
		        	if(!IPotion.cooldowns.get(p).containsKey(PotionSmallHeal.class))
		        	{
		        		if(event.getItem().getAmount() == 1) p.getInventory().removeItem(event.getItem());
		        		else event.getItem().setAmount(event.getItem().getAmount()-1);
		        	RemovePotion(p, 1);}
		        	else if(PotionSmallHeal.getRemainingCooldown(p, potion) <= 0) {
		        		if(event.getItem().getAmount() == 1) p.getInventory().removeItem(event.getItem());
		        		else event.getItem().setAmount(event.getItem().getAmount()-1);
		        	RemovePotion(p, 1);
		        	}	
		        	potion.launch(p);
		        	break;
		        case 2: 
		        	PotionSmallMana potion1 = new PotionSmallMana(1000*5);
		        	if(IPotion.cooldowns.containsKey(p))
		        	if(!IPotion.cooldowns.get(p).containsKey(PotionSmallMana.class))
		        	{
		        		if(event.getItem().getAmount() == 1) p.getInventory().removeItem(event.getItem());
		        		else event.getItem().setAmount(event.getItem().getAmount()-1);
		        	RemovePotion(p, 2);}
		        	else if(PotionSmallMana.getRemainingCooldown(p, potion1) <= 0) {
		        		if(event.getItem().getAmount() == 1) p.getInventory().removeItem(event.getItem());
		        		else event.getItem().setAmount(event.getItem().getAmount()-1);
		        	RemovePotion(p, 2);
		        	}	
		        	potion1.launch(p);
		        	break;
		        }
		    }
		}
	}
	
	@EventHandler()
	public void onPlayerHold(PlayerItemHeldEvent event){
	    Player p = event.getPlayer();
	 
	    if(p.getInventory().getItem(event.getNewSlot()) != null)
	    for (int i = 1; i <= PotionUtils.potionsNumber; i++) {
	    	ItemStack itm = p.getInventory().getItem(event.getNewSlot()).clone();
	    	itm.setAmount(1);
	    	if(p.getInventory().getItem(event.getNewSlot()) != null){
	    	if(itm.equals(PotionUtils.getItemPotionById(""+i))){
		        switch(i) {
		        case 1: 
		        	PotionSmallHeal potion = new PotionSmallHeal(1000*5);
		        	if(IPotion.cooldowns.containsKey(p))
			        	if(!IPotion.cooldowns.get(p).containsKey(PotionSmallHeal.class))
			        	{
			        		if(p.getInventory().getItem(event.getNewSlot()).getAmount() == 1) p.getInventory().removeItem(p.getInventory().getItem(event.getNewSlot()));
			        		else p.getInventory().getItem(event.getNewSlot()).setAmount(p.getInventory().getItem(event.getNewSlot()).getAmount()-1);
			        	RemovePotion(p, 1);}
			        	else if(PotionSmallHeal.getRemainingCooldown(p, potion) <= 0) {
			        		if(p.getInventory().getItem(event.getNewSlot()).getAmount() == 1) p.getInventory().removeItem(p.getInventory().getItem(event.getNewSlot()));
			        		else p.getInventory().getItem(event.getNewSlot()).setAmount(p.getInventory().getItem(event.getNewSlot()).getAmount()-1);
			        	RemovePotion(p, 1);
			        	}
			        	
			      potion.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        case 2: 
		        	PotionSmallMana potion1 = new PotionSmallMana(1000*5);
		        	if(IPotion.cooldowns.containsKey(p))
			        	if(!IPotion.cooldowns.get(p).containsKey(PotionSmallMana.class))
			        	{
			        		if(p.getInventory().getItem(event.getNewSlot()).getAmount() == 1) p.getInventory().removeItem(p.getInventory().getItem(event.getNewSlot()));
			        		else p.getInventory().getItem(event.getNewSlot()).setAmount(p.getInventory().getItem(event.getNewSlot()).getAmount()-1);
			        	RemovePotion(p, 2);}
			        	else if(PotionSmallMana.getRemainingCooldown(p, potion1) <= 0) {
			        		if(p.getInventory().getItem(event.getNewSlot()).getAmount() == 1) p.getInventory().removeItem(p.getInventory().getItem(event.getNewSlot()));
			        		else p.getInventory().getItem(event.getNewSlot()).setAmount(p.getInventory().getItem(event.getNewSlot()).getAmount()-1);
			        	RemovePotion(p, 2);
			        	}
			        	
			      potion1.launch(p);
		        p.getInventory().setHeldItemSlot(0);
		        	break;
		        }
		    }
	    }
		}
	}
	
	public static void RemovePotion(Player player, int id) {
		List<String> potions = PlayerEquipment.getPlayerEquipment().getPotions(player);
		
		String strM = String.valueOf(id);
		String[] partsM = strM.split("\\:");
		int uniteM = Integer.parseInt(partsM[0]);
		
		if(!potions.isEmpty()) {
		for (int i = 0; i < potions.size(); i++) {
			String str = potions.get(i);
			String[] parts = str.split("\\:");
			int unite = Integer.parseInt(parts[0]);
			int decimal = Integer.parseInt(parts[1]);
			
			//IS POTION?
			if(unite == uniteM) {
				if(decimal > 1) {
				decimal--;
				//RECONSTRUCT
				String dbl = unite + ":" + decimal;
				potions.set(i, dbl);
				}else {
				potions.remove(i);
				}
			}
		
		}
		}
		
		PlayerEquipment.getPlayerEquipment().setPotions(player, potions);
	}

}
