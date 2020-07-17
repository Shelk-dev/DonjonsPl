package fr.eazyender.donjon.potion;

import java.util.HashMap;

import org.bukkit.entity.LivingEntity;

public class IPotion {
	
	public static HashMap<LivingEntity, HashMap<Class<? extends IPotion>, Long>> cooldowns = new HashMap<>();

    public int cooldown;

    public IPotion(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean launch(LivingEntity p, Class<? extends IPotion> potion) {
        if (!cooldowns.containsKey(p) || !cooldowns.get(p).containsKey(potion)
                || System.currentTimeMillis() - cooldowns.get(p).get(potion) > getCooldown()) {
            HashMap<Class<? extends IPotion>, Long> newCooldown = new HashMap<>();
            if (cooldowns.get(p) != null)
                newCooldown = cooldowns.get(p);
            newCooldown.put(potion, System.currentTimeMillis());
            cooldowns.put(p, newCooldown);
            return true;
        } else
            return false;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    
    public static int getRemainingCooldown(LivingEntity p, IPotion potion) {
        int cooldownSkill = potion.getCooldown();
        long activeCooldown = cooldowns.get(p).get(potion.getClass());
        return (int) ((cooldownSkill - (System.currentTimeMillis() - activeCooldown)) / 1000);
    }

}
